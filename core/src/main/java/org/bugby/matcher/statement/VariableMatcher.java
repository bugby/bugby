package org.bugby.matcher.statement;

import javax.lang.model.element.Element;

import org.bugby.api.FluidMatcher;
import org.bugby.api.MatchingContext;
import org.bugby.api.TreeMatcher;
import org.bugby.api.TreeMatcherFactory;
import org.bugby.matcher.DefaultTreeMatcher;
import org.bugby.matcher.declaration.ModifiersMatcher;
import org.bugby.matcher.declaration.ModifiersMatching;
import org.bugby.matcher.javac.TreeUtils;

import com.sun.source.tree.Tree;
import com.sun.source.tree.VariableTree;

public class VariableMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final TreeMatcher typeMatcher;
	private final TreeMatcher initMatcher;
	private final TreeMatcher modifiersMatcher;

	public VariableMatcher(VariableTree patternNode, TreeMatcherFactory factory) {
		super(patternNode);
		Element element = TreeUtils.elementFromDeclaration(patternNode);
		modifiersMatcher = new ModifiersMatcher(element.getAnnotation(ModifiersMatching.class), patternNode.getModifiers(), factory);

		this.typeMatcher = factory.build(patternNode.getType());
		this.initMatcher = factory.build(patternNode.getInitializer());
	}

	@Override
	public boolean matches(Tree node, MatchingContext context) {
		FluidMatcher match = matching(node, context);
		if (!(node instanceof VariableTree)) {
			return match.done(false);
		}
		VariableTree mt = (VariableTree) node;

		match.self(mt.getName().toString().equals(((VariableTree) getPatternNode()).getName().toString()));
		match.self(modifiersMatcher.matches(mt.getModifiers(), context));
		match.child(mt.getType(), typeMatcher);
		match.child(mt.getInitializer(), initMatcher);

		return match.done();
	}

}
