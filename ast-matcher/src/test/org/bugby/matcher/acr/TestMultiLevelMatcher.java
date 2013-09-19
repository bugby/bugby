package org.bugby.matcher.acr;

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
				}, new TestNodeDefaultTreeModel(), new TestWildcardDefaultTreeModel());
	}

	@Test
	public void testSimpleMatch() {
		MultiLevelMatcher<IndexedValue, Wildcard<IndexedValue>, Tree<IndexedValue>, Tree<Wildcard<IndexedValue>>> matcher = matcher();
		Tree<IndexedValue> nodes = tree("a", "b", "c");
		Tree<Wildcard<IndexedValue>> wildcards = wtree("a", "b");
		Assert.assertEquals(true, matcher.match(nodes, wildcards));
	}

	@Test
	public void test2LevelMatch() {
		MultiLevelMatcher<IndexedValue, Wildcard<IndexedValue>, Tree<IndexedValue>, Tree<Wildcard<IndexedValue>>> matcher = matcher();
		Tree<IndexedValue> nodes = tree("a", tree("b", "d", "e"), "c");
		Tree<Wildcard<IndexedValue>> wildcards = wtree("a", "b");
		Assert.assertEquals(true, matcher.match(nodes, wildcards));
	}

	@Test
	public void test3LevelMatch() {
		MultiLevelMatcher<IndexedValue, Wildcard<IndexedValue>, Tree<IndexedValue>, Tree<Wildcard<IndexedValue>>> matcher = matcher();
		Tree<IndexedValue> nodes = tree("a", tree("b", "d", "e"), "c");
		Tree<Wildcard<IndexedValue>> wildcards = wtree("a", wtree("b", "e"));
		Assert.assertEquals(true, matcher.match(nodes, wildcards));
	}

	@Test
	public void testDescendantsMatch() {
		MultiLevelMatcher<IndexedValue, Wildcard<IndexedValue>, Tree<IndexedValue>, Tree<Wildcard<IndexedValue>>> matcher = matcher();
		Tree<IndexedValue> nodes = tree("a", tree("b", "d", "e"), "c");
		Tree<Wildcard<IndexedValue>> wildcards = wtree("a", "e");
		Assert.assertEquals(true, matcher.match(nodes, wildcards));
	}

	@Test
	public void testDescendantsMatchDifferentParents() {
		MultiLevelMatcher<IndexedValue, Wildcard<IndexedValue>, Tree<IndexedValue>, Tree<Wildcard<IndexedValue>>> matcher = matcher();
		Tree<IndexedValue> nodes = tree("a", tree("b", "d", "e"), "c");
		Tree<Wildcard<IndexedValue>> wildcards = wtree("a", "d", "e");
		Assert.assertEquals(true, matcher.match(nodes, wildcards));
	}

	@Test
	public void testDescendantsMatchDifferentParents2() {
		MultiLevelMatcher<IndexedValue, Wildcard<IndexedValue>, Tree<IndexedValue>, Tree<Wildcard<IndexedValue>>> matcher = matcher();
		Tree<IndexedValue> nodes = tree("a", tree("b", tree("d", "g", "h"), "e"));
		Tree<Wildcard<IndexedValue>> wildcards = wtree("a", wtree("d", "h"));
		Assert.assertEquals(true, matcher.match(nodes, wildcards));
	}

	@Test
	public void testDescendantsMatchDifferentParentsWrongOrder() {
		MultiLevelMatcher<IndexedValue, Wildcard<IndexedValue>, Tree<IndexedValue>, Tree<Wildcard<IndexedValue>>> matcher = matcher();
		Tree<IndexedValue> nodes = tree("a", tree("b", "d", "e"), "c");
		Tree<Wildcard<IndexedValue>> wildcards = wtree("a", "e", "d");
		Assert.assertEquals(false, matcher.match(nodes, wildcards));
	}

	@Test
	public void testDescendantsWithUnordered() {
		MultiLevelMatcher<IndexedValue, Wildcard<IndexedValue>, Tree<IndexedValue>, Tree<Wildcard<IndexedValue>>> matcher = matcher();
		Tree<IndexedValue> nodes = tree("a", tree("B", "d"), tree("C", "e"));
		Tree<Wildcard<IndexedValue>> wildcards = wtree("a", wtree("C", "e"), "B");
		Assert.assertEquals(true, matcher.match(nodes, wildcards));
	}
}
