package org.bugby.pattern.api;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

import org.bugby.pattern.api.model.Node;

@RequiredArgsConstructor
@ToString
public class CodePattern {

	private final Node root;
}
