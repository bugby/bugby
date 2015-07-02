package org.bugby.matcher;

import java.util.List;

import org.bugby.api.FluidMatcher;
import org.bugby.api.MatchingContext;
import org.bugby.api.TreeMatcher;
import org.bugby.api.TreeMatcherFactory;
import org.bugby.matcher.javac.TreeUtils;

import com.sun.source.tree.ClassTree;
import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.Tree;

public class CompilationUnitMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final List<TreeMatcher> typeMatchers;

	public CompilationUnitMatcher(CompilationUnitTree patternNode, TreeMatcherFactory factory) {
		super(patternNode);

		this.typeMatchers = build(factory, patternNode.getTypeDecls());
	}

	public List<TreeMatcher> getTypeMatchers() {
		return typeMatchers;
	}

	@Override
	public boolean matches(Tree node, MatchingContext context) {
		FluidMatcher match = matching(node, context);
		if (!(node instanceof CompilationUnitTree)) {
			return match.done(false);
		}

		return match.orderedChildren(TreeUtils.descendantsOfType(node, ClassTree.class), typeMatchers).done();
	}
}
