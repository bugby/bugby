package org.bugby.wildcard.matcher.type;

import javax.lang.model.element.TypeElement;

import org.bugby.api.javac.TreeUtils;
import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.FluidMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;

import com.sun.source.tree.IdentifierTree;
import com.sun.source.tree.Tree;

public class DynamicTypeInvocationMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final TreeMatcher typeDefMatcher;

	public DynamicTypeInvocationMatcher(IdentifierTree patternNode, TreeMatcherFactory factory) {
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
