package org.bugby.wildcard.api;

import japa.parser.ast.Node;

public interface WildcardNodeMatcher {
	public boolean matches(Node node);
}
