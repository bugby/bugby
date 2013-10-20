package org.bugby.wildcard.matcher;

import japa.parser.ast.Node;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.expr.MethodCallExpr;

import java.util.Comparator;

import org.bugby.matcher.acr.MatchingType;
import org.bugby.matcher.acr.TreeModel;
import org.bugby.wildcard.api.MatchingContext;
import org.bugby.wildcard.api.WildcardNodeMatcher;

public class SomeMethodMatcher implements WildcardNodeMatcher {
	private final boolean ordered;
	private final String correlationKey;
	private final Comparator<Node> correlationComparator;

	public SomeMethodMatcher(Node nodeFromExample, String correlationKey, Comparator<Node> correlationComparator) {
		ordered = (nodeFromExample instanceof MethodCallExpr);
		this.correlationKey = correlationKey;
		this.correlationComparator = correlationComparator;
	}

	@Override
	public boolean matches(TreeModel<Node, Node> treeModel, Node node, MatchingContext context) {
		if (node instanceof MethodDeclaration) {
			// TODO put this type of code in a generic way - in more places
			if (correlationKey != null) {
				return context.checkCorrelation(correlationKey, node, correlationComparator);
			}
			return true;
		}
		return node instanceof MethodCallExpr || node instanceof MethodDeclaration;
	}

	@Override
	public boolean isOrdered() {
		return ordered;
	}

	@Override
	public MatchingType getMatchingType() {
		return MatchingType.normal;
	}

}
