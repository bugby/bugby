package org.bugby.wildcard.matcher.code;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.MatchingPath;
import org.bugby.api.wildcard.TreeMatcher;

import com.sun.source.tree.AnnotationTree;
import com.sun.source.tree.Tree;

public class MissingMatcher extends DefaultTreeMatcher {
	private final TreeMatcher annotatedNodeMatcher;

	public MissingMatcher(AnnotationTree annotationNode, Tree annotatedNode, TreeMatcher annotatedNodeMatcher) {
		super(annotationNode);
		this.annotatedNodeMatcher = annotatedNodeMatcher;
	}

	@Override
	public boolean matches(Tree node, MatchingContext context) {
		return annotatedNodeMatcher.matches(node, context);
	}

	@Override
	public List<List<MatchingPath>> endMatching(List<List<MatchingPath>> currentResult, MatchingContext context) {
		if (!currentResult.isEmpty()) {
			//return empty result as there is no match
			return Collections.emptyList();
		}
		//return empty result as there is no match
		List<List<MatchingPath>> result = new ArrayList<List<MatchingPath>>();
		result.add(Collections.singletonList(context.getCurrentMatchingPath()));
		return result;
	}
}
