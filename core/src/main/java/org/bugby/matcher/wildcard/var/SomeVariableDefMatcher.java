package org.bugby.matcher.wildcard.var;

import javax.lang.model.element.Element;

import org.bugby.api.FluidMatcher;
import org.bugby.api.MatchingContext;
import org.bugby.api.TreeMatcher;
import org.bugby.api.TreeMatcherFactory;
import org.bugby.matcher.DefaultTreeMatcher;
import org.bugby.matcher.javac.TreeUtils;

import com.sun.source.tree.IdentifierTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.VariableTree;

public class SomeVariableDefMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final Element patternVariableElement;
	private final TreeMatcher initMatcher;
	private final TreeMatcher typeMatcher;

	public SomeVariableDefMatcher(VariableTree patternNode, TreeMatcherFactory factory) {
		super(patternNode);
		patternVariableElement = getVariableElement(patternNode);
		initMatcher = factory.build(patternNode.getInitializer());
		typeMatcher = factory.build(patternNode.getType());
	}

	private Element getVariableElement(Tree node) {
		if (node instanceof VariableTree) {
			return TreeUtils.elementFromDeclaration((VariableTree) node);
		}
		if (node instanceof IdentifierTree) {
			return TreeUtils.elementFromUse((IdentifierTree) node);
		}
		return null;
	}

	@Override
	public boolean matches(Tree node, MatchingContext context) {
		FluidMatcher match = matching(node, context);
		Element sourceVarElement = getVariableElement(node);
		if (sourceVarElement == null) {
			return match.done(false);
		}

		//match.self(context.compatibleTypes(patternVariableElement.asType(), sourceVarElement.asType()));

		if (node instanceof VariableTree) {
			VariableTree mt = (VariableTree) node;
			match.child(mt.getType(), typeMatcher);
			if (initMatcher != null) {
				match.child(mt.getInitializer(), initMatcher);
			}
		} else {
			match.child(node, typeMatcher);
		}

		return match.done();
	}

	@Override
	public String toString() {
		return "SomeVariableMatcher on " + patternVariableElement;
	}
}
