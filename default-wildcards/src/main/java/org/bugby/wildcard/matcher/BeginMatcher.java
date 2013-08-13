package org.bugby.wildcard.matcher;

import japa.parser.ast.Node;
import japa.parser.ast.stmt.BlockStmt;

import org.bugby.wildcard.api.WildcardNodeMatcher;

public class BeginMatcher implements WildcardNodeMatcher {

	@Override
	public boolean matches(Node node) {
		return node instanceof BlockStmt;
	}

}
