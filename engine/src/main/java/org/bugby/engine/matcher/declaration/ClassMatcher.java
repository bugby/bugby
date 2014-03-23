package org.bugby.engine.matcher.declaration;

import java.util.List;

import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.engine.javac.TreeUtils;
import org.bugby.engine.matcher.DefaultMatcher;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sun.source.tree.ClassTree;
import com.sun.source.tree.Tree;

public class ClassMatcher extends DefaultMatcher implements TreeMatcher {
	private final ClassTree patternNode;
	private final TreeMatcher extendsMatcher;
	private final List<TreeMatcher> implementsMatchers;
	private final List<TreeMatcher> typeParametersMatchers;
	private final List<TreeMatcher> membersMatchers;

	public ClassMatcher(ClassTree patternNode, TreeMatcher extendsMatcher, List<TreeMatcher> implementsMatchers,
			List<TreeMatcher> typeParametersMatchers, List<TreeMatcher> membersMatchers) {
		this.patternNode = patternNode;
		this.extendsMatcher = extendsMatcher;
		this.implementsMatchers = implementsMatchers;
		this.typeParametersMatchers = typeParametersMatchers;
		this.membersMatchers = membersMatchers;
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
}
