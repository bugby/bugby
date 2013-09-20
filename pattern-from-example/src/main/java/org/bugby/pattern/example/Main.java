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

public class Main {
	private static CompilationUnit parseSource(ClassLoader builtProjectClassLoader, File file) {
		ClassLoaderWrapper classLoaderWrapper = new ClassLoaderWrapper(builtProjectClassLoader,
				Collections.<String>emptyList(), Collections.<String>emptyList());
		GenerationContext context = new GenerationContext(file);
		return RichASTParser.parseAndResolve(classLoaderWrapper, file, context, "UTF-8");

	}

	private static NodeMatch<Node, WildcardNodeMatcher> nodeMatch() {
		return new NodeMatch<Node, WildcardNodeMatcher>() {
			@Override
			public boolean match(WildcardNodeMatcher wildcard, Node node) {
				System.out.println(wildcard + " on " + node.toString() + " =  " + wildcard.matches(node));
				return wildcard.matches(node);
			}

			@Override
			public MatchingType getMatchingType(WildcardNodeMatcher wildcard) {
				return wildcard.getMatchingType();
			}
		};
	}

	public static void main(String[] args) {
		// 1. read wildcards
		ClassLoader builtProjectClassLoader = Thread.currentThread().getContextClassLoader();
		WildcardDictionary wildcardDictionary = new WildcardDictionary();
		WildcardDictionaryFromFile.addWildcardsFromFile(wildcardDictionary, builtProjectClassLoader, new File(
				"../default-wildcards/src/main/java/org/bugby/wildcard/Wildcards.java"));
		// here add more custom wildcards by dynamic discovery

		// 2. read patterns
		PatternBuilder patternBuilder = new PatternBuilder();
		patternBuilder.setWildcardDictionary(wildcardDictionary);
		Tree<WildcardNodeMatcher> patternRoot = patternBuilder.buildFromFile(builtProjectClassLoader, new File(
				"../default-examples/src/main/java/org/bugby/bugs/pmd/CollapsibleIfStatements.java"));
		System.out.println("PATTERN:\n" + patternRoot);
		System.out.println("-------------------------");

		// 3. parse source file to be checked
		File sourceFile = new File("src/main/java/org/bugby/pattern/example/test/CollapsibleIfStatementsCheck.java");
		CompilationUnit sourceRootNode = parseSource(builtProjectClassLoader, sourceFile);

		// 4. apply matcher
		MultiLevelMatcher<Node, WildcardNodeMatcher, Node, Tree<WildcardNodeMatcher>> matcher = new MultiLevelMatcher<Node, WildcardNodeMatcher, Node, Tree<WildcardNodeMatcher>>(
				nodeMatch(), new ASTTreeModel(), new PatternTreeModel());
		List<Node> matches = matcher.match(sourceRootNode, patternRoot);
		for (Node match : matches) {
			System.out.println("Found match at: " + sourceFile.getPath() + ":" + match.getBeginLine());
		}
		if (matches.isEmpty()) {
			System.out.println("No match was found");
		}
	}

	private static class PatternTreeModel extends DefaultTreeModel<WildcardNodeMatcher> {
		@Override
		public boolean isOrdered(Tree<WildcardNodeMatcher> node) {
			return node.getValue().isOrdered();
		}

	}
}
