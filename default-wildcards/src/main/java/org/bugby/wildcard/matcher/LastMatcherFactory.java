package org.bugby.wildcard.matcher;

import japa.parser.ast.Node;
import japa.parser.ast.expr.MethodCallExpr;

import org.bugby.matcher.acr.TreeModel;
import org.bugby.matcher.tree.Tree;
import org.bugby.wildcard.api.WildcardNodeMatcher;
import org.bugby.wildcard.api.WildcardNodeMatcherFactory;
import org.bugby.wildcard.api.WildcardPatternBuildContext;

public class LastMatcherFactory implements WildcardNodeMatcherFactory {

	@Override
	public Tree<WildcardNodeMatcher> buildPatternNode(TreeModel<Node, Node> patternSourceTreeNodeModel,
			Node currentPatternSourceNode, Tree<WildcardNodeMatcher> parentPatternNode,
			WildcardNodeMatcherFactory defaultFactory, WildcardPatternBuildContext buildContext) {
		buildContext.pushAnnotationNode((MethodCallExpr) currentPatternSourceNode);
		return parentPatternNode;
	}
}
