package org.bugby.wildcard.matcher;

import japa.parser.ast.Node;

import org.bugby.matcher.acr.TreeModel;
import org.bugby.matcher.tree.Tree;
import org.bugby.wildcard.api.WildcardNodeMatcher;
import org.bugby.wildcard.api.WildcardNodeMatcherFactory;
import org.bugby.wildcard.api.WildcardPatternBuildContext;

abstract public class DefaultMatcherFactory implements WildcardNodeMatcherFactory {

	abstract protected WildcardNodeMatcher buildPatternNodeOnly(TreeModel<Node, Node> patternSourceTreeNodeModel,
			Node currentPatternSourceNode, Tree<WildcardNodeMatcher> parentPatternNode,
			WildcardNodeMatcherFactory defaultFactory);

	protected boolean skipChild(Node node) {
		return false;
	}

	@Override
	public Tree<WildcardNodeMatcher> buildPatternNode(TreeModel<Node, Node> patternSourceTreeNodeModel,
			Node currentPatternSourceNode, Tree<WildcardNodeMatcher> parentPatternNode,
			WildcardNodeMatcherFactory defaultFactory, WildcardPatternBuildContext buildContext) {
		WildcardNodeMatcher newMatcher = buildPatternNodeOnly(patternSourceTreeNodeModel, currentPatternSourceNode,
				parentPatternNode, defaultFactory);
		Tree<WildcardNodeMatcher> newParentPatternNode = parentPatternNode.newChild(newMatcher);

		for (Node child : patternSourceTreeNodeModel.getChildren(currentPatternSourceNode, false)) {
			if (!skipChild(child)) {
				defaultFactory.buildPatternNode(patternSourceTreeNodeModel, child, newParentPatternNode,
						defaultFactory, buildContext);
			}
		}
		for (Node child : patternSourceTreeNodeModel.getChildren(currentPatternSourceNode, true)) {
			if (!skipChild(child)) {
				defaultFactory.buildPatternNode(patternSourceTreeNodeModel, child, newParentPatternNode,
						defaultFactory, buildContext);
			}
		}

		return newParentPatternNode;
	}

}
