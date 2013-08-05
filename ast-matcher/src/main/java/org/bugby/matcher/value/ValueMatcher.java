package org.bugby.matcher.value;

import org.bugby.matcher.tree.Pattern;

/**
 * This defines the matching of a tree node value with a given pattern. This is invoked
 * when the node is seen.
 * 
 * @author catac
 */
public interface ValueMatcher<T> {
	boolean matches(Pattern<T> pattern, T value);
}
