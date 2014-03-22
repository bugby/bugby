package org.bugby.wildcard.matcher;

import japa.parser.ast.Node;

import java.util.Collection;
import java.util.Map;

import org.bugby.api.wildcard.WildcardNodeMatcher;
import org.bugby.api.wildcard.WildcardNodeMatcherFactory;
import org.bugby.api.wildcard.WildcardPatternBuildContext;
import org.bugby.matcher.tree.Tree;
import org.bugby.matcher.tree.TreeModel;

abstract public class DefaultMatcherFactory implements WildcardNodeMatcherFactory {

	abstract protected WildcardNodeMatcher buildPatternNodeOnly(TreeModel<Node, Node> patternSourceTreeNodeModel, Node currentPatternSourceNode,
			Tree<WildcardNodeMatcher> parentPatternNode, WildcardNodeMatcherFactory defaultFactory);

	protected boolean skipChild(Node node) {
		return false;
	}

	@Override
	public Tree<WildcardNodeMatcher> buildPatternNode(TreeModel<Node, Node> patternSourceTreeNodeModel, String currentPatternSourceNodeType,
			Node currentPatternSourceNode, Tree<WildcardNodeMatcher> parentPatternNode, WildcardNodeMatcherFactory defaultFactory,
			WildcardPatternBuildContext buildContext) {
		WildcardNodeMatcher newMatcher = buildPatternNodeOnly(patternSourceTreeNodeModel, currentPatternSourceNode, parentPatternNode,
				defaultFactory);
		Tree<WildcardNodeMatcher> newParentPatternNode = parentPatternNode.newChild(currentPatternSourceNodeType, newMatcher);
		for (Map.Entry<String, Collection<Node>> entry : patternSourceTreeNodeModel.getChildren(currentPatternSourceNode, false).asMap()
				.entrySet()) {
			for (Node child : entry.getValue()) {
				if (!skipChild(child)) {
					defaultFactory.buildPatternNode(patternSourceTreeNodeModel, entry.getKey(), child, newParentPatternNode, defaultFactory,
							buildContext);
				}
			}
		}

		for (Map.Entry<String, Collection<Node>> entry : patternSourceTreeNodeModel.getChildren(currentPatternSourceNode, true).asMap()
				.entrySet()) {
			for (Node child : entry.getValue()) {
				if (!skipChild(child)) {
					defaultFactory.buildPatternNode(patternSourceTreeNodeModel, entry.getKey(), child, newParentPatternNode, defaultFactory,
							buildContext);
				}
			}
		}
		return newParentPatternNode;
	}

}
