package org.bugby.engine.algorithm;

import java.util.List;

import junit.framework.Assert;

import org.bugby.api.wildcard.MatchingType;
import org.junit.Test;

@SuppressWarnings("unchecked")
public class TestOneLevelMatcher extends CommonMatcherTest {

	private OneLevelMatcher<IndexedValue, Wildcard<IndexedValue>> matcher() {
		// direct matching
		return new OneLevelMatcher<IndexedValue, Wildcard<IndexedValue>>(new NodeMatch<IndexedValue, Wildcard<IndexedValue>>() {
			@Override
			public boolean match(Wildcard<IndexedValue> wildcard, IndexedValue node) {
				return wildcard.match(node);
			}

			@Override
			public MatchingType getMatchingType(Wildcard<IndexedValue> wildcard) {
				return wildcard.getMatchingType();
			}

			@Override
			public void removedNodeFromMatch(Wildcard<IndexedValue> wildcard, IndexedValue node) {
				//

			}
		});
	}

	@Test
	public void testSimpleMatch() {
		OneLevelMatcher<IndexedValue, Wildcard<IndexedValue>> matcher = matcher();
		List<IndexedValue> nodes = values("a", "b", "c");
		List<Wildcard<IndexedValue>> wildcards = DefaultWildcard.build(array("a"));
		assertPositions(positions1(0), matcher.matchOrdered(nodes, wildcards));
	}

	@Test
	public void testTwoMatch() {
		OneLevelMatcher<IndexedValue, Wildcard<IndexedValue>> matcher = matcher();
		List<IndexedValue> nodes = values("a", "b", "c");
		List<Wildcard<IndexedValue>> wildcards = DefaultWildcard.build(array("a", "c"));
		assertPositions(positions1(0, 2), matcher.matchOrdered(nodes, wildcards));
	}

	@Test
	public void testSimpleNotMatch() {
		OneLevelMatcher<IndexedValue, Wildcard<IndexedValue>> matcher = matcher();
		List<IndexedValue> nodes = values("a", "b", "c");
		List<Wildcard<IndexedValue>> wildcards = DefaultWildcard.build(array("x"));
		Assert.assertEquals(0, matcher.matchOrdered(nodes, wildcards).size());
	}

	@Test
	public void testBegin() {
		OneLevelMatcher<IndexedValue, Wildcard<IndexedValue>> matcher = matcher();
		List<IndexedValue> nodes = values("a", "b", "c");
		List<Wildcard<IndexedValue>> wildcards = DefaultWildcard.buildBegin(array("a"));
		assertPositions(positions1(0), matcher.matchOrdered(nodes, wildcards));
	}

	@Test
	public void testBeginWrong() {
		OneLevelMatcher<IndexedValue, Wildcard<IndexedValue>> matcher = matcher();
		List<IndexedValue> nodes = values("a", "b", "c");
		List<Wildcard<IndexedValue>> wildcards = DefaultWildcard.buildBegin(array("b"));
		Assert.assertEquals(0, matcher.matchOrdered(nodes, wildcards).size());
	}

	@Test
	public void testEnd() {
		OneLevelMatcher<IndexedValue, Wildcard<IndexedValue>> matcher = matcher();
		List<IndexedValue> nodes = values("a", "b", "c");
		List<Wildcard<IndexedValue>> wildcards = DefaultWildcard.buildEnd(array("c"));
		assertPositions(positions1(2), matcher.matchOrdered(nodes, wildcards));
	}

	@Test
	public void testEndFalseFirstMatch() {
		OneLevelMatcher<IndexedValue, Wildcard<IndexedValue>> matcher = matcher();
		List<IndexedValue> nodes = values("a", "c", "b", "c");
		List<Wildcard<IndexedValue>> wildcards = DefaultWildcard.buildEnd(array("c"));
		assertPositions(positions1(3), matcher.matchOrdered(nodes, wildcards));
	}

	@Test
	public void testEndWrong() {
		OneLevelMatcher<IndexedValue, Wildcard<IndexedValue>> matcher = matcher();
		List<IndexedValue> nodes = values("a", "b", "c");
		List<Wildcard<IndexedValue>> wildcards = DefaultWildcard.buildEnd(array("b"));
		Assert.assertEquals(0, matcher.matchOrdered(nodes, wildcards).size());
	}

	@Test
	public void testExactMatch() {
		OneLevelMatcher<IndexedValue, Wildcard<IndexedValue>> matcher = matcher();
		List<IndexedValue> nodes = values("a", "b", "c");
		List<Wildcard<IndexedValue>> wildcards = DefaultWildcard.buildBeginEnd(array("a", "b", "c"));
		assertPositions(positions1(0, 1, 2), matcher.matchOrdered(nodes, wildcards));
	}

	@Test
	public void testExactMatchOne() {
		OneLevelMatcher<IndexedValue, Wildcard<IndexedValue>> matcher = matcher();
		List<IndexedValue> nodes = values("a");
		List<Wildcard<IndexedValue>> wildcards = DefaultWildcard.buildBeginEnd(array("a"));
		assertPositions(positions1(0), matcher.matchOrdered(nodes, wildcards));
	}

	@Test
	public void testTwoMatches() {
		OneLevelMatcher<IndexedValue, Wildcard<IndexedValue>> matcher = matcher();
		List<IndexedValue> nodes = values("a", "c", "b", "c");
		List<Wildcard<IndexedValue>> wildcards = DefaultWildcard.build(array("c"));
		assertPositions(positionsMore(1, EOL, 3), matcher.matchOrdered(nodes, wildcards));
	}

	@Test
	public void testTwoMatchesTwoValues() {
		OneLevelMatcher<IndexedValue, Wildcard<IndexedValue>> matcher = matcher();
		List<IndexedValue> nodes = values("a", "c", "b", "c");
		List<Wildcard<IndexedValue>> wildcards = DefaultWildcard.build(array("a", "c"));
		assertPositions(positionsMore(0, 1, EOL, 0, 3), matcher.matchOrdered(nodes, wildcards));
	}

	@Test
	public void testEmpty() {
		OneLevelMatcher<IndexedValue, Wildcard<IndexedValue>> matcher = matcher();
		List<IndexedValue> nodes = values();
		List<Wildcard<IndexedValue>> wildcards = DefaultWildcard.buildEmpty();
		assertPositions(positions1(), matcher.matchOrdered(nodes, wildcards));
	}

	/** -------------- unordered ------------- **/

	@Test
	public void testSimpleMatchUnordered() {
		OneLevelMatcher<IndexedValue, Wildcard<IndexedValue>> matcher = matcher();
		List<IndexedValue> nodes = values("a", "b", "c");
		List<Wildcard<IndexedValue>> wildcards = DefaultWildcard.build(array("a"));
		assertPositions(positions1(0), matcher.matchUnordered(nodes, wildcards));
	}

	@Test
	public void testTwoMatchUnordered() {
		OneLevelMatcher<IndexedValue, Wildcard<IndexedValue>> matcher = matcher();
		List<IndexedValue> nodes = values("a", "b", "c");
		List<Wildcard<IndexedValue>> wildcards = DefaultWildcard.build(array("c", "a"));
		assertPositions(positions1(2, 0), matcher.matchUnordered(nodes, wildcards));
	}

	@Test
	public void testSimpleNotMatchUnordered() {
		OneLevelMatcher<IndexedValue, Wildcard<IndexedValue>> matcher = matcher();
		List<IndexedValue> nodes = values("a", "b", "c");
		List<Wildcard<IndexedValue>> wildcards = DefaultWildcard.build(array("x"));
		Assert.assertEquals(0, matcher.matchUnordered(nodes, wildcards).size());
	}

	@Test
	public void testTwoMatchUnorderedDouble() {
		OneLevelMatcher<IndexedValue, Wildcard<IndexedValue>> matcher = matcher();
		List<IndexedValue> nodes = values("a", "b", "a");
		List<Wildcard<IndexedValue>> wildcards = DefaultWildcard.build(array("a", "b"));
		assertPositions(positionsMore(0, 1, EOL, 2, 1), matcher.matchUnordered(nodes, wildcards));
	}

	@Test
	public void testTwoNotMatchUnordered() {
		OneLevelMatcher<IndexedValue, Wildcard<IndexedValue>> matcher = matcher();
		List<IndexedValue> nodes = values("a", "b", "c");
		List<Wildcard<IndexedValue>> wildcards = DefaultWildcard.build(array("a", "a"));
		Assert.assertEquals(0, matcher.matchUnordered(nodes, wildcards).size());
	}

	@Test
	public void testUnorderedWithPermutations() {
		OneLevelMatcher<IndexedValue, Wildcard<IndexedValue>> matcher = matcher();
		List<IndexedValue> nodes = values("a", "a");
		List<Wildcard<IndexedValue>> wildcards = DefaultWildcard.build(array("a", "a"));
		assertPositions(positionsMore(0, 1, EOL, 1, 0), matcher.matchUnordered(nodes, wildcards));
	}

	@Test
	public void testUnorderedWithPermutations2() {
		OneLevelMatcher<IndexedValue, Wildcard<IndexedValue>> matcher = matcher();
		List<IndexedValue> nodes = values("a", "b", "b");
		List<Wildcard<IndexedValue>> wildcards = DefaultWildcard.build(array("a", "b", "b"));
		assertPositions(positionsMore(0, 1, 2, EOL, 0, 2, 1), matcher.matchUnordered(nodes, wildcards));
	}
}
