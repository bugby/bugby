package org.bugby.api.wildcard;

import japa.parser.ast.Node;

import java.util.Comparator;

import org.richast.scope.Scope;
import org.richast.type.TypeWrapper;
import org.richast.variable.Variable;

public interface MatchingContext {
	public Variable getVariableMapping(String nameInPatternAST, Scope scopeInPatternAST);

	public boolean setVariableMapping(String nameInPatternAST, Scope scopeInPatternAST, Variable var);

	public void addTypeRestriction(String nameInPatternAST, Scope scopeInPatternAST, TypeWrapper type);

	/**
	 * for the first node with the given key, the method will store the node and return true. subsequent calls for the
	 * same key will check if the associated comparator returns 0 between the existing node and the new node.
	 * 
	 * @param key
	 * @param node
	 * @return
	 */
	public boolean checkCorrelation(String key, Node nodeInSourceAST, Comparator<Node> comparator);

	/**
	 * clear all mappings, restrictions, etc related to the given node in the source AST
	 * 
	 * @param node
	 */
	public void clearDataForNode(Node nodeInSourceAST);
}
