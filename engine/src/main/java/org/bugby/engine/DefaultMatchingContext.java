package org.bugby.engine;

import java.util.Comparator;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.lang.model.type.TypeMirror;

import org.bugby.api.javac.ParsedSource;
import org.bugby.api.javac.TypesUtils;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.MatchingType;
import org.bugby.api.wildcard.MatchingValueKey;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.engine.algorithm.NodeMatch;
import org.bugby.engine.algorithm.OneLevelMatcher;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.sun.source.tree.BlockTree;
import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.StatementTree;
import com.sun.source.tree.Tree;
import com.sun.source.util.TreeScanner;

public class DefaultMatchingContext implements MatchingContext {
	private final OneLevelMatcher<Tree, TreeMatcher> oneLevelMatcher = new OneLevelMatcher<Tree, TreeMatcher>(nodeMatch());

	private final Map<String, String> variables = new HashMap<String, String>();
	private final Map<String, TypeMirror> typeRestrictions = new HashMap<String, TypeMirror>();
	private final Map<String, CorrelationInfo> correlations = new HashMap<String, CorrelationInfo>();
	private final Multimap<TreeMatcher, Tree> matches = HashMultimap.create();
	private final ParsedSource parsedSource;
	private final Map<MatchingValueKey, Object> values = new HashMap<MatchingValueKey, Object>();

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

	public DefaultMatchingContext(ParsedSource parsedSource) {
		this.parsedSource = parsedSource;
	}

	private NodeMatch<Tree, TreeMatcher> nodeMatch() {
		return new NodeMatch<Tree, TreeMatcher>() {
			@Override
			public boolean match(TreeMatcher wildcard, Tree node) {
				boolean ok = wildcard.matches(node, DefaultMatchingContext.this);
				if (ok) {
					if (!matches.containsEntry(wildcard, node)) {
						matches.put(wildcard, node);
					}
				}
				return ok;
			}

			@Override
			public MatchingType getMatchingType(TreeMatcher wildcard) {
				return wildcard.getMatchingType();
			}

			@Override
			public void removedNodeFromMatch(Tree node) {
				DefaultMatchingContext.this.clearDataForNode(node);
			}
		};
	}

	@Override
	public String getVariableMapping(String nameInPatternAST) {
		return variables.get(nameInPatternAST);
	}

	@Override
	public boolean setVariableMapping(String nameInPatternAST, String currentName, TypeMirror currentType) {
		TypeMirror tr = typeRestrictions.get(nameInPatternAST);
		if (tr == null || parsedSource.getTypes().isAssignable(tr, parsedSource.getTypes().erasure(currentType))) {
			variables.put(nameInPatternAST, currentName);
			return true;
		}
		return false;
	}

	@Override
	public void addTypeRestriction(String nameInPatternAST, TypeMirror type) {
		// type may be in the class loader of the pattern
		if (!TypesUtils.isObject(type)) {
			TypeMirror rawType = parsedSource.getTypes().erasure(type);
			TypeMirror reloaded = rawType;
			if (!rawType.getKind().isPrimitive()) {
				reloaded = parsedSource.getElements().getTypeElement(rawType.toString()).asType();
			}
			typeRestrictions.put(nameInPatternAST, reloaded);
		}
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

	@Override
	public void clearDataForNode(Tree nodeInSourceAST) {
		// clear correlations
		Iterator<Map.Entry<String, CorrelationInfo>> it = correlations.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, CorrelationInfo> entry = it.next();
			entry.getValue().removeNode(nodeInSourceAST);
			if (entry.getValue().getNodeCount() == 0) {
				it.remove();
			}
		}

		// TODO clear all the other maps
	}

	private Multimap<TreeMatcher, Tree> transformResult(List<TreeMatcher> matchers, List<List<Tree>> oneLevelMatch) {
		Multimap<TreeMatcher, Tree> result = HashMultimap.create();
		for (List<Tree> solution : oneLevelMatch) {
			//special case for empty matching
			if (solution.isEmpty()) {
				//TODO i should put here the parent.
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

	private Multimap<TreeMatcher, Tree> validateResult(List<TreeMatcher> matchers, Multimap<TreeMatcher, Tree> result) {
		Multimap<TreeMatcher, Tree> finalResult = result;
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
	public Multimap<TreeMatcher, Tree> matchOrdered(List<TreeMatcher> matchers, List<? extends Tree> nodes) {
		startMatchers(matchers, true);
		Multimap<TreeMatcher, Tree> result = transformResult(matchers, oneLevelMatcher.matchOrdered((List<Tree>) nodes, matchers));
		return validateResult(matchers, result);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Multimap<TreeMatcher, Tree> matchUnordered(List<TreeMatcher> matchers, List<? extends Tree> nodes) {
		startMatchers(matchers, false);
		Multimap<TreeMatcher, Tree> result = transformResult(matchers, oneLevelMatcher.matchUnordered((List<Tree>) nodes, matchers));
		return validateResult(matchers, result);
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
	}

	@SuppressWarnings("unchecked")
	@Override
	public <V> V getValue(MatchingValueKey key) {
		return (V) values.get(key);
	}

	@Override
	public CompilationUnitTree getCompilationUnitTree() {
		return parsedSource.getCompilationUnitTree();
	}
}
