package org.bugby.wildcard.matcher;

import java.util.Comparator;

import org.bugby.api.wildcard.Correlation;
import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.MethodInvocationTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.Tree;

public class SomeMethodMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final Tree patternNode;
	private final String correlationKey;
	private final Comparator<Tree> correlationComparator;

	public SomeMethodMatcher(Tree patternNode, TreeMatcherFactory factory) {
		this.patternNode = patternNode;
		// tree can be a method call or a method declaration
		Correlation correlation = getCorrelationAnnotation(patternNode);
		correlationKey = getCorrelationKey(correlation);
		correlationComparator = newComparator(correlation);
	}

	private Correlation getCorrelationAnnotation(Tree node) {
		if (node instanceof MethodTree) {
			// MethodWrapper method = ASTNodeData.resolvedMethod(currentPatternSourceNode);
			// if (method != null) {
			// Correlation correlation = method.getAnnotation(Correlation.class);
			// return correlation;
			// }
		}
		return null;
	}

	private String getCorrelationKey(Correlation correlation) {
		if (correlation != null) {
			return correlation.key();
		}
		return null;
	}

	@Override
	public Multimap<TreeMatcher, Tree> matches(Tree node, MatchingContext context) {
		if (!(node instanceof MethodInvocationTree) && !(node instanceof MethodTree)) {
			return HashMultimap.create();
		}
		// if (node instanceof MethodDeclaration) {
		// // TODO put this type of code in a generic way - in more places
		// if (correlationKey != null) {
		// return context.checkCorrelation(correlationKey, node, correlationComparator);
		// }
		// return true;
		// }

		ExpressionTree mt = (ExpressionTree) node;

		Multimap<TreeMatcher, Tree> result = null;
		result = matchSelf(result, mt, true, context);

		return result;
	}

	private Comparator<Tree> newComparator(Correlation correlation) {
		if (correlation == null) {
			return null;
		}
		try {
			return correlation.comparator().newInstance();
		}
		catch (InstantiationException e) {
			throw new RuntimeException(e);
		}
		catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

}
