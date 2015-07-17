package org.bugby.api;

import java.util.Collections;
import java.util.List;

import com.google.common.base.Strings;
import com.sun.source.tree.Tree;

public class FluidMatcher {
	private final MatchingContext context;
	private final Tree node;
	private final TreeMatcher matcher;
	private boolean currentMatch = true;
	private static int level = 0;
	private final boolean partial;

	public FluidMatcher(MatchingContext context, Tree node, TreeMatcher matcher) {
		this(context, node, matcher, false);
	}

	public FluidMatcher(MatchingContext context, Tree node, TreeMatcher matcher, boolean partial) {
		this.context = context;
		this.node = node;
		this.matcher = matcher;
		this.partial = partial;
		if (partial) {
			System.out.println(Strings.repeat("  ", level) + "[ #" + matcher.getId());
		} else {
			System.out.println(Strings.repeat("  ", level) + ">> " + toString(matcher) + " on " + toString(node) + " ["
					+ node.toString().replaceAll("[\\r\\n]+", " | ").replaceAll("[\\t]+", " ") + "]");
		}
		level++;
	}

	private String toString(Object obj) {
		if (obj instanceof TreeMatcher) {
			return obj.getClass().getSimpleName() + "#" + ((TreeMatcher) obj).getId();
		}
		return obj.getClass().getSimpleName() + "@" + Integer.toHexString(System.identityHashCode(obj));
	}

	protected <T> List<T> list(T element) {
		return element == null ? Collections.<T>emptyList() : Collections.singletonList(element);
	}

	public FluidMatcher child(Tree child, TreeMatcher childMatcher) {
		return child(child, childMatcher, PatternListMatchingType.partial);
	}

	public FluidMatcher child(Tree child, TreeMatcher childMatcher, PatternListMatchingType matchingType) {
		return matchChildren(list(child), list(childMatcher), false, matchingType);
	}

	public FluidMatcher unorderedChildren(List<? extends Tree> children, List<TreeMatcher> matchers) {
		return unorderedChildren(children, matchers, PatternListMatchingType.partial);
	}

	public FluidMatcher unorderedChildren(List<? extends Tree> children, List<TreeMatcher> matchers, PatternListMatchingType matchingType) {
		return matchChildren(children, matchers, false, matchingType);
	}

	public FluidMatcher orderedChildren(List<? extends Tree> children, List<TreeMatcher> matchers) {
		return orderedChildren(children, matchers, PatternListMatchingType.partial);
	}

	public FluidMatcher orderedChildren(List<? extends Tree> children, List<TreeMatcher> matchers, PatternListMatchingType matchingType) {
		return matchChildren(children, matchers, true, matchingType);
	}

	protected FluidMatcher matchChildren(List<? extends Tree> children, List<TreeMatcher> matchers, boolean ordered,
			PatternListMatchingType matchingType) {
		if (!currentMatch) {
			return this;
		}
		if (matchingType == PatternListMatchingType.ignore) {
			return this;
		}
		if (matchingType == PatternListMatchingType.exact && children.size() != matchers.size()) {
			// in this case the two lists need to be of the same size
			// TODO should i check only the normal type matchers!?
			currentMatch = false;
			return this;
		}

		if (matchers.isEmpty()) {
			currentMatch = true;
			return this;
		}
		List<List<MatchingPath>> childrenMatch = ordered ? context.matchOrdered(matchers, children) : context.matchUnordered(matchers, children);

		currentMatch = !childrenMatch.isEmpty();
		return this;
	}

	public boolean done(boolean result) {
		currentMatch = result;
		return done();
	}

	public boolean done() {
		level--;
		// matches - but put in error to view it better
		if (partial) {
			System.out.println(Strings.repeat("  ", level) + "] #" + matcher.getId() + " = " + currentMatch);
		} else {
			System.out.println(Strings.repeat("  ", level) + "<<  " + toString(matcher) + " = " + currentMatch);
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
