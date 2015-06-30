package org.bugby.wildcard.matcher.code;

import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.FluidMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;

import com.sun.source.tree.Tree;

public class LastMatcher extends DefaultTreeMatcher implements TreeMatcher {

	public LastMatcher(Tree patternNode, TreeMatcherFactory factory) {
		super(patternNode);
		// WildcardPatternBuildContext buildContext
		// buildContext.pushAnnotationNode((MethodCallExpr) currentPatternSourceNode);
	}

	@Override
	public boolean matches(Tree node, MatchingContext context) {
		FluidMatcher match = matching(node, context);
		// TODO Auto-generated method stub
		return false;
	}

}
