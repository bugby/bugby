/**
 *  Copyright 2011 Alexandru Craciun, Eyal Kaspi
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.richast.utils;

import static java.lang.String.format;
import japa.parser.ast.Node;

public final class PreConditions {
	private PreConditions() {
		//
	}

	public static void checkState(boolean check, String message, Object... args) {
		if (!check) {
			throw new IllegalArgumentException(format(message, args));
		}
	}

	public static void checkStateNode(Node node, boolean check, String message, Object... args) {
		if (!check) {
			throw new IllegalArgumentException("Line " + node.getBeginLine() + ":" + format(message, args));
		}
	}

	@SuppressWarnings("PMD.AvoidThrowingNullPointerException")
	public static void checkStateNodeNotNull(Node node, Object toTest, String message, Object... args) {
		if (toTest == null) {
			throw new NullPointerException("Line " + node.getBeginLine() + ":" + format(message, args));
		}

	}

	public static <T> T checkNotNull(T obj) {
		return checkNotNull(obj, "");
	}

	@SuppressWarnings("PMD.AvoidThrowingNullPointerException")
	public static <T> T checkNotNull(T obj, String message, Object... args) {
		if (obj == null) {
			throw new NullPointerException(format(message, args));
		}
		return obj;
	}

}
