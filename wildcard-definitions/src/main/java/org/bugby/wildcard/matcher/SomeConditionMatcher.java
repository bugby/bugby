package org.bugby.wildcard.matcher;

import org.bugby.api.javac.TreeUtils;
import org.bugby.api.javac.TypesUtils;
import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.MethodInvocationTree;
import com.sun.source.tree.Tree;

/**
 * 
 * someCondition()
 * 
 * @author acraciun
 */
public class SomeConditionMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final MethodInvocationTree patternNode;

	public SomeConditionMatcher(MethodInvocationTree patternNode, TreeMatcherFactory factory) {
		this.patternNode = patternNode;
	}

	@Override
	public Multimap<TreeMatcher, Tree> matches(Tree node, MatchingContext context) {
		if (!(node instanceof ExpressionTree)) {
			return HashMultimap.create();
		}
		ExpressionTree mt = (ExpressionTree) node;

		Multimap<TreeMatcher, Tree> result = null;
		result = matchSelf(result, node, TypesUtils.isBooleanType(TreeUtils.elementFromUse(mt).asType()), context);

		return result;
	}

}
