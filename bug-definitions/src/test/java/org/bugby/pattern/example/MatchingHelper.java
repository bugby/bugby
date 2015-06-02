package org.bugby.pattern.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;

import org.bugby.engine.Bugby;
import org.bugby.engine.MatchResult;

import com.sun.source.tree.Tree;

public class MatchingHelper {
	private static final String BUG_DEF_PATH = "src/main/java/";
	private static final String TEST_PATH = "src/test/java/";

	private static String sourceFileName(Class<?> clz) {
		return clz.getName().replace(".class", "").replace('.', File.separatorChar) + ".java";
	}

	public static void assertBug(Class<?> bugClass, Class<?> testClass, int line) {
		MatchResult matches = Bugby.check(BUG_DEF_PATH + "/" + sourceFileName(bugClass), TEST_PATH + "/" + sourceFileName(testClass));
		Tree node = matches.getBestMatch();

		assertNotNull("Expected a best match", node);
		assertEquals("Expected to match at line", line, matches.getParsedSource().getLine(node));
	}

	public static void assertNoBug(Class<?> bugClass, Class<?> testClass) {
		MatchResult matches = Bugby.check(BUG_DEF_PATH + "/" + sourceFileName(bugClass), TEST_PATH + "/" + sourceFileName(testClass));

		assertEquals("Should not match", 0, matches.getMatches().size());
	}
}
