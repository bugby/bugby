package org.bugby.matcher.wildcard.type;

import javax.lang.model.element.TypeElement;

import org.bugby.api.FluidMatcher;
import org.bugby.api.MatchingContext;
import org.bugby.api.TreeMatcher;
import org.bugby.api.TreeMatcherFactory;
import org.bugby.matcher.DefaultTreeMatcher;
import org.bugby.matcher.javac.TreeUtils;

import com.sun.source.tree.IdentifierTree;
import com.sun.source.tree.Tree;

public class DynamicTypeInvocationMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final TreeMatcher typeDefMatcher;

	public DynamicTypeInvocationMatcher(IdentifierTree patternNode, TreeMatcherFactory factory) {
		super(patternNode);
		// i need to load the type's definition here!
		TypeElement element = (TypeElement) TreeUtils.elementFromUse(patternNode);
		//	if (element.getQualifiedName().toString().startsWith("org.bugby")) {
		//TODO here I need the type matcher in fact
		typeDefMatcher = factory.buildForType(element.getQualifiedName().toString());
	}

	@Override
	public boolean matches(Tree node, MatchingContext context) {
		FluidMatcher match = matching(node, context);

		//step1: i need to see if the typeDefMatcher matches node's type
		//i have several cases:
		//direct match

		return true;
	}

}
