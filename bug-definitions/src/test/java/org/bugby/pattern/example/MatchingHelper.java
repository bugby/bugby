package org.bugby.pattern.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.bugby.Bugby;
import org.bugby.api.TreeMatcher;
import org.bugby.api.TreeMatcherFactory;
import org.bugby.matcher.MatchResult;
import org.bugby.matcher.javac.source.ProjectFileSourceParser;
import org.bugby.matcher.javac.source.ReflectionSourceParser;
import org.bugby.matcher.javac.source.SourceParser;

import com.google.common.base.Charsets;
import com.sun.source.tree.Tree;

public class MatchingHelper {
	private static final String[] SOURCE_PATHS = {"src/main/java", "src/test/java"};
	private static final String BUG_DEF_PATH = "src/main/java/";
	private static final String TEST_PATH = "src/test/java/";

	private static String sourceFileName(Class<?> clz) {
		return clz.getName().replace(".class", "").replace('.', File.separatorChar) + ".java";
	}

	private static String sourcePath(Class<?> cls) {
		String fileName = sourceFileName(cls);
		for (String p : SOURCE_PATHS) {
			File f = new File(p, fileName);
			if (f.exists()) {
				return f.getAbsolutePath();
			}
		}
		throw new RuntimeException("Cannot find file:" + fileName);
	}

	private static MatchResult match(Class<?> bugClass, Class<?> testClass) {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		String sourceEncoding = Charsets.UTF_8.name();
		List<SourceParser> sourceParsers = Arrays.<SourceParser> asList(
			new ProjectFileSourceParser(sourceEncoding, "../core", classLoader),
			new ProjectFileSourceParser(sourceEncoding, classLoader),
			new ReflectionSourceParser(sourceEncoding, classLoader)
				);

		TreeMatcherFactory matcherFactory = Bugby.newTreeMatcherFactory(classLoader, sourceParsers);
		TreeMatcher rootMatcher = matcherFactory.buildForType(bugClass.getName());
		matcherFactory.dumpMatcher(rootMatcher);
		System.out.println("-------------------------");

		MatchResult matches = matcherFactory.match(rootMatcher, matcherFactory.parseSource(testClass.getName()));
		return matches;
	}

	public static void assertBug(Class<?> bugClass, Class<?> testClass, int line) {
		MatchResult matches = match(bugClass, testClass);
		Tree node = matches.getBestMatch();

		assertNotNull("Expected a best match", node);
		assertEquals("Expected to match at line", line, matches.getParsedSource().getLine(node));
	}

	public static void assertNoBug(Class<?> bugClass, Class<?> testClass) {
		MatchResult matches = match(bugClass, testClass);
		assertEquals("Should not match", 0, matches.getMatches().size());
	}
}
