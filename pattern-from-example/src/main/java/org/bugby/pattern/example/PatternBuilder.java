package org.bugby.pattern.example;

import japa.parser.ast.CompilationUnit;
import japa.parser.ast.Node;

import java.io.File;
import java.util.Collections;

import org.bugby.matcher.tree.Tree;
import org.bugby.wildcard.WildcardNodeMatcherFromExample;
import org.bugby.wildcard.api.WildcardNodeMatcher;
import org.richast.GenerationContext;
import org.richast.RichASTParser;
import org.richast.type.ClassLoaderWrapper;

public class PatternBuilder {
	private WildcardDictionary wildcardDictionary;
	private Tree<WildcardNodeMatcher> root;

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
		addWildcards(treeModel, null, cu);
		return root;
	}

	private void addWildcards(ASTTreeModel treeModel, Tree<WildcardNodeMatcher> parentPatternNode, Node node) {
		String name = ASTModelBridges.getBridge(node).getMatcherName(node);
		Tree<WildcardNodeMatcher> newParentPatternNode = parentPatternNode;
		if (name != null) {
			newParentPatternNode = addMatcher(parentPatternNode, name, node);
		}
		for (Node child : treeModel.getChildren(node, false)) {
			addWildcards(treeModel, newParentPatternNode, child);
		}
		for (Node child : treeModel.getChildren(node, true)) {
			addWildcards(treeModel, newParentPatternNode, child);
		}

	}

	private Tree<WildcardNodeMatcher> addPatternNode(Tree<WildcardNodeMatcher> parentPatternNode,
			WildcardNodeMatcher matcher) {
		if (parentPatternNode == null) {
			root = new Tree<WildcardNodeMatcher>(matcher);
			return root;
		}
		return parentPatternNode.newChild(matcher);
	}

	public Tree<WildcardNodeMatcher> addMatcher(Tree<WildcardNodeMatcher> parentPatternNode, String name, Node n) {
		// remove the ending digits
		String simpleMethodeName = name.replaceAll("\\d+$", "");
		Class<? extends WildcardNodeMatcher> matcherClass = wildcardDictionary.findMatcherClass(simpleMethodeName);
		if (matcherClass != null) {
			try {
				WildcardNodeMatcher matcher = matcherClass.newInstance();
				if (matcher instanceof WildcardNodeMatcherFromExample) {
					((WildcardNodeMatcherFromExample) matcher).init(n);
				}
				return addPatternNode(parentPatternNode, matcher);
			}
			catch (InstantiationException e) {
				throw new RuntimeException(e);
			}
			catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}
		return addPatternNode(parentPatternNode, new DefaultNodeMatcher(n));
	}

}
