package org.bugby.matcher.wildcard.code;

import org.bugby.api.FluidMatcher;
import org.bugby.api.MatchingContext;
import org.bugby.api.TreeMatcher;
import org.bugby.api.TreeMatcherFactory;
import org.bugby.matcher.DefaultTreeMatcher;

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
