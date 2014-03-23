package org.bugby.wildcard.matcher;

import japa.parser.ast.Node;
import japa.parser.ast.expr.MethodCallExpr;

import org.bugby.api.wildcard.WildcardNodeMatcher;
import org.bugby.api.wildcard.WildcardNodeMatcherFactory;
import org.bugby.api.wildcard.WildcardPatternBuildContext;
import org.bugby.matcher.tree.Tree;
import org.bugby.matcher.tree.TreeModel;

/**
 * the arguments are simply transformed in child node of type SomeVar - basically it removes the args virtual node
 * 
 * @author acraciun
 * 
 */
public class SomeExpressionUsingMatcherFactory implements WildcardNodeMatcherFactory {

	@Override
	public Tree<WildcardNodeMatcher> buildPatternNode(TreeModel<Node, Node> patternSourceTreeNodeModel, String currentPatternSourceNodeType,
			Node currentPatternSourceNode, Tree<WildcardNodeMatcher> parentPatternNode, WildcardNodeMatcherFactory defaultFactory,
			WildcardPatternBuildContext buildContext) {
		if (currentPatternSourceNode instanceof MethodCallExpr) {
			WildcardNodeMatcher matcher = new SomeExpressionUsingMatcher();
			Tree<WildcardNodeMatcher> newNode = parentPatternNode.newChild(currentPatternSourceNodeType, matcher);
			MethodCallExpr expr = (MethodCallExpr) currentPatternSourceNode;
			if (expr.getArgs() != null) {
				for (Node arg : expr.getArgs()) {
					defaultFactory.buildPatternNode(patternSourceTreeNodeModel, currentPatternSourceNodeType, arg, newNode, defaultFactory,
							buildContext);
				}
			}
			return newNode;
		}
		return defaultFactory.buildPatternNode(patternSourceTreeNodeModel, currentPatternSourceNodeType, currentPatternSourceNode,
				parentPatternNode, defaultFactory, null);
	}

}