package org.bugby.wildcard.matcher;

import japa.parser.ast.stmt.BlockStmt;

import org.bugby.wildcard.api.WildcardNodeMatcher;

public class SomeCodeMatcher implements WildcardNodeMatcher<BlockStmt> {

	@Override
	public boolean matches(BlockStmt node) {
		return true;
	}

}
