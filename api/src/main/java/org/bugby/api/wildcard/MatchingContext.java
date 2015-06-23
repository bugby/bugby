package org.bugby.api.wildcard;

import java.util.Comparator;
import java.util.List;

import javax.lang.model.type.TypeMirror;

import com.google.common.collect.Multimap;
import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.Tree;

public interface MatchingContext {
	public String getVariableMapping(String nameInPatternAST);

	public boolean setVariableMapping(String nameInPatternAST, String currentName, TypeMirror type);

	public void addTypeRestriction(String nameInPatternAST, TypeMirror type);

	/**
	 * for the first node with the given key, the method will store the node and return true. subsequent calls for the same key will check if the
	 * associated comparator returns 0 between the existing node and the new node.
	 * @param key
	 * @param node
	 * @return
	 */
	public boolean checkCorrelation(String key, Tree nodeInSourceAST, Comparator<Tree> comparator);

	/**
	 * clear all mappings, restrictions, etc related to the given node in the source AST
	 * @param node
	 */
	public void clearDataForNode(Tree nodeInSourceAST);

	public Multimap<TreeMatcher, Tree> matchOrdered(List<TreeMatcher> matchers, List<? extends Tree> nodes);

	public Multimap<TreeMatcher, Tree> matchUnordered(List<TreeMatcher> matchers, List<? extends Tree> nodes);

	public Multimap<TreeMatcher, Tree> getMatches();

	public List<Tree> getSiblingsOf(Tree node);

	public <V> void putValue(MatchingValueKey key, V value);

	public <V> V getValue(MatchingValueKey key);

	public CompilationUnitTree getCompilationUnitTree();
}
