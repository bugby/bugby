package org.bugby.wildcard.matcher;

import japa.parser.ast.Node;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.type.Type;

import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.WildcardNodeMatcher;
import org.bugby.matcher.tree.MatchingType;
import org.bugby.matcher.tree.TreeModel;

public class SomeTypeMatcher implements WildcardNodeMatcher {
	private final boolean ordered;
	private final Node nodeFromExample;

	public SomeTypeMatcher(Node nodeFromExample) {
		ordered = nodeFromExample instanceof Type;
		this.nodeFromExample = nodeFromExample;
	}

	@Override
	public boolean matches(TreeModel<Node, Node> treeModel, Node node, MatchingContext context) {
		return node instanceof Type || node instanceof ClassOrInterfaceDeclaration;
	}

	@Override
	public boolean isOrdered(String childType) {
		return "typeParameters".equals(childType);
	}

	@Override
	public MatchingType getMatchingType() {
		return MatchingType.normal;
	}

	@Override
	public String toString() {
		return "SomeTypeMatcher@" + System.identityHashCode(this) + " on " + nodeFromExample.getClass();
	}
}
