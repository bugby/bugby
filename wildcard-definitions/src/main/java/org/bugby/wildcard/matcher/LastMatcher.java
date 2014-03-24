package org.bugby.wildcard.matcher;

import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;

import com.google.common.collect.Multimap;
import com.sun.source.tree.Tree;

public class LastMatcher extends DefaultTreeMatcher implements TreeMatcher {

	public LastMatcher(Tree patternNode, TreeMatcherFactory factory) {
		// WildcardPatternBuildContext buildContext
		// buildContext.pushAnnotationNode((MethodCallExpr) currentPatternSourceNode);
	}

	@Override
	public Multimap<TreeMatcher, Tree> matches(Tree node, MatchingContext context) {
		// TODO Auto-generated method stub
		return null;
	}

}
