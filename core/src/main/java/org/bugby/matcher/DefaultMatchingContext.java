package org.bugby.matcher;

import java.util.Comparator;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.lang.model.type.TypeMirror;

import org.bugby.api.MatchingContext;
import org.bugby.api.MatchingPath;
import org.bugby.api.MatchingType;
import org.bugby.api.MatchingValueKey;
import org.bugby.api.TreeMatcher;
import org.bugby.matcher.algorithm.NodeMatch;
import org.bugby.matcher.algorithm.OneLevelMatcher;
import org.bugby.matcher.javac.ParsedSource;
import org.bugby.matcher.javac.TypesUtils;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.sun.source.tree.BlockTree;
import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.StatementTree;
import com.sun.source.tree.Tree;
import com.sun.source.util.TreeScanner;

public class DefaultMatchingContext implements MatchingContext {
	private final OneLevelMatcher<Tree, TreeMatcher, MatchingPath> oneLevelMatcher = new OneLevelMatcher<Tree, TreeMatcher, MatchingPath>(
			nodeMatch());

	private final Map<String, CorrelationInfo> correlations = new HashMap<String, CorrelationInfo>();
	private final Multimap<TreeMatcher, Tree> matches = HashMultimap.create();
	private final ParsedSource parsedSource;
	private final Map<MatchingValueKey, Object> values = new HashMap<MatchingValueKey, Object>();

	private final TreeMatcher rootMatcher;

	private MatchingPath rootPath;
	private MatchingPath currentPath;

	private static class CorrelationInfo {
		private final Comparator<Tree> comparator;
		private final Set<Tree> nodes = Sets.newIdentityHashSet();

		public CorrelationInfo(Comparator<Tree> comparator, Tree node) {
			this.comparator = comparator;
			this.nodes.add(node);
		}

		public Comparator<Tree> getComparator() {
			return comparator;
		}

		public Tree getFirstNode() {
			return nodes.iterator().next();
		}

		public void addNode(Tree n) {
			nodes.add(n);
		}

		public void removeNode(Tree n) {
			nodes.remove(n);
		}

		public int getNodeCount() {
			return nodes.size();
		}

		@Override
		public String toString() {
			return "CI:" + nodes;
		}

	}

	public DefaultMatchingContext(TreeMatcher rootMatcher, ParsedSource parsedSource) {
		this.parsedSource = parsedSource;
		this.rootMatcher = rootMatcher;
	}

	private NodeMatch<Tree, TreeMatcher, MatchingPath> nodeMatch() {
		return new NodeMatch<Tree, TreeMatcher, MatchingPath>() {
			@Override
			public boolean match(TreeMatcher wildcard, Tree node, List<TreeMatcher> wildcards, List<Tree> nodes) {
				currentPath = new MatchingPath(DefaultMatchingContext.this, wildcard, node, currentPath);
				boolean ok = wildcard.matches(node, DefaultMatchingContext.this);
				MatchingPath oldPath = currentPath;
				currentPath = currentPath.getParent();
				if (ok) {
					if (!matches.containsEntry(wildcard, node)) {
						matches.put(wildcard, node);
					}
				} else {
					oldPath.remove();
				}
				return ok;
			}

			@Override
			public MatchingType getMatchingType(TreeMatcher wildcard) {
				return wildcard.getMatchingType();
			}

			@Override
			public void removedNodeFromMatch(TreeMatcher wildcard, Tree node) {
				MatchingPath child = currentPath.getChild(wildcard, node);
				if (child != null) {
					child.remove();
				}

			}

			@Override
			public void solutionFound() {
				currentPath.addSolution();
			}

			@Override
			public MatchingPath buildResult(TreeMatcher wildcard, Tree node) {
				return currentPath.getChild(wildcard, node);
			}
		};
	}

	@Override
	public boolean checkCorrelation(String key, Tree nodeInSourceAST, Comparator<Tree> comparator) {
		CorrelationInfo correlation = correlations.get(key);
		if (correlation == null) {
			correlations.put(key, new CorrelationInfo(comparator, nodeInSourceAST));
			return true;
		}

		if (correlation.getComparator().compare(correlation.getFirstNode(), nodeInSourceAST) == 0) {
			correlation.addNode(nodeInSourceAST);
			return true;
		}
		return false;
	}

	private Multimap<TreeMatcher, Tree> transformResult(List<TreeMatcher> matchers, List<List<Tree>> oneLevelMatch) {
		Multimap<TreeMatcher, Tree> result = HashMultimap.create();
		for (List<Tree> solution : oneLevelMatch) {
			// special case for empty matching
			if (solution.isEmpty()) {
				// TODO i should put here the parent.
				result.put(matchers.get(0), getCompilationUnitTree());
			}
			for (int i = 0; i < solution.size(); ++i) {
				if (!result.containsEntry(matchers.get(i), solution.get(i))) {
					result.put(matchers.get(i), solution.get(i));
				}
			}
		}
		return result;
	}

	private List<List<MatchingPath>> validateResult(List<TreeMatcher> matchers, List<List<MatchingPath>> result) {
		List<List<MatchingPath>> finalResult = result;
		for (TreeMatcher m : matchers) {
			finalResult = m.endMatching(finalResult, this);
		}
		return finalResult;
	}

	private void startMatchers(List<TreeMatcher> matchers, boolean ordered) {
		for (TreeMatcher m : matchers) {
			m.startMatching(ordered, this);
		}

	}

	@Override
	@SuppressWarnings("unchecked")
	public List<List<MatchingPath>> matchOrdered(List<TreeMatcher> matchers, List<? extends Tree> nodes) {
		startMatchers(matchers, true);
		List<List<MatchingPath>> result = oneLevelMatcher.matchOrdered((List<Tree>) nodes, matchers);
		return validateResult(matchers, result);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<List<MatchingPath>> matchUnordered(List<TreeMatcher> matchers, List<? extends Tree> nodes) {
		startMatchers(matchers, false);
		List<List<MatchingPath>> result = oneLevelMatcher.matchUnordered((List<Tree>) nodes, matchers);
		return validateResult(matchers, result);
	}

	@Override
	public boolean matches() {
		currentPath = rootPath = new MatchingPath(this, rootMatcher, getCompilationUnitTree(), null);
		return rootMatcher.matches(parsedSource.getCompilationUnitTree(), this);
	}

	@Override
	public Multimap<TreeMatcher, Tree> getMatches() {
		return matches;
	}

	// TODO find a better approach
	private Map<Tree, List<Tree>> childrenListByNode;

	@Override
	public List<Tree> getSiblingsOf(Tree node) {
		if (childrenListByNode == null) {
			childrenListByNode = new IdentityHashMap<Tree, List<Tree>>();
			// only do it for statements at this stage
			new TreeScanner<Boolean, Boolean>() {
				@Override
				public Boolean visitBlock(BlockTree node, Boolean p) {
					for (StatementTree stmt : node.getStatements()) {
						childrenListByNode.put(stmt, (List<Tree>) node.getStatements());
					}
					return super.visitBlock(node, p);
				}
			}.scan(parsedSource.getCompilationUnitTree(), true);
		}
		return childrenListByNode.get(node);
	}

	@Override
	public <V> void putValue(MatchingValueKey key, V value) {
		values.put(key, value);
		currentPath.addValueKey(key);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <V> V getValue(MatchingValueKey key) {
		return (V) values.get(key);
	}

	@Override
	public void removeValue(MatchingValueKey key) {
		// TODO should i call the path as well here !? need to clarify who can call what
		// TODO i should keep a counter for when keys are shared
		values.remove(key);
	}

	@Override
	public CompilationUnitTree getCompilationUnitTree() {
		return parsedSource.getCompilationUnitTree();
	}

	@Override
	public Map<MatchingValueKey, Object> getValues() {
		return values;
	}

	@Override
	public MatchingPath getCurrentMatchingPath() {
		return currentPath;
	}

	public MatchingPath getRootMatchingPath() {
		return rootPath;
	}

	@Override
	public ParsedSource getParsedSource() {
		return parsedSource;
	}

	private TypeMirror processType(TypeMirror type) {
		if (!TypesUtils.isObject(type)) {
			TypeMirror rawType = getParsedSource().getTypes().erasure(type);
			TypeMirror reloaded = rawType;
			if (!rawType.getKind().isPrimitive()) {
				reloaded = getParsedSource().getElements().getTypeElement(rawType.toString()).asType();
			}
			return reloaded;
		}
		return getParsedSource().getElements().getTypeElement(type.toString()).asType();
	}

	@Override
	public boolean compatibleTypes(TypeMirror patternType, TypeMirror sourceNodeType) {
		TypeMirror processedPatternType = processType(patternType);
		TypeMirror processedSourceNodeType = processType(sourceNodeType);

		return getParsedSource().getTypes().isAssignable(processedSourceNodeType, processedPatternType);
	}

}
