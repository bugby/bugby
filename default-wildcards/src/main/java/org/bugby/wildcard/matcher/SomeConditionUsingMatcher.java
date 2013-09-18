package org.bugby.wildcard.matcher;

import japa.parser.ast.Node;
import japa.parser.ast.expr.Expression;

import org.bugby.wildcard.WildcardNodeMatcherFromExample;
import org.richast.node.ASTNodeData;
import org.richast.type.TypeWrapper;
import org.richast.type.TypeWrappers;

public class SomeConditionUsingMatcher implements WildcardNodeMatcherFromExample {

	@Override
	public boolean matches(Node node) {
		// check if type = boolean
		if (node instanceof Expression) {
			TypeWrapper type = ASTNodeData.resolvedType(node);
			return TypeWrappers.wrap(boolean.class).equals(type);
		}
		return false;
	}

	@Override
	public void init(Node nodeFromExample) {
		// TODO Auto-generated method stub

	}

}