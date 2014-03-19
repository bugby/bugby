package org.bugby.wildcard.matcher;

import japa.parser.ast.Node;
import japa.parser.ast.type.ReferenceType;

import org.bugby.api.wildcard.WildcardNodeMatcher;
import org.bugby.api.wildcard.WildcardNodeMatcherFactory;
import org.bugby.matcher.tree.Tree;
import org.bugby.matcher.tree.TreeModel;
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
