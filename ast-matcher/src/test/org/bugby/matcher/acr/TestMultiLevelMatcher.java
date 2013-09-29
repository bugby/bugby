package org.bugby.matcher.acr;

import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import org.bugby.matcher.tree.Tree;
import org.junit.Test;

public class TestMultiLevelMatcher extends CommonMatcherTest {
	private static class TestNodeDefaultTreeModel extends DefaultTreeModel<IndexedValue> {
		@Override
		public boolean isOrdered(Tree<IndexedValue> node) {
			// ordered if only lower case, unorderder otherwise
			String v = node.getValue().getValue();
			return v.equals(v.toLowerCase());
		}
	}

	private static class TestWildcardDefaultTreeModel extends DefaultTreeModel<Wildcard<IndexedValue>> {
		@Override
		public boolean isOrdered(Tree<Wildcard<IndexedValue>> node) {
			if (((DefaultWildcard<IndexedValue>) node.getValue()).getValue() == null) {
				return true;
			}
			// ordered if only lower case, unorderder otherwise
			String v = ((DefaultWildcard<IndexedValue>) node.getValue()).getValue().getValue();

			return v.equals(v.toLowerCase());
		}
	}

	private MultiLevelMatcher<IndexedValue, Wildcard<IndexedValue>, Tree<IndexedValue>, Tree<Wildcard<IndexedValue>>> matcher() {
		// direct matching
		return new MultiLevelMatcher<IndexedValue, Wildcard<IndexedValue>, Tree<IndexedValue>, Tree<Wildcard<IndexedValue>>>(
				new NodeMatch<IndexedValue, Wildcard<IndexedValue>>() {
					@Override
					public boolean match(Wildcard<IndexedValue> wildcard, IndexedValue node) {
						return wildcard.match(node);
					}

					@Override
					public MatchingType getMatchingType(Wildcard<IndexedValue> wildcard) {
						return wildcard.getMatchingType();
					}

					@Override
					public boolean isFirstChild(List<IndexedValue> nodes, int index) {
						// is ignored
						return false;
					}

					@Override
					public boolean isLastChild(List<IndexedValue> nodes, int index) {
						// is ignored
						return false;
					}

					@Override
					public void removedNodeFromMatch(IndexedValue node) {
						//

					}
				}, new TestNodeDefaultTreeModel(), new TestWildcardDefaultTreeModel());
	}

	@Test
	public void testSimpleMatch() {
		MultiLevelMatcher<IndexedValue, Wildcard<IndexedValue>, Tree<IndexedValue>, Tree<Wildcard<IndexedValue>>> matcher = matcher();
		Tree<IndexedValue> nodes = tree("a", "b", "c");
		Tree<Wildcard<IndexedValue>> wildcards = wtree("a", "b");
		assertTerminalPositions(Arrays.asList(1), "b", matcher.match(nodes, wildcards));
	}

	@Test
	public void test2LevelMatch() {
		MultiLevelMatcher<IndexedValue, Wildcard<IndexedValue>, Tree<IndexedValue>, Tree<Wildcard<IndexedValue>>> matcher = matcher();
		Tree<IndexedValue> nodes = tree("a", tree("b", "d", "e"), "c");
		Tree<Wildcard<IndexedValue>> wildcards = wtree("a", "b");
		assertTerminalPositions(Arrays.asList(1), "b", matcher.match(nodes, wildcards));
	}

	@Test
	public void test3LevelMatch() {
		MultiLevelMatcher<IndexedValue, Wildcard<IndexedValue>, Tree<IndexedValue>, Tree<Wildcard<IndexedValue>>> matcher = matcher();
		Tree<IndexedValue> nodes = tree("a", tree("b", "d", "e"), "c");
		Tree<Wildcard<IndexedValue>> wildcards = wtree("a", wtree("b", "e"));
		assertTerminalPositions(Arrays.asList(3), "e", matcher.match(nodes, wildcards));
	}

	@Test
	public void testDescendantsMatch() {
		MultiLevelMatcher<IndexedValue, Wildcard<IndexedValue>, Tree<IndexedValue>, Tree<Wildcard<IndexedValue>>> matcher = matcher();
		Tree<IndexedValue> nodes = tree("a", tree("b", "d", "e"), "c");
		Tree<Wildcard<IndexedValue>> wildcards = wtree("a", "e");
		assertTerminalPositions(Arrays.asList(3), "e", matcher.match(nodes, wildcards));
	}

	@Test
	public void testDescendantsMatchDifferentParents() {
		MultiLevelMatcher<IndexedValue, Wildcard<IndexedValue>, Tree<IndexedValue>, Tree<Wildcard<IndexedValue>>> matcher = matcher();
		Tree<IndexedValue> nodes = tree("a", tree("b", "d", "e"), "c");
		Tree<Wildcard<IndexedValue>> wildcards = wtree("a", "d", "e");
		assertTerminalPositions(Arrays.asList(3), "e", matcher.match(nodes, wildcards));
	}

	@Test
	public void testDescendantsMatchDifferentParents2() {
		MultiLevelMatcher<IndexedValue, Wildcard<IndexedValue>, Tree<IndexedValue>, Tree<Wildcard<IndexedValue>>> matcher = matcher();
		Tree<IndexedValue> nodes = tree("a", tree("b", tree("d", "g", "h"), "e"));
		Tree<Wildcard<IndexedValue>> wildcards = wtree("a", wtree("d", "h"));
		assertTerminalPositions(Arrays.asList(4), "h", matcher.match(nodes, wildcards));
	}

	@Test
	public void testDescendantsMatchDifferentParentsWrongOrder() {
		MultiLevelMatcher<IndexedValue, Wildcard<IndexedValue>, Tree<IndexedValue>, Tree<Wildcard<IndexedValue>>> matcher = matcher();
		Tree<IndexedValue> nodes = tree("a", tree("b", "d", "e"), "c");
		Tree<Wildcard<IndexedValue>> wildcards = wtree("a", "e", "d");
		Assert.assertEquals(0, matcher.match(nodes, wildcards).size());
	}

	@Test
	public void testDescendantsWithUnordered() {
		MultiLevelMatcher<IndexedValue, Wildcard<IndexedValue>, Tree<IndexedValue>, Tree<Wildcard<IndexedValue>>> matcher = matcher();
		Tree<IndexedValue> nodes = tree("a", tree("B", "d"), tree("C", "e"));
		Tree<Wildcard<IndexedValue>> wildcards = wtree("a", wtree("C", "e"), "B");
		assertTerminalPositions(Arrays.asList(4), "e", matcher.match(nodes, wildcards));
	}

	@Test
	public void testDescendantsWithUnordered2() {
		MultiLevelMatcher<IndexedValue, Wildcard<IndexedValue>, Tree<IndexedValue>, Tree<Wildcard<IndexedValue>>> matcher = matcher();
		Tree<IndexedValue> nodes = tree("a", tree("B", "d"), tree("C", "e"));
		Tree<Wildcard<IndexedValue>> wildcards = wtree("a", "d");
		assertTerminalPositions(Arrays.asList(2), "d", matcher.match(nodes, wildcards));
	}

	@Test
	public void testDescendantsMultiMatch() {
		MultiLevelMatcher<IndexedValue, Wildcard<IndexedValue>, Tree<IndexedValue>, Tree<Wildcard<IndexedValue>>> matcher = matcher();
		Tree<IndexedValue> nodes = tree("a", tree("b", tree("d", "g", "h"), tree("e", "h")));
		Tree<Wildcard<IndexedValue>> wildcards = wtree("a", "h");
		assertTerminalPositions(Arrays.asList(4, 6), "h", matcher.match(nodes, wildcards));
	}

	@Test
	public void testBeginEndWithDescendants() {
		MultiLevelMatcher<IndexedValue, Wildcard<IndexedValue>, Tree<IndexedValue>, Tree<Wildcard<IndexedValue>>> matcher = matcher();
		Tree<IndexedValue> nodes = tree("a", tree("b", tree("x", "c", "d")));
		Tree<Wildcard<IndexedValue>> wildcards = wtree("a", "^", "x", "$");
		assertTerminalPositions(Arrays.asList(2), "x", matcher.match(nodes, wildcards));
	}

	@Test
	public void testBeginEndWithDescendantsWrong() {
		MultiLevelMatcher<IndexedValue, Wildcard<IndexedValue>, Tree<IndexedValue>, Tree<Wildcard<IndexedValue>>> matcher = matcher();
		Tree<IndexedValue> nodes = tree("a", tree("b", tree("x", "c", "d"), "y"));
		Tree<Wildcard<IndexedValue>> wildcards = wtree("a", "^", "x", "$");
		Assert.assertEquals(0, matcher.match(nodes, wildcards).size());
	}
}
