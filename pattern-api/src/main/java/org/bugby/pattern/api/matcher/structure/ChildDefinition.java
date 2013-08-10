package org.bugby.pattern.api.matcher.structure;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.bugby.pattern.api.model.Node;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ChildDefinition<T extends Node> {

	public static <T extends Node> ChildDefinition<T> noChild() {
		return new ChildDefinition<T>(ChildType.NONE, null, null);
	}

	public static <T extends Node> ChildDefinition<T> anyChild() {
		return new ChildDefinition<T>(ChildType.ANY, null, null);
	}

	public static <T extends Node> ChildDefinition<T> directChild(T child) {
		return new ChildDefinition<T>(ChildType.DIRECT, child, null);
	}

	public static <T extends Node> ChildDefinition<T> distantChild(T distantChild) {
		return new ChildDefinition<T>(ChildType.DISTANT, null, distantChild);
	}

	public enum ChildType {
		NONE, ANY, DIRECT, DISTANT
	}

	private final ChildType childType;

	private final T directChild;

	private final Node distantChild;

}
