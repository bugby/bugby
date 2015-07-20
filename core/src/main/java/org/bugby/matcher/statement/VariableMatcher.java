package org.bugby.matcher.statement;

import javax.lang.model.element.Element;

import org.bugby.api.FluidMatcher;
import org.bugby.api.MatchingContext;
import org.bugby.api.TreeMatcher;
import org.bugby.api.TreeMatcherFactory;
import org.bugby.api.annotation.ModifiersMatching;
import org.bugby.api.annotation.VariableMatching;
import org.bugby.matcher.DefaultTreeMatcher;
import org.bugby.matcher.declaration.ModifiersMatcher;
import org.bugby.matcher.javac.TreeUtils;

import com.sun.source.tree.IdentifierTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.VariableTree;

public class VariableMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final TreeMatcher typeMatcher;
	private final TreeMatcher initMatcher;
	private final TreeMatcher modifiersMatcher;
	private final VariableMatching variableMatching;

	public VariableMatcher(VariableTree patternNode, TreeMatcherFactory factory) {
		super(patternNode);
		Element element = TreeUtils.elementFromDeclaration(patternNode);
		modifiersMatcher = new ModifiersMatcher(element.getAnnotation(ModifiersMatching.class), patternNode.getModifiers(), factory);

		this.variableMatching = element.getAnnotation(VariableMatching.class);

		this.typeMatcher = factory.build(patternNode.getType());
		this.initMatcher = factory.build(patternNode.getInitializer());
	}

	protected boolean isNameMatching() {
		return variableMatching != null ? variableMatching.matchName() : false;
	}

	protected boolean isTypeMatching() {
		return variableMatching != null ? variableMatching.matchType() : true;
	}

	protected boolean isInitializerMatching() {
		return variableMatching != null ? variableMatching.matchInitializer() : true;
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
		if (node instanceof VariableTree) {
			VariableTree mt = (VariableTree) node;

			match.self(modifiersMatcher.matches(mt.getModifiers(), context));

			if (isNameMatching()) {
				match.self(mt.getName().toString().equals(((VariableTree) getPatternNode()).getName().toString()));
			}
			if (isTypeMatching()) {
				match.child(mt.getType(), typeMatcher);
			}
			if (isInitializerMatching()) {
				match.child(mt.getInitializer(), initMatcher);
			}
		} else {
			if (isTypeMatching()) {
				match.child(node, typeMatcher);
			}
		}

		return match.done();
	}

}
