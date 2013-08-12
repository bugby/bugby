package org.bugby.matcher.tree;

import java.util.ArrayList;
import java.util.List;

import org.bugby.matcher.value.MatchAnalyzer;
import org.bugby.matcher.value.ValueMatcher;

/**
 * An incomplete, recursive approach to the matching algorithm. Currently might find only the first match.
 *  
 * @author catac
 */
public class RecursiveAlgorithm<T> {
	private final Tree<Pattern<T>> patternTree;
	private final Tree<T> searchTree;

	public RecursiveAlgorithm(Tree<Pattern<T>> patternTree, Tree<T> searchTree) {
		this.patternTree = patternTree;
		this.searchTree = searchTree;
	}

	public Tree<T> getSearchTree() {
		return searchTree;
	}

	public Tree<Pattern<T>> getPatternTree() {
		return patternTree;
	}

	private boolean findMatch(Tree<Pattern<T>> searchNode, Tree<T> valueNode) {
		Pattern<T> pattern = searchNode.getValue();
		ValueMatcher<T> valueMatcher = pattern.getValueMatcher();
		T value = valueNode.getValue();

		if (valueMatcher.matches(pattern, value)) {
			pattern.setMatchedTree(valueNode);

			// searchNode matched; now try matching its children
			List<Pattern<T>> childPatterns = findChildrenMatch(searchNode, valueNode);

			MatchAnalyzer<T> matchAnalyzer = pattern.getMatchAnalyzer();
			return matchAnalyzer.matches(pattern, childPatterns);
		}

		// searchNode didn't match; try searching it through corresponding children
		for (int ivn = 0; ivn < valueNode.getChildrenCount(); ivn++) {
			Tree<T> cvn = valueNode.getChild(ivn);
			if (findMatch(searchNode, cvn)) {
				return true;
			}
		}

		// searchNode didn't match in this subtree
		pattern.setMatchedTree(null);
		return false;
	}

	private List<Pattern<T>> findChildrenMatch(Tree<Pattern<T>> searchNode, Tree<T> valueNode) {
		List<Pattern<T>> childPatterns = new ArrayList<Pattern<T>>(searchNode.getChildrenCount());

		int isn = 0, ivn = 0;
		for (; isn < searchNode.getChildrenCount(); isn++) {
			Tree<Pattern<T>> csn = searchNode.getChild(isn);
			Pattern<T> cp = csn.getValue();
			childPatterns.add(cp);

			if (cp.hasUnorderedChildren()) {
				// reset search index for each child pattern
				ivn = 0;
			}

			for (; ivn < valueNode.getChildrenCount(); ivn++) {
				Tree<T> cvn = valueNode.getChild(ivn);
				if (findMatch(csn, cvn)) {
					break;
				}
			}
		}

		return childPatterns;
	}
}
