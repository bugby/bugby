package org.bugby.pattern.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.bugby.engine.Main;
import org.bugby.engine.MatchResult;

import com.sun.source.tree.Tree;

public class MatchingHelper {
	private static final String BUG_DEF_PATH = "../bug-definitions/src/main/java/org/bugby/bugs";
	private static final String TEST_PATH = "src/test/java/org/bugby/pattern/example";

	public static void assertPmd(String bugFile, String testFile, int line) {
		assertBug("pmd", bugFile, testFile, line);
	}

	public static void assertFindbugs(String bugFile, String testFile, int line) {
		assertBug("findbugs", bugFile, testFile, line);
	}

	public static void assertBug(String type, String bugFile, String testFile, int line) {
		MatchResult matches = Main.check(BUG_DEF_PATH + "/" + type + "/" + bugFile, TEST_PATH + "/" + type + "/" + testFile);
		Tree node = matches.getBestMatch();

		assertNotNull("Expected a best match", node);
		assertEquals("Expected to match at line", line, matches.getParsedSource().getLine(node));
	}
}
