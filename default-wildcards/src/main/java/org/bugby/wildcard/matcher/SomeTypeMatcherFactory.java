package org.bugby.wildcard.matcher;

import japa.parser.ast.Node;
import japa.parser.ast.type.ReferenceType;

import org.bugby.matcher.acr.TreeModel;
import org.bugby.matcher.tree.Tree;
import org.bugby.wildcard.api.WildcardNodeMatcher;
import org.bugby.wildcard.api.WildcardNodeMatcherFactory;
import org.richast.node.ASTNodeData;

public class SomeTypeMatcherFactory extends DefaultMatcherFactory {

	@Override
	protected boolean skipChild(Node node) {
		if (ASTNodeData.parent(node) instanceof ReferenceType) {
			// skip the annoying second type
			return true;
		}
		return false;
	}

	@Override
	protected WildcardNodeMatcher buildPatternNodeOnly(TreeModel<Node, Node> patternSourceTreeNodeModel,
			Node currentPatternSourceNode, Tree<WildcardNodeMatcher> parentPatternNode,
			WildcardNodeMatcherFactory defaultFactory) {
		// deal here with annotations
		return new SomeTypeMatcher(currentPatternSourceNode);
	}
}
