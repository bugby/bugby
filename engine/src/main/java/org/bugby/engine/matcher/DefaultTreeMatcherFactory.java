package org.bugby.engine.matcher;

import japa.parser.ast.expr.AnnotationExpr;

import java.lang.reflect.Constructor;

import org.bugby.annotation.IgnoreFromMatching;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;
import org.bugby.api.wildcard.WildcardNodeMatcher;
import org.bugby.engine.ASTModelBridge;
import org.bugby.engine.ASTModelBridges;
import org.bugby.engine.WildcardDictionary;
import org.bugby.engine.model.IgnoreBridge;
import org.bugby.engine.model.NodeUtils;

import com.sun.source.tree.Tree;

public class DefaultTreeMatcherFactory implements TreeMatcherFactory {
	private WildcardDictionary wildcardDictionary;

	public WildcardDictionary getWildcardDictionary() {
		return wildcardDictionary;
	}

	public void setWildcardDictionary(WildcardDictionary wildcardDictionary) {
		this.wildcardDictionary = wildcardDictionary;
	}

	private String getMatcherName(Tree patternNode) {
	}

	@Override
	public TreeMatcher build(Tree patternNode) {

		if (skip(patternNode)) {
			return null;
		}
		Class<? extends TreeMatcher> matcherClass = null;
		String name = getMatcherName(patternNode);
		if (name != null) {
			// remove the ending digits
			String baseName = name.replaceAll("\\d+$", "");
			matcherClass = wildcardDictionary.findMatcherClass(baseName);
		}

		if (matcherClass == null) {
			matcherClass = getDefaultMatcherClass(patternNode);
		}

		if (matcherClass == null) {
			return null;
		}

		try {
			// TODO find here compatiable constructor
			Constructor<?> constructor = matcherClass.getDeclaredConstructors()[0];
			return (TreeMatcher) constructor.newInstance(patternNode, this);
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	private Tree<WildcardNodeMatcher> addPatternNode(String nodeType, Tree<WildcardNodeMatcher> parentPatternNode, WildcardNodeMatcher matcher) {
		if (parentPatternNode == null) {
			return new Tree<WildcardNodeMatcher>(matcher);
		}
		return parentPatternNode.newChild(nodeType, matcher);
	}

	protected boolean skip(AnnotationExpr ann) {
		return skipAnnotations.contains(ann.getName().toString()) || wildcardDictionary.isAnnotation(ann.getName().toString());
	}

	private boolean skip(Tree node) {
		ASTModelBridge bridge = ASTModelBridges.getBridge(node);
		if (bridge.getClass().equals(IgnoreBridge.class)) {
			return true;
		}

		if (node instanceof AnnotationExpr) {
			return skip((AnnotationExpr) node);
		}
		if (NodeUtils.hasAnnotation(node, IgnoreFromMatching.class)) {
			return true;
		}

		return false;
	}
}
