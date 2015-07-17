package org.bugby.matcher.declaration;

import java.util.List;

import org.bugby.api.FluidMatcher;
import org.bugby.api.MatchingContext;
import org.bugby.api.TreeMatcher;
import org.bugby.api.TreeMatcherFactory;
import org.bugby.matcher.DefaultTreeMatcher;

import com.sun.source.tree.AnnotationTree;
import com.sun.source.tree.Tree;

public class AnnotationMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final TreeMatcher typeMatcher;
	private final List<TreeMatcher> argumentsMatcher;

	public AnnotationMatcher(AnnotationTree patternNode, TreeMatcherFactory factory) {
		super(patternNode);
		typeMatcher = factory.build(patternNode.getAnnotationType());
		argumentsMatcher = build(factory, patternNode.getArguments());
	}

	@Override
	public boolean matches(final Tree node, final MatchingContext context) {
		FluidMatcher match = matching(node, context);
		if (!(node instanceof AnnotationTree)) {
			return match.done(false);
		}
		final AnnotationTree mt = (AnnotationTree) node;
		match.child(mt.getAnnotationType(), typeMatcher);
		match.unorderedChildren(mt.getArguments(), argumentsMatcher);

		return match.done();
	}

	@Override
	public String toString() {
		return "AnnotationMatcher [typeMatcher=" + typeMatcher + ", argumentsMatcher=" + argumentsMatcher + "]";
	}

}
