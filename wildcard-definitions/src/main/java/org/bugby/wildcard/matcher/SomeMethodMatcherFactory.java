package org.bugby.wildcard.matcher;

import japa.parser.ast.Node;
import japa.parser.ast.body.MethodDeclaration;

import java.util.Comparator;

import org.bugby.api.wildcard.Correlation;
import org.bugby.api.wildcard.WildcardNodeMatcher;
import org.bugby.api.wildcard.WildcardNodeMatcherFactory;
import org.bugby.matcher.tree.Tree;
import org.bugby.matcher.tree.TreeModel;
import org.richast.node.ASTNodeData;
import org.richast.type.MethodWrapper;

public class SomeMethodMatcherFactory extends DefaultMatcherFactory {

	private Comparator<Node> newComparator(Correlation correlation) {
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

	@Override
	protected WildcardNodeMatcher buildPatternNodeOnly(TreeModel<Node, Node> patternSourceTreeNodeModel,
			Node currentPatternSourceNode, Tree<WildcardNodeMatcher> parentPatternNode,
			WildcardNodeMatcherFactory defaultFactory) {
		// deal here with annotations

		String correlationKey = null;
		Comparator<Node> correlationComparator = null;
		if (currentPatternSourceNode instanceof MethodDeclaration) {
			MethodWrapper method = ASTNodeData.resolvedMethod(currentPatternSourceNode);
			if (method != null) {
				Correlation correlation = method.getAnnotation(Correlation.class);
				if (correlation != null) {
					correlationKey = correlation.key();
					correlationComparator = newComparator(correlation);
				}
			}
		}
		return new SomeMethodMatcher(currentPatternSourceNode, correlationKey, correlationComparator);
	}
}
