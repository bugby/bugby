package org.bugby.wildcard.matcher;

import japa.parser.ast.Node;
import japa.parser.ast.expr.MethodCallExpr;

import org.bugby.matcher.acr.TreeModel;
import org.bugby.matcher.tree.Tree;
import org.bugby.wildcard.api.WildcardNodeMatcher;
import org.bugby.wildcard.api.WildcardNodeMatcherFactory;

/**
 * the arguments are simply transformed in child node of type SomeVar - basically it removes the args virtual node
 * 
 * @author acraciun
 * 
 */
public class SomeConditionUsingMatcherFactory implements WildcardNodeMatcherFactory {

	@Override
	public Tree<WildcardNodeMatcher> buildPatternNode(TreeModel<Node, Node> patternSourceTreeNodeModel,
			Node currentPatternSourceNode, Tree<WildcardNodeMatcher> parentPatternNode,
			WildcardNodeMatcherFactory defaultFactory) {
		if (currentPatternSourceNode instanceof MethodCallExpr) {
			WildcardNodeMatcher matcher = new SomeConditionUsingMatcher();
			Tree<WildcardNodeMatcher> newNode = parentPatternNode.newChild(matcher);
			MethodCallExpr expr = (MethodCallExpr) currentPatternSourceNode;
			if (expr.getArgs() != null) {
				for (Node arg : expr.getArgs()) {
					defaultFactory.buildPatternNode(patternSourceTreeNodeModel, arg, newNode, defaultFactory);
				}
			}
			return newNode;
		}
		return defaultFactory.buildPatternNode(patternSourceTreeNodeModel, currentPatternSourceNode, parentPatternNode,
				defaultFactory);
	}

}
