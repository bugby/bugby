package org.bugby.matcher;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;

import org.bugby.api.MatchingContext;
import org.bugby.api.MatchingPath;
import org.bugby.api.MatchingType;
import org.bugby.api.MatchingValueKey;
import org.bugby.api.SolutionFoundCallback;
import org.bugby.api.TreeMatcher;
import org.bugby.api.TreeMatcherExecutionType;
import org.bugby.matcher.algorithm.NodeMatch;
import org.bugby.matcher.algorithm.OneLevelMatcher;
import org.bugby.matcher.javac.ElementUtils;
import org.bugby.matcher.javac.ParsedSource;
import org.bugby.matcher.javac.TypesUtils;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sun.source.tree.BlockTree;
import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.StatementTree;
import com.sun.source.tree.Tree;
import com.sun.source.util.TreeScanner;

public class DefaultMatchingContext implements MatchingContext {
	private final OneLevelMatcher<Tree, TreeMatcher, MatchingPath> oneLevelMatcher = new OneLevelMatcher<Tree, TreeMatcher, MatchingPath>(
			nodeMatch());

	private final Multimap<TreeMatcher, Tree> matches = HashMultimap.create();
	private final ParsedSource parsedSource;
	private final Map<MatchingValueKey, Object> values = new HashMap<MatchingValueKey, Object>();

	private final TreeMatcher rootMatcher;
	private final ClassLoader builtProjectClassLoader;

	private MatchingPath rootPath;
	private MatchingPath currentPath;

	public DefaultMatchingContext(ClassLoader builtProjectClassLoader, TreeMatcher rootMatcher, ParsedSource parsedSource) {
		this.parsedSource = parsedSource;
		this.rootMatcher = rootMatcher;
		this.builtProjectClassLoader = builtProjectClassLoader;
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

	private List<List<MatchingPath>> validateResult(List<TreeMatcher> matchers, List<List<MatchingPath>> result) {
		List<List<MatchingPath>> finalResult = result;
		for (TreeMatcher m : matchers) {
			finalResult = m.endMatching(finalResult, this);
		}
		return finalResult;
	}

	private List<TreeMatcher> startMatchers(List<TreeMatcher> matchers, boolean ordered) {
		List<TreeMatcher> filteredMatchers = new ArrayList<>(matchers.size());
		for (TreeMatcher m : matchers) {
			if (m.startMatching(ordered, this) == TreeMatcherExecutionType.keep) {
				filteredMatchers.add(m);
			}
		}
		return filteredMatchers;
	}

	@Override
	public List<List<MatchingPath>> matchOrdered(List<TreeMatcher> matchers, List<? extends Tree> nodes) {
		return matchOrdered(matchers, nodes, null);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<List<MatchingPath>> matchOrdered(List<TreeMatcher> matchers, List<? extends Tree> nodes,
			SolutionFoundCallback solutionFoundCallback) {
		List<TreeMatcher> filteredMatchers = startMatchers(matchers, true);
		List<List<MatchingPath>> result = oneLevelMatcher.matchOrdered((List<Tree>) nodes, filteredMatchers, solutionFoundCallback);
		return validateResult(matchers, result);
	}

	@Override
	public List<List<MatchingPath>> matchUnordered(List<TreeMatcher> matchers, List<? extends Tree> nodes) {
		return matchUnordered(matchers, nodes, null);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<List<MatchingPath>> matchUnordered(List<TreeMatcher> matchers, List<? extends Tree> nodes,
			SolutionFoundCallback solutionFoundCallback) {
		List<TreeMatcher> filteredMatchers = startMatchers(matchers, false);
		List<List<MatchingPath>> result = oneLevelMatcher.matchUnordered((List<Tree>) nodes, filteredMatchers, solutionFoundCallback);
		return validateResult(matchers, result);
	}

	@Override
	public boolean matches() {
		currentPath = rootPath = new MatchingPath(this, rootMatcher, getCompilationUnitTree(), null);
		for (Tree cls : getCompilationUnitTree().getTypeDecls()) {
			//TODO may cleanup between matches
			boolean ok = rootMatcher.matches(cls, this);
			if (ok) {
				return true;
			}
		}
		return false;
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
						childrenListByNode.put(stmt, (List) node.getStatements());
					}
					return super.visitBlock(node, p);
				}
			}.scan(getCompilationUnitTree(), true);
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

	@Override
	public boolean sameType(TypeMirror patternType, TypeMirror sourceNodeType) {
		TypeMirror processedPatternType = processType(patternType);
		TypeMirror processedSourceNodeType = processType(sourceNodeType);

		return getParsedSource().getTypes().isSameType(processedPatternType, processedSourceNodeType);
	}

	@Override
	public Class<?> getClassAnnotationField(Element element, Class<? extends Annotation> annotationType, String fieldName) {
		return ElementUtils.getAnnotationClassValue(builtProjectClassLoader, element, annotationType, fieldName);
	}

}
