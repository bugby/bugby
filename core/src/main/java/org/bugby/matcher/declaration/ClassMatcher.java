package org.bugby.matcher.declaration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.lang.model.element.Element;

import org.bugby.api.FluidMatcher;
import org.bugby.api.MatchingContext;
import org.bugby.api.TreeMatcher;
import org.bugby.api.TreeMatcherFactory;
import org.bugby.api.Variables;
import org.bugby.api.annotation.ModifiersMatching;
import org.bugby.api.annotation.PatternListMatchingType;
import org.bugby.api.annotation.TypeMatching;
import org.bugby.matcher.DefaultTreeMatcher;
import org.bugby.matcher.javac.ElementWrapperTree;
import org.bugby.matcher.javac.InternalUtils;
import org.bugby.matcher.javac.TreeUtils;

import com.sun.source.tree.ClassTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.VariableTree;

public class ClassMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final TreeMatcher modifiersMatcher;
	private final TypeMatching typeMatching;
	private final TreeMatcher extendsMatcher;
	private final List<TreeMatcher> implementsMatchers;
	private final List<TreeMatcher> typeParametersMatchers;
	private final List<TreeMatcher> methodsMatchers;
	private final List<TreeMatcher> fieldsMatchers;

	public ClassMatcher(ClassTree patternNode, TreeMatcherFactory factory) {
		super(patternNode);

		Element element = TreeUtils.elementFromDeclaration(patternNode);
		modifiersMatcher = new ModifiersMatcher(element.getAnnotation(ModifiersMatching.class), patternNode.getModifiers(), factory);
		this.typeMatching = element.getAnnotation(TypeMatching.class);

		this.extendsMatcher = factory.build(patternNode.getExtendsClause());
		this.implementsMatchers = build(factory, patternNode.getImplementsClause());
		this.typeParametersMatchers = build(factory, patternNode.getTypeParameters());
		this.methodsMatchers = build(factory, methods(removeSyntheticConstructors(patternNode.getMembers())));
		this.fieldsMatchers = build(factory, fields(patternNode.getMembers()));
		// TODO - I may need to add also static blocks !?
	}

	private List<Tree> fields(List<? extends Tree> members) {
		List<Tree> filtered = new ArrayList<Tree>(members.size());
		for (Tree member : members) {
			if (member instanceof VariableTree) {
				filtered.add(member);
			}
		}
		return filtered;
	}

	private List<Tree> methods(List<? extends Tree> members) {
		List<Tree> filtered = new ArrayList<Tree>(members.size());
		for (Tree member : members) {
			if (member instanceof MethodTree) {
				filtered.add(member);
			}
		}
		return filtered;
	}

	private List<Tree> removeSyntheticConstructors(List<? extends Tree> members) {
		List<Tree> filtered = new ArrayList<Tree>(members.size());
		for (Tree member : members) {
			if (!InternalUtils.isSyntheticConstructor(member)) {
				filtered.add(member);
			}
		}
		return filtered;
	}

	public TreeMatcher getExtendsMatcher() {
		return extendsMatcher;
	}

	public List<TreeMatcher> getImplementsMatchers() {
		return implementsMatchers;
	}

	public List<TreeMatcher> getTypeParametersMatchers() {
		return typeParametersMatchers;
	}

	protected boolean isNameMatching() {
		return typeMatching != null && typeMatching.matchName();
	}

	protected PatternListMatchingType getFieldsMatching() {
		return typeMatching != null ? typeMatching.matchFields() : PatternListMatchingType.partial;
	}

	protected PatternListMatchingType getMethodsMatching() {
		return typeMatching != null ? typeMatching.matchMethods() : PatternListMatchingType.partial;
	}

	protected PatternListMatchingType getExtendsMatching() {
		return typeMatching != null ? typeMatching.matchExtends() : PatternListMatchingType.partial;
	}

	protected PatternListMatchingType getImplementsMatching() {
		return typeMatching != null ? typeMatching.matchImplements() : PatternListMatchingType.partial;
	}

	protected PatternListMatchingType getTypeParametersMatching() {
		return typeMatching != null ? typeMatching.matchTypeParameters() : PatternListMatchingType.partial;
	}

	@Override
	public boolean matches(final Tree node, final MatchingContext context) {
		FluidMatcher match = matching(node, context);
		if (node instanceof ElementWrapperTree) {
			Element sourceElement = ((ElementWrapperTree) node).getElement();
			Element patternElement = TreeUtils.elementFromDeclaration((ClassTree) getPatternNode());
			return match.done(context.compatibleTypes(patternElement.asType(), sourceElement.asType()));
		}

		if (!(node instanceof ClassTree)) {
			return match.done(false);
		}
		final ClassTree ct = (ClassTree) node;
		final List<Tree> methods = methods(removeSyntheticConstructors(ct.getMembers()));
		final List<Tree> fields = fields(ct.getMembers());

		final Callable<Boolean> matchSolution = new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				FluidMatcher solutionMatch = partialMatching(node, context);
				if (isNameMatching()) {
					solutionMatch.self(TreeUtils.elementFromDeclaration((ClassTree) getPatternNode()).getQualifiedName()
							.equals(TreeUtils.elementFromDeclaration(ct).getQualifiedName()));
				}
				solutionMatch.self(modifiersMatcher.matches(ct.getModifiers(), context));

				solutionMatch.unorderedChildren(methods, methodsMatchers, getMethodsMatching());
				solutionMatch.child(ct.getExtendsClause(), extendsMatcher, getExtendsMatching());

				solutionMatch.unorderedChildren(ct.getImplementsClause(), implementsMatchers, getImplementsMatching());
				solutionMatch.orderedChildren(ct.getTypeParameters(), typeParametersMatchers, getTypeParametersMatching());
				return solutionMatch.done();
			}
		};

		if (getFieldsMatching() == PatternListMatchingType.exact && fieldsMatchers.size() != fields.size()) {
			return match.done(false);
		}

		if (fieldsMatchers.isEmpty() || getFieldsMatching() == PatternListMatchingType.ignore) {
			try {
				match.self(matchSolution.call());
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			final AtomicBoolean foundMatch = new AtomicBoolean(false);
			context.matchUnordered(fieldsMatchers, fields, Variables.variablesForEachSolution(context, matchSolution, foundMatch));
			match.self(foundMatch.get());
		}

		return match.done();
	}

	@Override
	public String toString() {
		return "ClassMatcher [Class=" + ((ClassTree) getPatternNode()).getSimpleName() + ", extendsMatcher=" + extendsMatcher
				+ ", implementsMatchers=" + implementsMatchers + ", typeParametersMatchers=" + typeParametersMatchers + ", membersMatchers="
				+ methodsMatchers + "]";
	}
}
