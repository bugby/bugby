package org.bugby.pattern.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;

import org.bugby.Bugby;
import org.bugby.matcher.MatchResult;

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

	public static void assertBug(Class<?> bugClass, Class<?> testClass, int line) {
		MatchResult matches = Bugby.check(sourcePath(bugClass), sourcePath(testClass));
		Tree node = matches.getBestMatch();

		assertNotNull("Expected a best match", node);
		assertEquals("Expected to match at line", line, matches.getParsedSource().getLine(node));
	}

	public static void assertNoBug(Class<?> bugClass, Class<?> testClass) {
		MatchResult matches = Bugby.check(sourcePath(bugClass), sourcePath(testClass));

		assertEquals("Should not match", 0, matches.getMatches().size());
	}
}
