package org.bugby.engine.matcher.expression;

import javax.lang.model.element.Element;
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

public class PrimitiveTypeMatcher extends DefaultTreeMatcher implements TreeMatcher {
	public PrimitiveTypeMatcher(PrimitiveTypeTree patternNode, TreeMatcherFactory factory) {
		super(patternNode);
	}

	@Override
	public boolean matches(Tree node, MatchingContext context) {
		FluidMatcher match = matching(node, context);
		if (!(node instanceof PrimitiveTypeTree) && !(node instanceof IdentifierTree)) {
			return match.done(false);
		}
		if (node instanceof PrimitiveTypeTree) {
			PrimitiveTypeTree mt = (PrimitiveTypeTree) node;
			match.self(mt.getPrimitiveTypeKind() == ((PrimitiveTypeTree) getPatternNode()).getPrimitiveTypeKind());
		} else {
			PrimitiveType patternType =
					context.getParsedSource().getTypes().getPrimitiveType(((PrimitiveTypeTree) getPatternNode()).getPrimitiveTypeKind());
			Element sourceNodeElement = TreeUtils.elementFromUse((IdentifierTree) node);
			TypeMirror sourceNodeType = sourceNodeElement.asType();
			match.self(context.compatibleTypes(patternType, sourceNodeType));
		}
		return match.done();
	}
}
