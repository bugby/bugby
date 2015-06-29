package org.bugby.wildcard.matcher.code;

import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.FluidMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;

import com.sun.source.tree.MethodTree;
import com.sun.source.tree.Tree;

/**
 * when used like this <br>
 * public void someCode(){ // ... bla bla // }
 * 
 * @author acraciun
 */
public class SomeCodeMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final MethodTree patternNode;
	private final TreeMatcher bodyMatcher;

	// private final Scope patternScope;

	public SomeCodeMatcher(MethodTree patternNode, TreeMatcherFactory factory) {
		this.patternNode = patternNode;
		this.bodyMatcher = factory.build(patternNode.getBody());
	}

	@Override
	public boolean matches(Tree node, MatchingContext context) {
		FluidMatcher match = matching(node, context);
		// TODO should match intantiation blocks
		if (!(node instanceof MethodTree)) {
			return match.done(false);
		}
		MethodTree mt = (MethodTree) node;

		match.child(mt.getBody(), bodyMatcher);

		return match.done();
	}

	@Override
	public String toString() {
		return "SomeCodeMatcher [patternNode=" + patternNode + ", bodyMatcher=" + bodyMatcher + "]";
	}

}
