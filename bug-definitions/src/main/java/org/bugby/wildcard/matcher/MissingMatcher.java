package org.bugby.wildcard.matcher;

import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sun.source.tree.AnnotationTree;
import com.sun.source.tree.Tree;

public class MissingMatcher extends DefaultTreeMatcher {
	private final TreeMatcher annotatedNodeMatcher;

	public MissingMatcher(AnnotationTree annotationNode, Tree annotatedNode, TreeMatcher annotatedNodeMatcher) {
		this.annotatedNodeMatcher = annotatedNodeMatcher;
	}

	@Override
	public boolean matches(Tree node, MatchingContext context) {
		return annotatedNodeMatcher.matches(node, context);
	}

	@Override
	public Multimap<TreeMatcher, Tree> endMatching(Multimap<TreeMatcher, Tree> currentResult, MatchingContext context) {
		if (!currentResult.isEmpty()) {
			//return empty result as there is no match
			return HashMultimap.create();
		}
		//return empty result as there is no match
		Multimap<TreeMatcher, Tree> result = HashMultimap.create();
		result.put(annotatedNodeMatcher, context.getCompilationUnitTree());
		return result;
	}

}
