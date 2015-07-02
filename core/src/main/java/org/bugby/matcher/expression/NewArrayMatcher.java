package org.bugby.matcher.expression;

import java.util.List;

import org.bugby.api.FluidMatcher;
import org.bugby.api.MatchingContext;
import org.bugby.api.TreeMatcher;
import org.bugby.api.TreeMatcherFactory;
import org.bugby.matcher.DefaultTreeMatcher;

import com.sun.source.tree.NewArrayTree;
import com.sun.source.tree.Tree;

public class NewArrayMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final TreeMatcher typeMatcher;
	private final List<TreeMatcher> dimensionsMatchers;
	private final List<TreeMatcher> initializersMatchers;

	public NewArrayMatcher(NewArrayTree patternNode, TreeMatcherFactory factory) {
		super(patternNode);
		this.typeMatcher = factory.build(patternNode.getType());
		this.dimensionsMatchers = build(factory, patternNode.getDimensions());
		this.initializersMatchers = build(factory, patternNode.getInitializers());
	}

	@Override
	public boolean matches(Tree node, MatchingContext context) {
		FluidMatcher match = matching(node, context);
		if (!(node instanceof NewArrayTree)) {
			return match.done(false);
		}
		NewArrayTree mt = (NewArrayTree) node;

		match.child(mt.getType(), typeMatcher);
		match.orderedChildren(mt.getDimensions(), dimensionsMatchers);
		match.orderedChildren(mt.getInitializers(), initializersMatchers);

		return match.done();
	}

}
