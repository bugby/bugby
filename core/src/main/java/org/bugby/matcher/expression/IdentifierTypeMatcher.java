package org.bugby.matcher.expression;

import javax.lang.model.element.Element;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeMirror;

import org.bugby.api.FluidMatcher;
import org.bugby.api.MatchingContext;
import org.bugby.api.TreeMatcher;
import org.bugby.api.TreeMatcherFactory;
import org.bugby.matcher.DefaultTreeMatcher;
import org.bugby.matcher.javac.ElementWrapperTree;
import org.bugby.matcher.javac.TreeUtils;

import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.IdentifierTree;
import com.sun.source.tree.MemberSelectTree;
import com.sun.source.tree.PrimitiveTypeTree;
import com.sun.source.tree.Tree;

public class IdentifierTypeMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final TypeMirror patternType;
	private final TreeMatcher typeDefMatcher;
	private final TreeMatcherFactory factory;

	public IdentifierTypeMatcher(IdentifierTree patternNode, TreeMatcherFactory factory) {
		super(patternNode);
		Element element = TreeUtils.elementFromUse(patternNode);
		patternType = element.asType();
		typeDefMatcher = factory.buildForType(patternType.toString());
		this.factory = factory;
	}

	@Override
	public boolean matches(Tree node, MatchingContext context) {
		FluidMatcher match = matching(node, context);
		if (!(node instanceof IdentifierTree) && !(node instanceof PrimitiveTypeTree) && !(node instanceof ElementWrapperTree)
				&& !(node instanceof MemberSelectTree)) {
			return match.done(false);
		}
		if (node instanceof IdentifierTree || node instanceof MemberSelectTree) {
			//0. i need to make the difference between matching a random variable or a variable as required by the SomeCodeMatcher
			//1. check if the assignment was already done (see variables)
			//2. i need to see if i load the corresponding sources
			Element element = TreeUtils.elementFromUse((ExpressionTree) node);
			Tree sourceNodeTypeDef = factory.loadTypeDefinition(element.asType().toString());
			match.self(typeDefMatcher.matches(sourceNodeTypeDef, context));

		} else if (node instanceof PrimitiveTypeTree) {
			PrimitiveTypeTree mt = (PrimitiveTypeTree) node;
			PrimitiveType primitiveType = context.getParsedSource().getTypes().getPrimitiveType(mt.getPrimitiveTypeKind());
			TypeMirror sourceNodeType = context.getParsedSource().getTypes().boxedClass(primitiveType).asType();

			match.self(typeDefMatcher.matches(new ElementWrapperTree(context.getParsedSource().getTypes().asElement(sourceNodeType)), context));
		}
		return match.done();
	}
}
