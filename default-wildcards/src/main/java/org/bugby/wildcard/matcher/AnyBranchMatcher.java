package org.bugby.wildcard.matcher;

import japa.parser.ast.Node;
import japa.parser.ast.stmt.BreakStmt;
import japa.parser.ast.stmt.ContinueStmt;
import japa.parser.ast.stmt.ReturnStmt;

import org.bugby.matcher.acr.MatchingType;
import org.bugby.matcher.acr.TreeModel;
import org.bugby.wildcard.api.MatchingContext;
import org.bugby.wildcard.api.WildcardNodeMatcher;

public class AnyBranchMatcher implements WildcardNodeMatcher {

	@Override
	public boolean matches(TreeModel<Node, Node> treeModel, Node node, MatchingContext context) {
		return node instanceof BreakStmt || node instanceof ContinueStmt || node instanceof ReturnStmt;
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
