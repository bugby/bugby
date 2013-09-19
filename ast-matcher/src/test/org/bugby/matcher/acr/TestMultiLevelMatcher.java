package org.bugby.matcher.acr;

import junit.framework.Assert;

import org.bugby.matcher.tree.Tree;
import org.junit.Test;

public class TestMultiLevelMatcher extends CommonMatcherTest {
	private MultiLevelMatcher<IndexedValue, Wildcard<IndexedValue>> matcher() {
		// direct matching
		return new MultiLevelMatcher<IndexedValue, Wildcard<IndexedValue>>(
				new NodeMatch<IndexedValue, Wildcard<IndexedValue>>() {
					@Override
					public boolean match(Wildcard<IndexedValue> wildcard, IndexedValue node) {
						return wildcard.match(node);
					}
				});
	}

	@Test
	public void testSimpleMatch() {
		MultiLevelMatcher<IndexedValue, Wildcard<IndexedValue>> matcher = matcher();
		Tree<IndexedValue> nodes = tree("a", "b", "c");
		Tree<Wildcard<IndexedValue>> wildcards = wtree("a", "b");
		Assert.assertEquals(true, matcher.match(nodes, wildcards));
	}

	@Test
	public void test3LevelMatch() {
		MultiLevelMatcher<IndexedValue, Wildcard<IndexedValue>> matcher = matcher();
		Tree<IndexedValue> nodes = tree("a", tree("b", "d", "e"), "c");
		Tree<Wildcard<IndexedValue>> wildcards = wtree("a", wtree("b", "e"));
		Assert.assertEquals(true, matcher.match(nodes, wildcards));
	}
}
