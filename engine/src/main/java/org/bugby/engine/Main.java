package org.bugby.engine;

import java.io.File;

import org.bugby.api.javac.ParsedSource;
import org.bugby.api.javac.SourceParser;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.engine.matcher.DefaultTreeMatcherFactory;

import com.google.common.collect.Multimap;
import com.sun.source.tree.Tree;

public class Main {

	public static MatchResult check(String patternSource, String source) {
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
		DefaultTreeMatcherFactory matcherFactory = new DefaultTreeMatcherFactory(wildcardDictionary);
		TreeMatcher rootMatcher = matcherFactory.buildFromFile(builtProjectClassLoader, new File(patternSource));
		System.out.println("PATTERN:\n" + rootMatcher);
		System.out.println("-------------------------");

		// 3. parse source file to be checked
		File sourceFile = new File(source);
		ParsedSource parsedSource = SourceParser.parse(sourceFile, builtProjectClassLoader, "UTF-8");

		// 4. apply matcher
		MatchingContext context = new DefaultMatchingContext();
		Multimap<TreeMatcher, Tree> matches = rootMatcher.matches(parsedSource.getCompilationUnitTree(), context);
		// TODO find a good report here

		// System.out.println("FULLMATCH:------------");
		//
		// for (Map.Entry<Tree<WildcardNodeMatcher>, Collection<Node>> entry : matches.asMap().entrySet()) {
		// System.out.println(entry.getKey().getValue() + " on " + entry.getValue());
		// }
		return new MatchResult(parsedSource, matches);
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
