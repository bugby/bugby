package org.bugby.engine.matcher;

import java.util.List;

import org.bugby.api.javac.TreeUtils;
import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sun.source.tree.ClassTree;
import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.Tree;

public class CompilationUnitMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final CompilationUnitTree patternNode;
	private final List<TreeMatcher> typeMatchers;

	public CompilationUnitMatcher(CompilationUnitTree patternNode, TreeMatcherFactory factory) {
		this.patternNode = patternNode;
		this.typeMatchers = build(factory, patternNode.getTypeDecls());
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
