package org.bugby.wildcard;

import japa.parser.ast.Node;

import org.bugby.wildcard.api.WildcardNodeMatcher;

public interface WildcardNodeMatcherFromExample<T extends Node> extends WildcardNodeMatcher<T> {
	public void init(Node nodeFromExample);
}
