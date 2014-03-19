package org.bugby.engine;

import japa.parser.ast.CompilationUnit;
import japa.parser.ast.Node;
import japa.parser.ast.expr.AnnotationExpr;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bugby.annotation.IgnoreFromMatching;
import org.bugby.api.wildcard.WildcardNodeMatcher;
import org.bugby.api.wildcard.WildcardNodeMatcherFactory;
import org.bugby.api.wildcard.WildcardPatternBuildContext;
import org.bugby.engine.model.IgnoreBridge;
import org.bugby.engine.model.NodeUtils;
import org.bugby.matcher.tree.Tree;
import org.bugby.matcher.tree.TreeModel;
import org.richast.GenerationContext;
import org.richast.RichASTParser;
import org.richast.type.ClassLoaderWrapper;

public class PatternBuilder implements WildcardNodeMatcherFactory {
	private static Set<String> skipAnnotations = new HashSet<String>(Arrays.asList("GoodExample", "GoodExampleTrigger", "BadExample",
			"SuppressWarnings", "Override", "Correlation"));

	private WildcardDictionary wildcardDictionary;

	public WildcardDictionary getWildcardDictionary() {
		return wildcardDictionary;
	}

	public void setWildcardDictionary(WildcardDictionary wildcardDictionary) {
		this.wildcardDictionary = wildcardDictionary;
	}

	public Tree<WildcardNodeMatcher> buildFromFile(ClassLoader builtProjectClassLoader, File file) {
		ClassLoaderWrapper classLoaderWrapper = new ClassLoaderWrapper(builtProjectClassLoader, Collections.<String> emptyList(),
				Collections.<String> emptyList());
		GenerationContext context = new GenerationContext(file);
		CompilationUnit cu = RichASTParser.parseAndResolve(classLoaderWrapper, file, context, "UTF-8");

		ASTTreeModel treeModel = new ASTTreeModel();
		return buildPatternNode(treeModel, "default", cu, null, this, new WildcardPatternBuildContext());
	}

	@Override
	public Tree<WildcardNodeMatcher> buildPatternNode(TreeModel<Node, Node> patternSourceTreeNodeModel, String currentPatternSourceNodeType,
			Node currentPatternSourceNode, Tree<WildcardNodeMatcher> parentPatternNode, WildcardNodeMatcherFactory defaultFactory,
			WildcardPatternBuildContext buildContext) {
		Tree<WildcardNodeMatcher> newParentPatternNode = parentPatternNode;

		if (skip(currentPatternSourceNode)) {
			return newParentPatternNode;
		}

		String name = ASTModelBridges.getBridge(currentPatternSourceNode).getMatcherName(currentPatternSourceNode);
		if (name != null) {
			// remove the ending digits
			String baseName = name.replaceAll("\\d+$", "");
			// try first the factory
			WildcardNodeMatcherFactory matcherFactory = wildcardDictionary.findMatcherFactory(baseName);
			if (matcherFactory != null) {
				newParentPatternNode = matcherFactory.buildPatternNode(patternSourceTreeNodeModel, currentPatternSourceNodeType,
						currentPatternSourceNode, parentPatternNode, this, buildContext);
				return newParentPatternNode;
			}

			// find the matcher
			WildcardNodeMatcher matcher;
			Class<? extends WildcardNodeMatcher> matcherClass = wildcardDictionary.findMatcherClass(baseName);
			if (matcherClass != null) {
				try {
					matcher = matcherClass.newInstance();
				} catch (InstantiationException e) {
					throw new RuntimeException(e);
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e);
				}
			} else {
				matcher = new DefaultNodeMatcher(currentPatternSourceNode, buildContext.retrieveAnnotations());
			}
			newParentPatternNode = addPatternNode(currentPatternSourceNodeType, parentPatternNode, matcher);
		}

		// continue with the children
		for (Map.Entry<String, Collection<Node>> entry : patternSourceTreeNodeModel.getChildren(currentPatternSourceNode, false).asMap()
				.entrySet()) {
			for (Node child : entry.getValue()) {
				buildPatternNode(patternSourceTreeNodeModel, entry.getKey(), child, newParentPatternNode, defaultFactory, buildContext);
			}
		}

		for (Map.Entry<String, Collection<Node>> entry : patternSourceTreeNodeModel.getChildren(currentPatternSourceNode, true).asMap()
				.entrySet()) {
			for (Node child : entry.getValue()) {
				buildPatternNode(patternSourceTreeNodeModel, entry.getKey(), child, newParentPatternNode, defaultFactory, buildContext);
			}
		}
		// remove empty virtual nodes
		if (currentPatternSourceNode instanceof VirtualNode && newParentPatternNode.getChildrenCount() == 0) {
			parentPatternNode.removeChild(currentPatternSourceNodeType, newParentPatternNode);
			newParentPatternNode = parentPatternNode;
		}

		return newParentPatternNode;
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

	private boolean skip(Node node) {
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
