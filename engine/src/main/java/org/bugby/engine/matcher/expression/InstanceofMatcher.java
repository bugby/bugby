package org.bugby.engine.matcher.expression;

import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.FluidMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;

import com.google.common.collect.Multimap;
import com.sun.source.tree.InstanceOfTree;
import com.sun.source.tree.Tree;

public class InstanceofMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final InstanceOfTree patternNode;
	private final TreeMatcher typeMatcher;
	private final TreeMatcher expressionMatcher;

	public InstanceofMatcher(InstanceOfTree patternNode, TreeMatcherFactory factory) {
		this.patternNode = patternNode;
		this.typeMatcher = factory.build(patternNode.getType());
		this.expressionMatcher = factory.build(patternNode.getExpression());
	}

	@Override
	public boolean matches(Tree node, MatchingContext context) {
		FluidMatcher match = matching(node, context);
		if (!(node instanceof InstanceOfTree)) {
			return match.done(false);
		}
		InstanceOfTree mt = (InstanceOfTree) node;

		
		match.child(mt.getType(), typeMatcher);
		match.child(mt.getExpression(), expressionMatcher);

		return match.done();
	}

}
