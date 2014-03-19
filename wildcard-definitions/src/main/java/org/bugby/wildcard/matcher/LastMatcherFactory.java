package org.bugby.wildcard.matcher;

import japa.parser.ast.Node;
import japa.parser.ast.expr.MethodCallExpr;

import org.bugby.api.wildcard.WildcardNodeMatcher;
import org.bugby.api.wildcard.WildcardNodeMatcherFactory;
import org.bugby.api.wildcard.WildcardPatternBuildContext;
import org.bugby.matcher.tree.Tree;
import org.bugby.matcher.tree.TreeModel;

public class LastMatcherFactory implements WildcardNodeMatcherFactory {

	@Override
	public Tree<WildcardNodeMatcher> buildPatternNode(TreeModel<Node, Node> patternSourceTreeNodeModel,
			Node currentPatternSourceNode, Tree<WildcardNodeMatcher> parentPatternNode,
			WildcardNodeMatcherFactory defaultFactory, WildcardPatternBuildContext buildContext) {
		buildContext.pushAnnotationNode((MethodCallExpr) currentPatternSourceNode);
		return parentPatternNode;
	}
}
