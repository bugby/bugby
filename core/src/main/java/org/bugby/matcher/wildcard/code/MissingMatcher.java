package org.bugby.matcher.wildcard.code;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bugby.api.MatchingContext;
import org.bugby.api.MatchingPath;
import org.bugby.api.TreeMatcher;
import org.bugby.matcher.DefaultTreeMatcher;

import com.sun.source.tree.AnnotationTree;
import com.sun.source.tree.Tree;

public class MissingMatcher extends DefaultTreeMatcher {
	private final TreeMatcher annotatedNodeMatcher;

	public MissingMatcher(AnnotationTree annotationNode, Tree annotatedNode, TreeMatcher annotatedNodeMatcher) {
		super(annotatedNode);
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
