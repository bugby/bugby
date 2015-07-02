package org.bugby.matcher.wildcard.type;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.bugby.api.FluidMatcher;
import org.bugby.api.MatchingContext;
import org.bugby.api.MatchingPath;
import org.bugby.api.TreeMatcher;
import org.bugby.api.TreeMatcherFactory;
import org.bugby.api.Variables;
import org.bugby.matcher.DefaultTreeMatcher;
import org.bugby.matcher.javac.InternalUtils;

import com.sun.source.tree.ClassTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.VariableTree;

public class DynamicTypeDefinitionMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final TreeMatcher extendsMatcher;
	private final List<TreeMatcher> implementsMatchers;
	private final List<TreeMatcher> typeParametersMatchers;
	private final List<TreeMatcher> methodsMatchers;
	private final List<TreeMatcher> fieldsMatchers;

	public DynamicTypeDefinitionMatcher(ClassTree patternNode, TreeMatcherFactory factory) {
		super(patternNode);
		this.extendsMatcher = factory.build(patternNode.getExtendsClause());
		this.implementsMatchers = build(factory, patternNode.getImplementsClause());
		this.typeParametersMatchers = build(factory, patternNode.getTypeParameters());
		this.methodsMatchers = build(factory, methods(removeSyntheticConstructors(patternNode.getMembers())));
		this.fieldsMatchers = build(factory, fields(patternNode.getMembers()));
		//TODO - I may need to add also static blocks !?
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

	@Override
	public boolean matches(final Tree node, final MatchingContext context) {
		FluidMatcher match = matching(node, context);
		if (!(node instanceof ClassTree)) {
			return match.done(false);
		}
		final ClassTree ct = (ClassTree) node;
		final List<Tree> methods = methods(removeSyntheticConstructors(ct.getMembers()));
		final List<Tree> fields = fields(ct.getMembers());

		//match.self(TreeUtils.elementFromDeclaration((ClassTree) getPatternNode()).equals(TreeUtils.elementFromDeclaration(ct)));

		Callable<Boolean> matchSolution = new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				FluidMatcher solutionMatch = partialMatching(node, context);
				solutionMatch.unorderedChildren(methods, methodsMatchers);
				solutionMatch.child(ct.getExtendsClause(), extendsMatcher);
				solutionMatch.unorderedChildren(ct.getImplementsClause(), implementsMatchers);
				solutionMatch.orderedChildren(ct.getTypeParameters(), typeParametersMatchers);
				return solutionMatch.done();
			}
		};

		if (fieldsMatchers.isEmpty()) {
			try {
				match.self(matchSolution.call());
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			List<List<MatchingPath>> fieldsMatch = context.matchUnordered(fieldsMatchers, fields);
			match.self(Variables.forAllVariables(context, fieldsMatch, matchSolution));
		}

		return match.done();
	}
}
