package org.bugby.matcher.declaration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;

import org.bugby.api.FluidMatcher;
import org.bugby.api.MatchingContext;
import org.bugby.api.TreeMatcher;
import org.bugby.api.TreeMatcherFactory;
import org.bugby.api.annotation.ModifiersMatching;
import org.bugby.api.annotation.PatternConfig;
import org.bugby.matcher.DefaultTreeMatcher;
import org.bugby.matcher.javac.ElementUtils;
import org.bugby.matcher.javac.TreeUtils;

import com.sun.source.tree.AnnotationTree;
import com.sun.source.tree.ModifiersTree;
import com.sun.source.tree.Tree;

public class ModifiersMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private static final Set<String> IGNORED_ANNOTATIONS = new HashSet<>(Arrays.asList(Override.class.getName(),
			SuppressWarnings.class.getName()));

	private final ModifiersMatching matching;
	private final List<TreeMatcher> annotationsMatchers;

	public ModifiersMatcher(ModifiersMatching matching, ModifiersTree patternNode, TreeMatcherFactory factory) {
		super(patternNode);
		this.matching = matching;
		this.annotationsMatchers = build(factory, filterAnnotations(patternNode.getAnnotations()));
	}

	private List<? extends Tree> filterAnnotations(List<? extends AnnotationTree> annotations) {
		List<AnnotationTree> filtered = new ArrayList<>(annotations.size());
		for (AnnotationTree ann : annotations) {
			Element annTypeElement = TreeUtils.elementFromUse(ann.getAnnotationType());
			if (IGNORED_ANNOTATIONS.contains(annTypeElement.toString())) {
				continue;
			}
			boolean isPatternConfig = ElementUtils.getAnnotationMirror(annTypeElement, PatternConfig.class) != null;
			if (!isPatternConfig) {
				filtered.add(ann);
			}
		}

		return filtered;
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
		match.unorderedChildren(mt.getAnnotations(), annotationsMatchers);

		return match.done();
	}

	@Override
	public String toString() {
		return "ModifiersMatcher [matching=" + matching + "]";
	}

}
