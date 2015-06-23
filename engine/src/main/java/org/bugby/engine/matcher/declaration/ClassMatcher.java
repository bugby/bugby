package org.bugby.engine.matcher.declaration;

import java.util.ArrayList;
import java.util.List;

import org.bugby.api.javac.InternalUtils;
import org.bugby.api.javac.TreeUtils;
import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.FluidMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;

import com.sun.source.tree.ClassTree;
import com.sun.source.tree.Tree;

public class ClassMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final ClassTree patternNode;
	private final TreeMatcher extendsMatcher;
	private final List<TreeMatcher> implementsMatchers;
	private final List<TreeMatcher> typeParametersMatchers;
	private final List<TreeMatcher> membersMatchers;

	public ClassMatcher(ClassTree patternNode, TreeMatcherFactory factory) {
		this.patternNode = patternNode;
		this.extendsMatcher = factory.build(patternNode.getExtendsClause());
		this.implementsMatchers = build(factory, patternNode.getImplementsClause());
		this.typeParametersMatchers = build(factory, patternNode.getTypeParameters());
		this.membersMatchers = build(factory, removeSyntheticConstructors(patternNode.getMembers()));
	}

	private List<? extends Tree> removeSyntheticConstructors(List<? extends Tree> members) {
		List<Tree> filtered = new ArrayList<Tree>(members.size());
		for (Tree member : members) {
			if (!InternalUtils.isSyntheticConstructor(member)) {
				filtered.add(member);
			}
		}
		return filtered;
	}

	public ClassTree getPatternNode() {
		return patternNode;
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

	public List<TreeMatcher> getMembersMatchers() {
		return membersMatchers;
	}

	@Override
	public boolean matches(Tree node, MatchingContext context) {
		FluidMatcher match = matching(node, context);
		if (!(node instanceof ClassTree)) {
			return match.done(false);
		}
		ClassTree ct = (ClassTree) node;

		match.self(TreeUtils.elementFromDeclaration(patternNode).equals(TreeUtils.elementFromDeclaration(ct)));
		match.unorderedChildren(removeSyntheticConstructors(ct.getMembers()), membersMatchers);
		match.child(ct.getExtendsClause(), extendsMatcher);
		match.unorderedChildren(ct.getImplementsClause(), implementsMatchers);
		match.orderedChildren(ct.getTypeParameters(), typeParametersMatchers);

		return match.done();
	}

	@Override
	public String toString() {
		return "ClassMatcher [Class=" + patternNode.getSimpleName() + ", extendsMatcher=" + extendsMatcher + ", implementsMatchers="
				+ implementsMatchers + ", typeParametersMatchers=" + typeParametersMatchers + ", membersMatchers=" + membersMatchers + "]";
	}
}
