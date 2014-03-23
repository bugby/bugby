package org.bugby.engine.matcher;

import java.util.List;

import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.engine.javac.TreeUtils;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;
import com.sun.source.tree.ClassTree;
import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.Tree;

public class CompilationUnitMatcher extends DefaultMatcher implements TreeMatcher {
	private final CompilationUnitTree patternNode;
	private final List<TreeMatcher> typeMatchers;

	public CompilationUnitMatcher(CompilationUnitTree patternNode, List<TreeMatcher> typeMatchers) {
		this.patternNode = patternNode;
		this.typeMatchers = ImmutableList.copyOf(typeMatchers);
	}

	public CompilationUnitTree getPatternNode() {
		return patternNode;
	}

	public List<TreeMatcher> getTypeMatchers() {
		return typeMatchers;
	}

	@Override
	public Multimap<TreeMatcher, Tree> matches(Tree node, MatchingContext context) {
		if (!(node instanceof CompilationUnitTree)) {
			return HashMultimap.create();
		}

		return matchOrderedChildren(null, node, TreeUtils.descendantsOfType(node, ClassTree.class), typeMatchers, context);
	}
}
