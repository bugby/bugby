package org.bugby.wildcard.matcher;

import japa.parser.ast.Node;
import japa.parser.ast.stmt.BlockStmt;

import java.util.Map;

import org.bugby.matcher.acr.MatchingType;
import org.bugby.matcher.acr.TreeModel;
import org.bugby.wildcard.api.MatchingContext;
import org.bugby.wildcard.api.WildcardNodeMatcher;
import org.richast.scope.Scope;
import org.richast.type.TypeWrapper;

public class SomeCodeMatcher implements WildcardNodeMatcher {
	private final Map<String, TypeWrapper> typeRestrictions;
	private final Scope patternScope;

	public SomeCodeMatcher(Scope patternScope, Map<String, TypeWrapper> typeRestrictions) {
		this.typeRestrictions = typeRestrictions;
		this.patternScope = patternScope;
	}

	@Override
	public boolean matches(TreeModel<Node, Node> treeModel, Node node, MatchingContext context) {
		if (node instanceof BlockStmt) {
			for (Map.Entry<String, TypeWrapper> entry : typeRestrictions.entrySet()) {
				context.addTypeRestriction(entry.getKey(), patternScope, entry.getValue());
			}
			// TODO need to clean up the restrictions as the end of the children matching
			return true;
		}
		return false;
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
