package org.bugby.matcher.declaration;

import javax.lang.model.element.Modifier;

import org.bugby.api.FluidMatcher;
import org.bugby.api.MatchingContext;
import org.bugby.api.TreeMatcher;
import org.bugby.api.TreeMatcherFactory;
import org.bugby.matcher.DefaultTreeMatcher;

import com.sun.source.tree.ModifiersTree;
import com.sun.source.tree.Tree;

public class ModifiersMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final ModifiersMatching matching;

	public ModifiersMatcher(ModifiersMatching matching, ModifiersTree patternNode, TreeMatcherFactory factory) {
		super(patternNode);
		this.matching = matching;
	}

	private boolean check(boolean doCheck, ModifiersTree sourceNode, Modifier... flags) {
		if (!doCheck) {
			return true;
		}
		ModifiersTree pattern = (ModifiersTree) getPatternNode();
		for (Modifier flag : flags) {
			if (pattern.getFlags().contains(flag) != sourceNode.getFlags().contains(flag)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean matches(final Tree node, final MatchingContext context) {
		FluidMatcher match = matching(node, context);
		if (!(node instanceof ModifiersTree)) {
			return match.done(false);
		}
		final ModifiersTree mt = (ModifiersTree) node;
		match.self(check(matching != null ? matching.VISIBILITY() : false, mt, Modifier.PUBLIC, Modifier.PRIVATE, Modifier.PROTECTED));
		match.self(check(matching != null ? matching.ABSTRACT() : false, mt, Modifier.ABSTRACT));
		match.self(check(matching != null ? matching.FINAL() : false, mt, Modifier.FINAL));
		match.self(check(matching != null ? matching.NATIVE() : false, mt, Modifier.NATIVE));
		match.self(check(matching != null ? matching.STATIC() : false, mt, Modifier.STATIC));
		match.self(check(matching != null ? matching.STRICTFP() : false, mt, Modifier.STRICTFP));
		match.self(check(matching != null ? matching.SYNCHRONIZED() : false, mt, Modifier.SYNCHRONIZED));
		match.self(check(matching != null ? matching.TRANSIENT() : false, mt, Modifier.TRANSIENT));
		match.self(check(matching != null ? matching.VOLATILE() : false, mt, Modifier.VOLATILE));

		return match.done();
	}

	@Override
	public String toString() {
		return "ModifiersMatcher [matching=" + matching + "]";
	}

}
