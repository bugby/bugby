package org.bugby.matcher.declaration;

import java.util.List;
import java.util.concurrent.Callable;

import javax.lang.model.element.Element;

import org.bugby.api.FluidMatcher;
import org.bugby.api.MatchingContext;
import org.bugby.api.MatchingPath;
import org.bugby.api.PatternListMatchingType;
import org.bugby.api.TreeMatcher;
import org.bugby.api.TreeMatcherFactory;
import org.bugby.api.Variables;
import org.bugby.matcher.DefaultTreeMatcher;
import org.bugby.matcher.javac.TreeUtils;

import com.sun.source.tree.MethodTree;
import com.sun.source.tree.Tree;

public class MethodMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final TreeMatcher modifiersMatcher;
	private final TreeMatcher returnTypeMatcher;
	private final List<TreeMatcher> parametersMatchers;
	private final List<TreeMatcher> typeParametersMatchers;
	private final List<TreeMatcher> throwsMatchers;
	private final TreeMatcher bodyMatcher;
	private final MethodMatching methodMatching;
	private final boolean override;

	public MethodMatcher(MethodTree patternNode, TreeMatcherFactory factory) {
		super(patternNode);
		Element element = TreeUtils.elementFromDeclaration(patternNode);
		modifiersMatcher = new ModifiersMatcher(element.getAnnotation(ModifiersMatching.class), patternNode.getModifiers(), factory);

		this.methodMatching = element.getAnnotation(MethodMatching.class);
		//TODO I should rather look if it's a real override and not rely on the annotation
		this.override = element.getAnnotation(Override.class) != null;

		this.returnTypeMatcher = factory.build(patternNode.getReturnType());
		this.parametersMatchers = build(factory, patternNode.getParameters());
		this.typeParametersMatchers = build(factory, patternNode.getTypeParameters());
		this.throwsMatchers = build(factory, patternNode.getThrows());
		this.bodyMatcher = factory.build(patternNode.getBody());
	}

	protected PatternListMatchingType getTypeParametersMatching() {
		if (override) {
			return PatternListMatchingType.exact;
		}
		return methodMatching != null ? methodMatching.matchTypeParameters() : PatternListMatchingType.partial;
	}

	protected PatternListMatchingType getThrowsMatching() {
		if (override) {
			return PatternListMatchingType.exact;
		}
		return methodMatching != null ? methodMatching.matchThrows() : PatternListMatchingType.partial;
	}

	protected PatternListMatchingType getParametersMatching() {
		if (override) {
			return PatternListMatchingType.exact;
		}
		return methodMatching != null ? methodMatching.matchParameters() : PatternListMatchingType.partial;
	}

	protected boolean isNameMatching() {
		return override || (methodMatching != null ? methodMatching.matchName() : false);
	}

	protected boolean isReturnTypeMatching() {
		//void return is considered as "not present". To check on the result type for voids you need to set the @MethodMatching(matchReturnType=true);
		return override || (methodMatching != null ? methodMatching.matchReturnType() : !isVoidReturn());
	}

	protected boolean isVoidReturn() {
		MethodTree mt = (MethodTree) getPatternNode();
		return "void".equals(mt.getReturnType().toString());
	}

	@Override
	public boolean matches(final Tree node, final MatchingContext context) {
		FluidMatcher match = matching(node, context);
		if (!(node instanceof MethodTree)) {
			return match.done(false);
		}
		final MethodTree mt = (MethodTree) node;

		Callable<Boolean> matchSolution = new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				FluidMatcher solutionMatch = partialMatching(node, context);
				if (isNameMatching()) {
					solutionMatch.self(((MethodTree) getPatternNode()).getName().toString().equals(mt.getName().toString()));
				}
				solutionMatch.self(modifiersMatcher.matches(mt.getModifiers(), context));
				solutionMatch.unorderedChildren(mt.getThrows(), throwsMatchers, getThrowsMatching());
				if (isReturnTypeMatching()) {
					solutionMatch.child(mt.getReturnType(), returnTypeMatcher);
				}
				solutionMatch.orderedChildren(mt.getTypeParameters(), typeParametersMatchers, getTypeParametersMatching());
				solutionMatch.child(mt.getBody(), bodyMatcher);
				return solutionMatch.done();
			}
		};

		if (getParametersMatching() == PatternListMatchingType.exact && parametersMatchers.size() != mt.getParameters().size()) {
			return match.done(false);
		}

		if (parametersMatchers.isEmpty() || getParametersMatching() == PatternListMatchingType.ignore) {
			try {
				match.self(matchSolution.call());
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			List<List<MatchingPath>> paramsMatch = context.matchOrdered(parametersMatchers, mt.getParameters());
			match.self(Variables.forAllVariables(context, paramsMatch, matchSolution));
		}

		return match.done();
	}

	@Override
	public String toString() {
		return "MethodMatcher [method=" + ((MethodTree) getPatternNode()).getName() + ", returnTypeMatcher=" + returnTypeMatcher
				+ ", parametersMatchers=" + parametersMatchers + ", typeParametersMatchers=" + typeParametersMatchers + ", throwsMatchers="
				+ throwsMatchers + ", bodyMatcher=" + bodyMatcher + "]";
	}

}
