package org.bugby.engine.matcher.statement;

import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.FluidMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;

import com.sun.source.tree.Tree;
import com.sun.source.tree.VariableTree;

public class VariableMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final TreeMatcher typeMatcher;
	private final TreeMatcher initMatcher;

	public VariableMatcher(VariableTree patternNode, TreeMatcherFactory factory) {
		super(patternNode);

		this.typeMatcher = factory.build(patternNode.getType());
		this.initMatcher = factory.build(patternNode.getInitializer());
	}

	@Override
	public boolean matches(Tree node, MatchingContext context) {
		FluidMatcher match = matching(node, context);
		if (!(node instanceof VariableTree)) {
			return match.done(false);
		}
		VariableTree mt = (VariableTree) node;

		match.self(mt.getName().toString().equals(((VariableTree) getPatternNode()).getName().toString()));
		match.child(mt.getType(), typeMatcher);
		match.child(mt.getInitializer(), initMatcher);

		return match.done();
	}

}
