package org.bugby.engine;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.bugby.api.javac.ParsedSource;
import org.bugby.api.javac.SourceParser;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.engine.matcher.DefaultTreeMatcherFactory;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sun.source.tree.Tree;

public class Bugby {

	private static String toString(Object obj) {
		return obj.getClass().getSimpleName() + "@" + Integer.toHexString(System.identityHashCode(obj));
	}

	public static void dumpMatcher(String indent, TreeMatcher matcher) {
		Field[] fields = matcher.getClass().getDeclaredFields();
		try {
			for (Field field : fields) {
				field.setAccessible(true);
				// simple field
				if (TreeMatcher.class.isAssignableFrom(field.getType())) {
					TreeMatcher fieldValue = (TreeMatcher) field.get(matcher);
					if (fieldValue != null) {
						System.out.println(indent + field.getName() + " = " + toString(fieldValue));
						dumpMatcher(indent + "  ", fieldValue);
					}
					continue;
				}
				// list field
				if (List.class.isAssignableFrom(field.getType())) {// TODO check list of tree matcher
					List<TreeMatcher> fieldValue = (List<TreeMatcher>) field.get(matcher);
					if (fieldValue != null && !fieldValue.isEmpty()) {
						int i = 0;
						for (TreeMatcher f : fieldValue) {
							System.out.println(indent + field.getName() + "[" + i + "] = " + toString(f));
							++i;
							dumpMatcher(indent + "  ", f);
						}
					}
					continue;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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
		dumpMatcher("", rootMatcher);
		System.out.println("-------------------------");

		// 3. parse source file to be checked
		File sourceFile = new File(source);
		ParsedSource parsedSource = SourceParser.parse(sourceFile, builtProjectClassLoader, "UTF-8");

		// 4. apply matcher
		MatchingContext context = new DefaultMatchingContext(parsedSource);
		boolean ok = rootMatcher.matches(parsedSource.getCompilationUnitTree(), context);
		Multimap<TreeMatcher, Tree> matches = context.getMatches();
		if (!ok) {
			matches = HashMultimap.create();
		}

		System.out.println("FULLMATCH:------------");

		for (Map.Entry<TreeMatcher, Collection<Tree>> entry : matches.asMap().entrySet()) {
			System.out.println(entry.getKey().getClass() + " on " + entry.getValue());
		}
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
