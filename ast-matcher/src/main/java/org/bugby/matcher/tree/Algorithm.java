package org.bugby.matcher.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the matching algorithm.
 *  
 * @author catac
 */
public class Algorithm<T> {
	private final Tree<Pattern<T>> patternTree;
	private final Tree<T> searchTree;

	private final List<StackEntry<Pattern<T>>> patternStack = new ArrayList<StackEntry<Pattern<T>>>();
	private final List<StackEntry<T>> searchStack = new ArrayList<StackEntry<T>>();

	public Algorithm(Tree<Pattern<T>> patternTree, Tree<T> searchTree) {
		this.patternTree = patternTree;
		this.searchTree = searchTree;

		patternStack.add(new StackEntry<Pattern<T>>(patternTree));
		searchStack.add(new StackEntry<T>(searchTree));
	}

	public Tree<T> getSearchTree() {
		return searchTree;
	}

	public Tree<Pattern<T>> getPatternTree() {
		return patternTree;
	}

	public boolean findMatch() {
		int psSize = -1, ssSize = -1;

		// TODO use tree-size vs stack height comparison instead of this
		while (((psSize = patternStack.size()) > 0) && ((ssSize = searchStack.size()) > 0)) {
			StackEntry<Pattern<T>> topPatternNode = patternStack.get(psSize - 1);
			StackEntry<T> topSearchNode = searchStack.get(ssSize - 1);

			Pattern<T> topPattern = topPatternNode.node.getValue();
			T topValue = topSearchNode.node.getValue();

			if (topPattern.getValueMatcher().matches(topPattern, topValue)) {
				topPattern.setMatchedTree(topSearchNode.node);
				advance(patternStack);
				advance(searchStack);
			} else {
				// keep the pattern here, but advance the searchStack
				advance(searchStack);
			}
			// TODO
		}

		// TODO
		return false;
	}

	/* package */<E> boolean advance(List<StackEntry<E>> stack) {
		int size;
		while ((size = stack.size()) > 0) {
			StackEntry<E> top = stack.get(size - 1);
			if (top.hasNextChild()) {
				stack.add(new StackEntry<E>(top.getNextChild()));
				return true;
			}
			stack.remove(size - 1);
		}
		return false;
	}

	/* package */static class StackEntry<E> {
		final Tree<E> node;
		final int children;
		int crtChild;

		public StackEntry(Tree<E> node) {
			this.node = node;
			this.children = node.getChildrenCount();
			this.crtChild = -1;
		}

		public boolean hasNextChild() {
			return crtChild < (children - 1);
		}

		public Tree<E> getNextChild() {
			crtChild++;
			return node.getChild(crtChild);
		}
	}

}
