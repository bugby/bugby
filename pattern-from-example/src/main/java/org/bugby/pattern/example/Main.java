package org.bugby.pattern.example;

import japa.parser.ast.CompilationUnit;
import japa.parser.ast.Node;

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.bugby.matcher.acr.DefaultTreeModel;
import org.bugby.matcher.acr.MatchingType;
import org.bugby.matcher.acr.MultiLevelMatcher;
import org.bugby.matcher.acr.NodeMatch;
import org.bugby.matcher.tree.Tree;
import org.bugby.wildcard.api.WildcardNodeMatcher;
import org.richast.GenerationContext;
import org.richast.RichASTParser;
import org.richast.type.ClassLoaderWrapper;

import com.google.common.collect.Multimap;

public class Main {
	private static CompilationUnit parseSource(ClassLoader builtProjectClassLoader, File file) {
		ClassLoaderWrapper classLoaderWrapper = new ClassLoaderWrapper(builtProjectClassLoader,
				Collections.<String>emptyList(), Collections.<String>emptyList());
		GenerationContext context = new GenerationContext(file);
		return RichASTParser.parseAndResolve(classLoaderWrapper, file, context, "UTF-8");

	}

	private static NodeMatch<Node, WildcardNodeMatcher> nodeMatch(final ASTTreeModel astTreeModel) {
		return new NodeMatch<Node, WildcardNodeMatcher>() {
			@Override
			public boolean match(WildcardNodeMatcher wildcard, Node node) {
				boolean ok = wildcard.matches(node);
				if (ok) {
					System.err.println(wildcard + " on " + node.toString() + " = OK");
				} else {
					System.out.println(wildcard + " on " + node.toString());
				}
				return ok;
			}

			@Override
			public MatchingType getMatchingType(WildcardNodeMatcher wildcard) {
				return wildcard.getMatchingType();
			}

			@Override
			public boolean isFirstChild(List<Node> nodes, int index) {
				return astTreeModel.isFirstChild(nodes.get(index));
			}

			@Override
			public boolean isLastChild(List<Node> nodes, int index) {
				return astTreeModel.isLastChild(nodes.get(index));
			}
		};
	}

	public static void check(String patternSource, String source) {
		// 1. read wildcards
		ClassLoader builtProjectClassLoader = Thread.currentThread().getContextClassLoader();
		WildcardDictionary wildcardDictionary = new WildcardDictionary();
		WildcardDictionaryFromFile.addWildcardsFromFile(wildcardDictionary, builtProjectClassLoader, new File(
				"../default-wildcards/src/main/java/org/bugby/wildcard/Wildcards.java"));
		WildcardDictionaryFromFile.addWildcardsFromFile(wildcardDictionary, builtProjectClassLoader, new File(
				"../default-wildcards/src/main/java/org/bugby/wildcard/SomeType.java"));
		// here add more custom wildcards by dynamic discovery

		// 2. read patterns
		PatternBuilder patternBuilder = new PatternBuilder();
		patternBuilder.setWildcardDictionary(wildcardDictionary);
		Tree<WildcardNodeMatcher> patternRoot = patternBuilder.buildFromFile(builtProjectClassLoader, new File(
				patternSource));
		System.out.println("PATTERN:\n" + patternRoot);
		System.out.println("-------------------------");

		// 3. parse source file to be checked
		File sourceFile = new File(source);
		CompilationUnit sourceRootNode = parseSource(builtProjectClassLoader, sourceFile);

		// 4. apply matcher
		ASTTreeModel astTreeModel = new ASTTreeModel();
		MultiLevelMatcher<Node, WildcardNodeMatcher, Node, Tree<WildcardNodeMatcher>> matcher = new MultiLevelMatcher<Node, WildcardNodeMatcher, Node, Tree<WildcardNodeMatcher>>(
				nodeMatch(astTreeModel), astTreeModel, new PatternTreeModel());
		Multimap<Tree<WildcardNodeMatcher>, Node> matches = matcher.match(sourceRootNode, patternRoot);
		// TODO find a good report here
		int maxLine = -1;
		Node maxNode = null;
		for (Node node : matches.values()) {
			if (node.getBeginLine() >= maxLine) {
				maxLine = node.getBeginLine();
				maxNode = node;
			}

		}
		if (maxNode != null) {
			System.err.println("Found match at: (" + sourceFile.getName() + ":" + maxNode.getBeginLine() + ") ->"
					+ maxNode);
		} else {
			System.out.println("No match was found");
		}
	}

	private static class PatternTreeModel extends DefaultTreeModel<WildcardNodeMatcher> {
		@Override
		public boolean isOrdered(Tree<WildcardNodeMatcher> node) {
			return node.getValue().isOrdered();
		}

	}

	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("Usage: Main <pathToPatternFile> <pathToSourceFileToCheck>");
			return;
		}

		// see MainTest for some examples
		check(args[0], args[1]);
	}
}
