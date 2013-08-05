package org.bugby.matcher.value;

import java.util.List;

import org.bugby.matcher.tree.Pattern;

/**
 * This allows analyzing the matching of a full tree of patterns, being invoked after all
 * direct children found a match or finished their traversal without finding their match. 
 * 
 * @author catac
 */
public interface MatchAnalyzer<T> {
	boolean matches(Pattern<T> pattern, List<Pattern<T>> childPatterns);
}
