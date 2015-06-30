package org.bugby.wildcard.matcher.method;

import java.util.Comparator;

import org.bugby.api.wildcard.Correlation;
import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;

import com.sun.source.tree.MethodInvocationTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.Tree;

public class SomeMethodMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final String correlationKey;
	private final Comparator<Tree> correlationComparator;

	private final TreeMatcher delegateMatcher;

	public SomeMethodMatcher(Tree patternNode, TreeMatcherFactory factory) {
		super(patternNode);
		// tree can be a method call or a method declaration
		Correlation correlation = getCorrelationAnnotation(patternNode);
		correlationKey = getCorrelationKey(correlation);
		correlationComparator = newComparator(correlation);

		if (patternNode instanceof MethodTree) {
			delegateMatcher = new DynamicMethodMatcher((MethodTree) patternNode, factory);
		} else if (patternNode instanceof MethodInvocationTree) {
			delegateMatcher = new DynamicMethodInvocationMatcher((MethodInvocationTree) patternNode, factory);
		} else {
			throw new RuntimeException("Wrong node type:" + patternNode);
		}

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
	public boolean matches(Tree node, MatchingContext context) {
		return delegateMatcher.matches(node, context);
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
