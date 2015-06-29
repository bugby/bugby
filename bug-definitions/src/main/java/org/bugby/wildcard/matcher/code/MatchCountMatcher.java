package org.bugby.wildcard.matcher.code;

import javax.lang.model.element.Element;

import org.bugby.api.javac.TreeUtils;
import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.MatchingValueKey;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.wildcard.MatchCount;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sun.source.tree.AnnotationTree;
import com.sun.source.tree.ClassTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.VariableTree;

public class MatchCountMatcher extends DefaultTreeMatcher {
	private final static MatchingValueKey MATCHING_KEY = new MatchingValueKey("METHOD", "COUNT");
	private final int min;
	private final int max;
	private final TreeMatcher annotatedNodeMatcher;

	public MatchCountMatcher(AnnotationTree annotationNode, Tree annotatedNode, TreeMatcher annotatedNodeMatcher) {
		Element element = getElementFromDeclaration(annotatedNode);
		MatchCount ann = element.getAnnotation(MatchCount.class);
		if (ann == null) {
			throw new RuntimeException("The node is not annotated with MatchCount annotation:" + annotatedNode);
		}
		min = ann.min();
		max = ann.max();
		this.annotatedNodeMatcher = annotatedNodeMatcher;
	}

	@Override
	public boolean matches(Tree node, MatchingContext context) {
		boolean matched = annotatedNodeMatcher.matches(node, context);

		if (matched) {
			context.putValue(MATCHING_KEY, (Integer) context.getValue(MATCHING_KEY) + 1);
		}
		return matched;
	}

	@Override
	public void startMatching(boolean ordered, MatchingContext context) {
		context.putValue(MATCHING_KEY, 0);
		super.startMatching(ordered, context);
	}

	@Override
	public Multimap<TreeMatcher, Tree> endMatching(Multimap<TreeMatcher, Tree> currentResult, MatchingContext context) {
		int count = context.getValue(MATCHING_KEY);
		if (count >= min && count <= max) {
			return super.endMatching(currentResult, context);
		}
		//return empty result as there is no match
		return HashMultimap.create();
	}

	private static Element getElementFromDeclaration(Tree annotatedNode) {
		if (annotatedNode instanceof MethodTree) {
			return TreeUtils.elementFromDeclaration((MethodTree) annotatedNode);
		}
		if (annotatedNode instanceof VariableTree) {
			return TreeUtils.elementFromDeclaration((VariableTree) annotatedNode);
		}
		if (annotatedNode instanceof ClassTree) {
			return TreeUtils.elementFromDeclaration((ClassTree) annotatedNode);
		}
		throw new RuntimeException("Received a tree node without annotation possible:" + annotatedNode);
	}
}
