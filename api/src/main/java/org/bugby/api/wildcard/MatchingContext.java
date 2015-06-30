package org.bugby.api.wildcard;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.lang.model.type.TypeMirror;

import org.bugby.api.javac.ParsedSource;

import com.google.common.collect.Multimap;
import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.Tree;

public interface MatchingContext {

	/**
	 * for the first node with the given key, the method will store the node and return true. subsequent calls for the same key will check if the
	 * associated comparator returns 0 between the existing node and the new node.
	 * @param key
	 * @param node
	 * @return
	 */
	public boolean checkCorrelation(String key, Tree nodeInSourceAST, Comparator<Tree> comparator);

	public List<List<MatchingPath>> matchOrdered(List<TreeMatcher> matchers, List<? extends Tree> nodes);

	public List<List<MatchingPath>> matchUnordered(List<TreeMatcher> matchers, List<? extends Tree> nodes);

	public Multimap<TreeMatcher, Tree> getMatches();

	public List<Tree> getSiblingsOf(Tree node);

	public <V> void putValue(MatchingValueKey key, V value);

	public <V> V getValue(MatchingValueKey key);

	public void removeValue(MatchingValueKey key);

	public CompilationUnitTree getCompilationUnitTree();

	/**
	 * contains as last node the current matcher being checked. It assumes that the next check is actually part of the path, until the matches
	 * method of the matcher returns.
	 * @return
	 */
	public MatchingPath getCurrentMatchingPath();

	/**
	 * @return true if there is a match between the pattern and the source corresponding to this context
	 */
	boolean matches();

	public ParsedSource getParsedSource();

	boolean compatibleTypes(TypeMirror patternType, TypeMirror sourceNodeType);

	public Map<MatchingValueKey, Object> getValues();

}
