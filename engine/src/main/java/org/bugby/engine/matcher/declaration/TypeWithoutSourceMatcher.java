package org.bugby.engine.matcher.declaration;

import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;

import org.bugby.api.javac.ElementWrapperTree;
import org.bugby.api.javac.TreeUtils;
import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.FluidMatcher;
import org.bugby.api.wildcard.MatchingContext;

import com.sun.source.tree.IdentifierTree;
import com.sun.source.tree.PrimitiveTypeTree;
import com.sun.source.tree.Tree;

public class TypeWithoutSourceMatcher extends DefaultTreeMatcher {
	private final TypeMirror patternType;

	public TypeWithoutSourceMatcher(Tree patternNode, TypeMirror patternType) {
		super(patternNode);
		this.patternType = patternType;
	}

	public TypeMirror getPatternType() {
		return patternType;
	}

	@Override
	public boolean matches(Tree node, MatchingContext context) {
		FluidMatcher match = matching(node, context);
		if (!(node instanceof IdentifierTree) && !(node instanceof PrimitiveTypeTree) && !(node instanceof ElementWrapperTree)) {
			return match.done(false);
		}
		if (node instanceof IdentifierTree) {
			IdentifierTree mt = (IdentifierTree) node;
			Element element = TreeUtils.elementFromUse(mt);
			TypeMirror sourceNodeType = element.asType();
			match.self(context.compatibleTypes(patternType, sourceNodeType));
		} else if (node instanceof ElementWrapperTree) {
			ElementWrapperTree mt = (ElementWrapperTree) node;
			match.self(context.compatibleTypes(patternType, mt.getElement().asType()));
		}
		return match.done();
	}

}
