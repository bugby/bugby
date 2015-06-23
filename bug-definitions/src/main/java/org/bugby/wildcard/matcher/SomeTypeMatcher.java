package org.bugby.wildcard.matcher;

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

public class SomeTypeMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final Tree patternNode;
	private final TreeMatcher extendsMatcher;
	private final List<TreeMatcher> implementsMatchers;
	private final List<TreeMatcher> typeParametersMatchers;
	private final List<TreeMatcher> membersMatchers;

	public SomeTypeMatcher(Tree patternNode, TreeMatcherFactory factory) {
		this.patternNode = patternNode;
		if (patternNode instanceof ClassTree) {
			ClassTree ct = (ClassTree) patternNode;
			this.extendsMatcher = factory.build(ct.getExtendsClause());
			this.implementsMatchers = build(factory, ct.getImplementsClause());
			this.typeParametersMatchers = build(factory, ct.getTypeParameters());
			this.membersMatchers = build(factory, removeSyntheticConstructors(ct.getMembers()));
		} else {
			this.extendsMatcher = null;
			this.implementsMatchers = null;
			this.typeParametersMatchers = null;
			this.membersMatchers = null;
		}
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

	@Override
	public boolean matches(Tree node, MatchingContext context) {
		FluidMatcher match = matching(node, context);
		if (!(node instanceof ClassTree) && !TreeUtils.isTypeTree(node)) {
			return match.done(false);
		}
		ClassTree ct = (ClassTree) node;

		if (patternNode instanceof ClassTree) {
			match.unorderedChildren(removeSyntheticConstructors(ct.getMembers()), membersMatchers);
			match.child(ct.getExtendsClause(), extendsMatcher);
			match.unorderedChildren(ct.getImplementsClause(), implementsMatchers);
			match.unorderedChildren(ct.getTypeParameters(), typeParametersMatchers);
		} else {
			match.self(true);
		}
		return match.done();
	}

	@Override
	public String toString() {
		return "SomeTypeMatcher [Class=" + patternNode.getClass().getName() + ", extendsMatcher=" + extendsMatcher + ", implementsMatchers="
				+ implementsMatchers + ", typeParametersMatchers=" + typeParametersMatchers + ", membersMatchers=" + membersMatchers + "]";
	}

}
