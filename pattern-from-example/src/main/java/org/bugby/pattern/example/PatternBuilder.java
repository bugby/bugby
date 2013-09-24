package org.bugby.pattern.example;

import japa.parser.ast.CompilationUnit;
import japa.parser.ast.Node;

import java.io.File;
import java.util.Collections;

import org.bugby.matcher.acr.TreeModel;
import org.bugby.matcher.tree.Tree;
import org.bugby.wildcard.api.WildcardNodeMatcher;
import org.bugby.wildcard.api.WildcardNodeMatcherFactory;
import org.richast.GenerationContext;
import org.richast.RichASTParser;
import org.richast.type.ClassLoaderWrapper;

public class PatternBuilder implements WildcardNodeMatcherFactory {
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
		// cu.accept(new PatternFromExampleVisitor(), this);
		// System.out.println(matchers);
		ASTTreeModel treeModel = new ASTTreeModel();
		return buildPatternNode(treeModel, cu, null, this);
	}

	@Override
	public Tree<WildcardNodeMatcher> buildPatternNode(TreeModel<Node, Node> patternSourceTreeNodeModel,
			Node currentPatternSourceNode, Tree<WildcardNodeMatcher> parentPatternNode,
			WildcardNodeMatcherFactory defaultFactory) {
		String name = ASTModelBridges.getBridge(currentPatternSourceNode).getMatcherName(currentPatternSourceNode);
		Tree<WildcardNodeMatcher> newParentPatternNode = parentPatternNode;

		if (name != null) {
			// remove the ending digits
			String baseName = name.replaceAll("\\d+$", "");
			// try first the factory
			WildcardNodeMatcherFactory matcherFactory = wildcardDictionary.findMatcherFactory(baseName);
			if (matcherFactory != null) {
				return matcherFactory.buildPatternNode(patternSourceTreeNodeModel, currentPatternSourceNode,
						parentPatternNode, this);
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
				matcher = new DefaultNodeMatcher(currentPatternSourceNode);
			}
			newParentPatternNode = addPatternNode(parentPatternNode, matcher);
		}

		// continue with the children
		for (Node child : patternSourceTreeNodeModel.getChildren(currentPatternSourceNode, false)) {
			buildPatternNode(patternSourceTreeNodeModel, child, newParentPatternNode, defaultFactory);
		}
		for (Node child : patternSourceTreeNodeModel.getChildren(currentPatternSourceNode, true)) {
			buildPatternNode(patternSourceTreeNodeModel, child, newParentPatternNode, defaultFactory);
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

}
