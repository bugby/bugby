package org.bugby.wildcard.matcher;

import japa.parser.ast.Node;
import japa.parser.ast.expr.Expression;

import org.bugby.matcher.acr.MatchingType;
import org.bugby.wildcard.api.WildcardNodeMatcher;
import org.richast.node.ASTNodeData;
import org.richast.type.TypeWrapper;

public class SomeValueMatcher implements WildcardNodeMatcher {
	private final TypeWrapper nodeType;

	public SomeValueMatcher(TypeWrapper nodeType) {
		this.nodeType = nodeType;
	}

	@Override
	public boolean matches(Node node) {
		if (!(node instanceof Expression)) {
			return false;
		}
		if (nodeType == null) {
			return true;
		}
		return nodeType.equals(ASTNodeData.resolvedType(node));
	}

	@Override
	public boolean isOrdered() {
		return true;
	}

	@Override
	public MatchingType getMatchingType() {
		return MatchingType.normal;
	}
}
