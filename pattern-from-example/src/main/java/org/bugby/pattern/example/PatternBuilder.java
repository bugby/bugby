package org.bugby.pattern.example;

import japa.parser.ast.CompilationUnit;
import japa.parser.ast.Node;
import japa.parser.ast.expr.AnnotationExpr;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.bugby.annotation.IgnoreFromMatching;
import org.bugby.matcher.acr.TreeModel;
import org.bugby.matcher.tree.Tree;
import org.bugby.pattern.example.model.NodeUtils;
import org.bugby.wildcard.api.WildcardNodeMatcher;
import org.bugby.wildcard.api.WildcardNodeMatcherFactory;
import org.bugby.wildcard.api.WildcardPatternBuildContext;
import org.richast.GenerationContext;
import org.richast.RichASTParser;
import org.richast.type.ClassLoaderWrapper;

public class PatternBuilder implements WildcardNodeMatcherFactory {
	private static Set<String> skipAnnotations = new HashSet<String>(Arrays.asList("GoodExample", "GoodExampleTrigger",
			"BadExample", "SuppressWarnings", "Override", "Correlation"));

	private WildcardDictionary wildcardDictionary;

	public WildcardDictionary getWildcardDictionary() {
		return wildcardDictionary;
	}

	public void setWildcardDictionary(WildcardDictionary wildcardDictionary) {
		this.wildcardDictionary = wildcardDictionary;
	}

	public Tree<WildcardNodeMatcher> buildFromFile(ClassLoader builtProjectClassLoader, File file) {
		ClassLoaderWrapper classLoaderWrapper = new ClassLoaderWrapper(builtProjectClassLoader,
				Collections.<String>emptyList(), Collections.<String>emptyList());
		GenerationContext context = new GenerationContext(file);
		CompilationUnit cu = RichASTParser.parseAndResolve(classLoaderWrapper, file, context, "UTF-8");

		ASTTreeModel treeModel = new ASTTreeModel();
		return buildPatternNode(treeModel, cu, null, this, new WildcardPatternBuildContext());
	}

	@Override
	public Tree<WildcardNodeMatcher> buildPatternNode(TreeModel<Node, Node> patternSourceTreeNodeModel,
			Node currentPatternSourceNode, Tree<WildcardNodeMatcher> parentPatternNode,
			WildcardNodeMatcherFactory defaultFactory, WildcardPatternBuildContext buildContext) {
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
				newParentPatternNode = matcherFactory.buildPatternNode(patternSourceTreeNodeModel,
						currentPatternSourceNode, parentPatternNode, this, buildContext);
				return newParentPatternNode;
			}

			// find the matcher
			WildcardNodeMatcher matcher;
			Class<? extends WildcardNodeMatcher> matcherClass = wildcardDictionary.findMatcherClass(baseName);
			if (matcherClass != null) {
				try {
					matcher = matcherClass.newInstance();
				}
				catch (InstantiationException e) {
					throw new RuntimeException(e);
				}
				catch (IllegalAccessException e) {
					throw new RuntimeException(e);
				}
			} else {
				matcher = new DefaultNodeMatcher(currentPatternSourceNode, buildContext.retrieveAnnotations());
			}
			newParentPatternNode = addPatternNode(parentPatternNode, matcher);
		}

		// continue with the children
		for (Node child : patternSourceTreeNodeModel.getChildren(currentPatternSourceNode, false)) {
			buildPatternNode(patternSourceTreeNodeModel, child, newParentPatternNode, defaultFactory, buildContext);
		}
		for (Node child : patternSourceTreeNodeModel.getChildren(currentPatternSourceNode, true)) {
			buildPatternNode(patternSourceTreeNodeModel, child, newParentPatternNode, defaultFactory, buildContext);
		}
		// remove empty virtual nodes
		if (currentPatternSourceNode instanceof VirtualNode && newParentPatternNode.getChildrenCount() == 0) {
			parentPatternNode.removeChild(newParentPatternNode);
			newParentPatternNode = parentPatternNode;
		}

		return newParentPatternNode;
	}

	private Tree<WildcardNodeMatcher> addPatternNode(Tree<WildcardNodeMatcher> parentPatternNode,
			WildcardNodeMatcher matcher) {
		if (parentPatternNode == null) {
			return new Tree<WildcardNodeMatcher>(matcher);
		}
		return parentPatternNode.newChild(matcher);
	}

	protected boolean skip(AnnotationExpr ann) {
		return skipAnnotations.contains(ann.getName().toString())
				|| wildcardDictionary.isAnnotation(ann.getName().toString());
	}

	private boolean skip(Node node) {
		if (node instanceof AnnotationExpr) {
			return skip((AnnotationExpr) node);
		}
		if (NodeUtils.hasAnnotation(node, IgnoreFromMatching.class)) {
			return true;
		}
		return false;
	}

}
