package org.bugby.engine.matcher.declaration;

import java.util.List;

import org.bugby.api.javac.TreeUtils;
import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
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
		this.membersMatchers = build(factory, patternNode.getMembers());
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
	public Multimap<TreeMatcher, Tree> matches(Tree node, MatchingContext context) {
		if (!(node instanceof ClassTree)) {
			return HashMultimap.create();
		}
		ClassTree ct = (ClassTree) node;

		Multimap<TreeMatcher, Tree> result = null;
		result = matchSelf(result, node, TreeUtils.elementFromDeclaration(patternNode).equals(TreeUtils.elementFromDeclaration(ct)), context);
		result = matchUnorderedChildren(result, node, ct.getMembers(), membersMatchers, context);
		result = matchChild(result, node, ct.getExtendsClause(), extendsMatcher, context);
		result = matchUnorderedChildren(result, node, ct.getImplementsClause(), implementsMatchers, context);
		result = matchOrderedChildren(result, node, ct.getTypeParameters(), typeParametersMatchers, context);

		return result;
	}

	@Override
	public String toString() {
		return "ClassMatcher [patternNode=" + patternNode + ", extendsMatcher=" + extendsMatcher + ", implementsMatchers=" + implementsMatchers
				+ ", typeParametersMatchers=" + typeParametersMatchers + ", membersMatchers=" + membersMatchers + "]";
	}
}
