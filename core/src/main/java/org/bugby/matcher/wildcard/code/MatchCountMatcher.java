package org.bugby.matcher.wildcard.code;

import java.util.Collections;
import java.util.List;

import javax.lang.model.element.Element;

import org.bugby.api.MatchingContext;
import org.bugby.api.MatchingPath;
import org.bugby.api.MatchingValueKey;
import org.bugby.api.TreeMatcher;
import org.bugby.matcher.DefaultTreeMatcher;
import org.bugby.matcher.javac.TreeUtils;
import org.bugby.wildcard.MatchCount;

import com.sun.source.tree.AnnotationTree;
import com.sun.source.tree.ClassTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.VariableTree;

public class MatchCountMatcher extends DefaultTreeMatcher {
	private final MatchingValueKey matchingKey;
	private final int min;
	private final int max;
	private final TreeMatcher annotatedNodeMatcher;

	public MatchCountMatcher(AnnotationTree annotationNode, Tree annotatedNode, TreeMatcher annotatedNodeMatcher) {
		super(annotationNode);
		Element element = getElementFromDeclaration(annotatedNode);
		MatchCount ann = element.getAnnotation(MatchCount.class);
		if (ann == null) {
			throw new RuntimeException("The node is not annotated with MatchCount annotation:" + annotatedNode);
		}
		min = ann.min();
		max = ann.max();
		this.annotatedNodeMatcher = annotatedNodeMatcher;
		matchingKey = new MatchingValueKey("METHOD", "COUNT:" + getId());
	}

	@Override
	public boolean matches(Tree node, MatchingContext context) {
		boolean matched = annotatedNodeMatcher.matches(node, context);

		if (matched) {
			context.putValue(matchingKey, (Integer) context.getValue(matchingKey) + 1);
		}
		return matched;
	}

	@Override
	public void startMatching(boolean ordered, MatchingContext context) {
		context.putValue(matchingKey, 0);
		super.startMatching(ordered, context);
	}

	@Override
	public List<List<MatchingPath>> endMatching(List<List<MatchingPath>> currentResult, MatchingContext context) {
		int count = context.getValue(matchingKey);
		if (count >= min && count <= max) {
			return super.endMatching(currentResult, context);
		}
		// return empty result as there is no match
		return Collections.emptyList();
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