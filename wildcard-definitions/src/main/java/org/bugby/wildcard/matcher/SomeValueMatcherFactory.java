package org.bugby.wildcard.matcher;

import japa.parser.ast.Node;
import japa.parser.ast.expr.MethodCallExpr;

import org.bugby.api.wildcard.WildcardNodeMatcher;
import org.bugby.api.wildcard.WildcardNodeMatcherFactory;
import org.bugby.api.wildcard.WildcardPatternBuildContext;
import org.bugby.matcher.tree.Tree;
import org.bugby.matcher.tree.TreeModel;
import org.richast.node.ASTNodeData;
import org.richast.type.ParameterizedTypeWrapper;
import org.richast.type.TypeWrapper;

public class SomeValueMatcherFactory implements WildcardNodeMatcherFactory {

	@Override
	public Tree<WildcardNodeMatcher> buildPatternNode(TreeModel<Node, Node> patternSourceTreeNodeModel, String currentPatternSourceNodeType,
			Node currentPatternSourceNode, Tree<WildcardNodeMatcher> parentPatternNode, WildcardNodeMatcherFactory defaultFactory,
			WildcardPatternBuildContext buildContext) {
		if (currentPatternSourceNode instanceof MethodCallExpr) {
			MethodCallExpr expr = (MethodCallExpr) currentPatternSourceNode;
			TypeWrapper nodeType = null;
			if (expr.getArgs() != null && expr.getArgs().size() > 0) {
				nodeType = ASTNodeData.resolvedType(expr.getArgs().get(0));
				if (nodeType instanceof ParameterizedTypeWrapper) {
					// expect Class<XYZ> -> i need to get the XYZ
					nodeType = ((ParameterizedTypeWrapper) nodeType).getActualTypeArguments()[0];
				}
			}
			WildcardNodeMatcher matcher = new SomeValueMatcher(nodeType);
			return parentPatternNode.newChild(currentPatternSourceNodeType, matcher);
		}
		return defaultFactory.buildPatternNode(patternSourceTreeNodeModel, currentPatternSourceNodeType, currentPatternSourceNode,
				parentPatternNode, defaultFactory, buildContext);
	}

}