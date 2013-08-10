package org.bugby.pattern.api;

import java.util.Collection;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class BugDefinition {

	private final CodePattern trigger;
	private final Collection<CodePattern> requiredMatches;
	private final Collection<CodePattern> forbiddenMatches;
}
