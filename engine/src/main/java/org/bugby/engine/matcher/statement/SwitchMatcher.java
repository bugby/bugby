package org.bugby.engine.matcher.statement;

import java.util.List;

import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.engine.matcher.DefaultMatcher;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sun.source.tree.SwitchTree;
import com.sun.source.tree.Tree;

public class SwitchMatcher extends DefaultMatcher implements TreeMatcher {
	private final SwitchTree patternNode;
	private final TreeMatcher expressionMatcher;
	private final List<TreeMatcher> casesMatchers;

	public SwitchMatcher(SwitchTree patternNode, TreeMatcher expressionMatcher, List<TreeMatcher> casesMatchers) {
		this.patternNode = patternNode;
		this.expressionMatcher = expressionMatcher;
		this.casesMatchers = casesMatchers;
	}

	@Override
	public Multimap<TreeMatcher, Tree> matches(Tree node, MatchingContext context) {
		if (!(node instanceof SwitchTree)) {
			return HashMultimap.create();
		}
		SwitchTree mt = (SwitchTree) node;

		Multimap<TreeMatcher, Tree> result = null;
		result = matchChild(result, node, mt.getExpression(), expressionMatcher, context);
		result = matchUnorderedChildren(result, node, mt.getCases(), casesMatchers, context);

		return result;
	}

}
