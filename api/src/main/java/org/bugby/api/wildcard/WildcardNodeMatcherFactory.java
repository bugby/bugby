package org.bugby.api.wildcard;

import japa.parser.ast.Node;

import org.bugby.matcher.tree.Tree;
import org.bugby.matcher.tree.TreeModel;

/**
 * You should implement this factory when you want to control what children has the created WildcardNodeMatcher node. If
 * you only implement the WildcardNodeMatcher, the pattern build will subsequently build pattern node for the matching
 * nodes in the pattern example source.
 * 
 * @author acraciun
 * 
 */
public interface WildcardNodeMatcherFactory {
	/**
	 * 
	 * @param patternSourceTreeNodeModel
	 *            - the access to the pattern example's source
	 * @param currentPatternSourceNode
	 *            - the current AST node in the pattern example's source
	 * @param parentPatternNode
	 *            - the parent node where this factory should attach the nodes it creates
	 * @param defaultFactory
	 *            - the default node factory that will create if needed the children and descendants pattern node
	 * @param buildContext TODO
	 * @return
	 */
	public Tree<WildcardNodeMatcher> buildPatternNode(TreeModel<Node, Node> patternSourceTreeNodeModel,
			Node currentPatternSourceNode, Tree<WildcardNodeMatcher> parentPatternNode,
			WildcardNodeMatcherFactory defaultFactory, WildcardPatternBuildContext buildContext);
}
