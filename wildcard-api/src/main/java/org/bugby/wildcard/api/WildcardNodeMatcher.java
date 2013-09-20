package org.bugby.wildcard.api;

import japa.parser.ast.Node;

import org.bugby.matcher.acr.MatchingType;

public interface WildcardNodeMatcher {
	public boolean matches(Node node);

	public boolean isOrdered();

	public MatchingType getMatchingType();
}
