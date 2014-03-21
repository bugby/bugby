package org.bugby.engine;

import japa.parser.ast.CompilationUnit;
import japa.parser.ast.Node;

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.WildcardNodeMatcher;
import org.bugby.matcher.tree.DefaultTreeModel;
import org.bugby.matcher.tree.MatchingType;
import org.bugby.matcher.tree.MultiLevelMatcher;
import org.bugby.matcher.tree.NodeMatch;
import org.bugby.matcher.tree.Tree;
import org.richast.GenerationContext;
import org.richast.RichASTParser;
import org.richast.type.ClassLoaderWrapper;

import com.google.common.collect.Multimap;

public class Main {
	private static CompilationUnit parseSource(ClassLoader builtProjectClassLoader, File file) {
		ClassLoaderWrapper classLoaderWrapper = new ClassLoaderWrapper(builtProjectClassLoader, Collections.<String> emptyList(),
				Collections.<String> emptyList());
		GenerationContext context = new GenerationContext(file);
		return RichASTParser.parseAndResolve(classLoaderWrapper, file, context, "UTF-8");

	}

	private static NodeMatch<Node, WildcardNodeMatcher> nodeMatch(final ASTTreeModel astTreeModel, final MatchingContext context) {
		return new NodeMatch<Node, WildcardNodeMatcher>() {
			@Override
			public boolean match(WildcardNodeMatcher wildcard, Node node) {
				boolean ok = wildcard.matches(astTreeModel, node, context);
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

			@Override
			public void removedNodeFromMatch(Node node) {
				context.clearDataForNode(node);
			}
		};
	}

	public static Multimap<Tree<WildcardNodeMatcher>, Node> check(String patternSource, String source) {
		// 1. read wildcards
		ClassLoader builtProjectClassLoader = Thread.currentThread().getContextClassLoader();
		WildcardDictionary wildcardDictionary = new WildcardDictionary();
		WildcardDictionaryFromFile.addWildcardsFromFile(wildcardDictionary, builtProjectClassLoader, new File(
				"../wildcard-definitions/src/main/java/org/bugby/wildcard/Wildcards.java"));
		WildcardDictionaryFromFile.addWildcardsFromFile(wildcardDictionary, builtProjectClassLoader, new File(
				"../wildcard-definitions/src/main/java/org/bugby/wildcard/SomeType.java"));
		WildcardDictionaryFromFile.addWildcardsFromFile(wildcardDictionary, builtProjectClassLoader, new File(
				"../wildcard-definitions/src/main/java/org/bugby/wildcard/WildcardAnnotations.java"));
		// here add more custom wildcards by dynamic discovery

		// 2. read patterns
		PatternBuilder patternBuilder = new PatternBuilder();
		patternBuilder.setWildcardDictionary(wildcardDictionary);
		Tree<WildcardNodeMatcher> patternRoot = patternBuilder.buildFromFile(builtProjectClassLoader, new File(patternSource));
		System.out.println("PATTERN:\n" + patternRoot);
		System.out.println("-------------------------");

		// 3. parse source file to be checked
		File sourceFile = new File(source);
		CompilationUnit sourceRootNode = parseSource(builtProjectClassLoader, sourceFile);

		// 4. apply matcher
		ASTTreeModel astTreeModel = new ASTTreeModel();
		MatchingContext context = new DefaultMatchingContext();
		MultiLevelMatcher<Node, WildcardNodeMatcher, Node, Tree<WildcardNodeMatcher>> matcher = new MultiLevelMatcher<Node, WildcardNodeMatcher, Node, Tree<WildcardNodeMatcher>>(
				nodeMatch(astTreeModel, context), astTreeModel, new PatternTreeModel());
		Multimap<Tree<WildcardNodeMatcher>, Node> matches = matcher.match(sourceRootNode, patternRoot);
		// TODO find a good report here
		Node maxNode = getBestMatch(matches);

		if (maxNode != null) {
			System.err.println("Found match at: (" + sourceFile.getName() + ":" + maxNode.getBeginLine() + ") ->" + maxNode);
		} else {
			System.out.println("No match was found");
		}

		// System.out.println("FULLMATCH:------------");
		//
		// for (Map.Entry<Tree<WildcardNodeMatcher>, Collection<Node>> entry : matches.asMap().entrySet()) {
		// System.out.println(entry.getKey().getValue() + " on " + entry.getValue());
		// }
		return matches;
	}

	public static Node getBestMatch(Multimap<Tree<WildcardNodeMatcher>, Node> matches) {
		int maxLine = -1;
		Node maxNode = null;
		for (Node node : matches.values()) {
			if (node.getBeginLine() >= maxLine) {
				maxLine = node.getBeginLine();
				maxNode = node;
			}

		}
		return maxNode;
	}

	private static class PatternTreeModel extends DefaultTreeModel<WildcardNodeMatcher> {
		@Override
		public boolean isOrdered(Tree<WildcardNodeMatcher> node, String childType) {
			return node.getValue().isOrdered(childType);
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
