package org.bugby.wildcard.matcher;

import japa.parser.ast.Node;
import japa.parser.ast.stmt.BreakStmt;
import japa.parser.ast.stmt.ContinueStmt;
import japa.parser.ast.stmt.ReturnStmt;

import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.WildcardNodeMatcher;
import org.bugby.matcher.tree.MatchingType;
import org.bugby.matcher.tree.TreeModel;

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
