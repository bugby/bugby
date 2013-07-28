package org.bugby.wildcard.api;

import japa.parser.ast.Node;

public interface WildcardNodeMatcher<T extends Node> {
	public boolean matches(T node);
}
