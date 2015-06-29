package org.bugby.engine.matcher.expression;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeMirror;

import org.bugby.api.javac.TreeUtils;
import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.FluidMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;

import com.sun.source.tree.IdentifierTree;
import com.sun.source.tree.PrimitiveTypeTree;
import com.sun.source.tree.Tree;

public class IdentifierMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final IdentifierTree patternNode;
	private final TypeMirror patternType;

	public IdentifierMatcher(IdentifierTree patternNode, TreeMatcherFactory factory) {
		this.patternNode = patternNode;
		Element element = TreeUtils.elementFromUse(patternNode);
		patternType = element.asType();
	}

	@Override
	public boolean matches(Tree node, MatchingContext context) {
		FluidMatcher match = matching(node, context);
		if (!(node instanceof IdentifierTree) && !(node instanceof PrimitiveTypeTree)) {
			return match.done(false);
		}
		if (node instanceof IdentifierTree) {
			IdentifierTree mt = (IdentifierTree) node;
			Element element = TreeUtils.elementFromUse(mt);
			if (element.getKind() == ElementKind.CLASS) {// TODO interface as well
				TypeMirror sourceNodeType = element.asType();
				match.self(context.compatibleTypes(patternType, sourceNodeType));
			} else {
				match.self(mt.getName().toString().equals(patternNode.getName().toString()));
			}
		} else if (node instanceof PrimitiveTypeTree) {
			PrimitiveTypeTree mt = (PrimitiveTypeTree) node;
			PrimitiveType primitiveType = context.getParsedSource().getTypes().getPrimitiveType(mt.getPrimitiveTypeKind());
			TypeMirror sourceNodeType = context.getParsedSource().getTypes().boxedClass(primitiveType).asType();
			match.self(context.compatibleTypes(patternType, sourceNodeType));
		}
		return match.done();
	}
}
