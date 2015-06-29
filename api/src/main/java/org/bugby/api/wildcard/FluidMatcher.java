package org.bugby.api.wildcard;

import java.util.Collections;
import java.util.List;

import com.google.common.base.Strings;
import com.google.common.collect.Multimap;
import com.sun.source.tree.Tree;

public class FluidMatcher {
	private final MatchingContext context;
	private final Tree node;
	private final TreeMatcher matcher;
	private boolean currentMatch = true;
	private static int level = 0;

	public FluidMatcher(MatchingContext context, Tree node, TreeMatcher matcher) {
		this.context = context;
		this.node = node;
		this.matcher = matcher;
		System.out.println(Strings.repeat("  ", level) + ">> " + toString(matcher) + " on " + toString(node) + " ["
				+ node.toString().replace("\n", "  ") + "]");
		level++;
	}

	private String toString(Object obj) {
		return obj.getClass().getSimpleName() + "@" + Integer.toHexString(System.identityHashCode(obj));
	}

	protected <T> List<T> list(T element) {
		return element == null ? Collections.<T>emptyList() : Collections.singletonList(element);
	}

	public FluidMatcher child(Tree child, TreeMatcher childMatcher) {
		return matchChildren(list(child), list(childMatcher), false);
	}

	public FluidMatcher unorderedChildren(List<? extends Tree> children, List<TreeMatcher> matchers) {
		return matchChildren(children, matchers, false);
	}

	public FluidMatcher orderedChildren(List<? extends Tree> children, List<TreeMatcher> matchers) {
		return matchChildren(children, matchers, true);
	}

	protected FluidMatcher matchChildren(List<? extends Tree> children, List<TreeMatcher> matchers, boolean ordered) {
		if (!currentMatch) {
			return this;
		}
		if (matchers.isEmpty()) {
			currentMatch = true;
			return this;
		}
		Multimap<TreeMatcher, Tree> childrenMatch = ordered ? context.matchOrdered(matchers, children) : context.matchUnordered(matchers,
				children);

		currentMatch = !childrenMatch.isEmpty();
		return this;
	}

	public boolean done(boolean result) {
		currentMatch = result;
		return done();
	}

	public boolean done() {
		level--;
		if (currentMatch) {
			// matches - but put in error to view it better
			System.out.println(Strings.repeat("  ", level) + "<<  " + toString(matcher) + " = " + currentMatch);
		} else {
			System.out.println(Strings.repeat("  ", level) + "<< " + toString(matcher) + " = " + currentMatch);
		}

		return currentMatch;
	}

	public FluidMatcher self(boolean b) {
		currentMatch &= b;
		return this;
	}

	public boolean isCurrentMatch() {
		return currentMatch;
	}

}
