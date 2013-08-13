package org.bugby.wildcard;

import japa.parser.ast.Node;

import org.bugby.wildcard.api.WildcardNodeMatcher;

public interface WildcardNodeMatcherFromExample extends WildcardNodeMatcher {
	public void init(Node nodeFromExample);
}
