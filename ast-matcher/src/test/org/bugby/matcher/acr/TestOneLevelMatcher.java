package org.bugby.matcher.acr;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

@SuppressWarnings("unchecked")
public class TestOneLevelMatcher {
	private static class IndexedValue {

		private final String value;
		private final int index;

		public IndexedValue(String value, int index) {
			this.value = value;
			this.index = index;
		}

		public String getValue() {
			return value;
		}

		public int getIndex() {
			return index;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((value == null) ? 0 : value.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			IndexedValue other = (IndexedValue) obj;
			if (value == null) {
				if (other.value != null) {
					return false;
				}
			} else if (!value.equals(other.value)) {
				return false;
			}
			return true;
		}
	}

	private List<IndexedValue> values(String... strings) {
		List<IndexedValue> v = new ArrayList<IndexedValue>(strings.length);
		for (int i = 0; i < strings.length; ++i) {
			v.add(new IndexedValue(strings[i], i));
		}
		return v;
	}

	private IndexedValue[] array(String... strings) {
		return values(strings).toArray(new IndexedValue[strings.length]);
	}

	private void assertPositions(List<List<Integer>> positions, List<List<IndexedValue>> match) {
		assertEquals(positions.size(), match.size());
		for (int i = 0; i < positions.size(); ++i) {
			assertEquals(positions.get(i).size(), match.get(i).size());
			for (int j = 0; j < positions.get(i).size(); ++j) {
				assertEquals(positions.get(i).get(j).intValue(), match.get(i).get(j).getIndex());
			}
		}
	}

	private List<List<Integer>> positions1(Integer... n) {
		return Arrays.asList(Arrays.asList(n));
	}

	private List<List<Integer>> positionsMore(Integer... n) {
		List<List<Integer>> ret = new ArrayList<List<Integer>>();
		List<Integer> crt = new ArrayList<Integer>();
		ret.add(crt);
		for (Integer x : n) {
			if (x.intValue() < 0) {
				crt = new ArrayList<Integer>();
				ret.add(crt);
			} else {
				crt.add(x);
			}
		}

		return ret;
	}

	@Test
	public void testSimpleMatch() {
		OneLevelMatcher matcher = new OneLevelMatcher();
		List<IndexedValue> nodes = values("a", "b", "c");
		List<Wildcard<IndexedValue>> wildcards = DefaultWildcard.build(array("a"));
		assertPositions(positions1(0), matcher.matchOrdered(nodes, wildcards));
	}

	@Test
	public void testTwoMatch() {
		OneLevelMatcher matcher = new OneLevelMatcher();
		List<IndexedValue> nodes = values("a", "b", "c");
		List<Wildcard<IndexedValue>> wildcards = DefaultWildcard.build(array("a", "c"));
		assertPositions(positions1(0, 2), matcher.matchOrdered(nodes, wildcards));
	}

	@Test
	public void testSimpleNotMatch() {
		OneLevelMatcher matcher = new OneLevelMatcher();
		List<IndexedValue> nodes = values("a", "b", "c");
		List<Wildcard<IndexedValue>> wildcards = DefaultWildcard.build(array("x"));
		Assert.assertEquals(0, matcher.matchOrdered(nodes, wildcards).size());
	}

	@Test
	public void testBegin() {
		OneLevelMatcher matcher = new OneLevelMatcher();
		List<IndexedValue> nodes = values("a", "b", "c");
		List<Wildcard<IndexedValue>> wildcards = DefaultWildcard.buildBegin(array("a"));
		assertPositions(positions1(0), matcher.matchOrdered(nodes, wildcards));
	}

	@Test
	public void testBeginWrong() {
		OneLevelMatcher matcher = new OneLevelMatcher();
		List<IndexedValue> nodes = values("a", "b", "c");
		List<Wildcard<IndexedValue>> wildcards = DefaultWildcard.buildBegin(array("b"));
		Assert.assertEquals(0, matcher.matchOrdered(nodes, wildcards).size());
	}

	@Test
	public void testEnd() {
		OneLevelMatcher matcher = new OneLevelMatcher();
		List<IndexedValue> nodes = values("a", "b", "c");
		List<? extends Wildcard<IndexedValue>> wildcards = DefaultWildcard.buildEnd(array("c"));
		assertPositions(positions1(2), matcher.matchOrdered(nodes, wildcards));
	}

	@Test
	public void testEndFalseFirstMatch() {
		OneLevelMatcher matcher = new OneLevelMatcher();
		List<IndexedValue> nodes = values("a", "c", "b", "c");
		List<? extends Wildcard<IndexedValue>> wildcards = DefaultWildcard.buildEnd(array("c"));
		assertPositions(positions1(3), matcher.matchOrdered(nodes, wildcards));
	}

	@Test
	public void testEndWrong() {
		OneLevelMatcher matcher = new OneLevelMatcher();
		List<IndexedValue> nodes = values("a", "b", "c");
		List<? extends Wildcard<IndexedValue>> wildcards = DefaultWildcard.buildEnd(array("b"));
		Assert.assertEquals(0, matcher.matchOrdered(nodes, wildcards).size());
	}

	@Test
	public void testExactMatch() {
		OneLevelMatcher matcher = new OneLevelMatcher();
		List<IndexedValue> nodes = values("a", "b", "c");
		List<? extends Wildcard<IndexedValue>> wildcards = DefaultWildcard.buildBeginEnd(array("a", "b", "c"));
		assertPositions(positions1(0, 1, 2), matcher.matchOrdered(nodes, wildcards));
	}

	@Test
	public void testExactMatchOne() {
		OneLevelMatcher matcher = new OneLevelMatcher();
		List<IndexedValue> nodes = values("a");
		List<? extends Wildcard<IndexedValue>> wildcards = DefaultWildcard.buildBeginEnd(array("a"));
		assertPositions(positions1(0), matcher.matchOrdered(nodes, wildcards));
	}

	@Test
	public void testTwoMatches() {
		OneLevelMatcher matcher = new OneLevelMatcher();
		List<IndexedValue> nodes = values("a", "c", "b", "c");
		List<? extends Wildcard<IndexedValue>> wildcards = DefaultWildcard.build(array("c"));
		assertPositions(positionsMore(1, -1, 3), matcher.matchOrdered(nodes, wildcards));
	}

	@Test
	public void testTwoMatchesTwoValues() {
		OneLevelMatcher matcher = new OneLevelMatcher();
		List<IndexedValue> nodes = values("a", "c", "b", "c");
		List<? extends Wildcard<IndexedValue>> wildcards = DefaultWildcard.build(array("a", "c"));
		assertPositions(positionsMore(0, 1, -1, 0, 3), matcher.matchOrdered(nodes, wildcards));
	}

	/** -------------- unordered ------------- **/

	@Test
	public void testSimpleMatchUnordered() {
		OneLevelMatcher matcher = new OneLevelMatcher();
		List<String> nodes = Arrays.asList("a", "b", "c");
		List<? extends Wildcard<String>> wildcards = DefaultWildcard.build("a");
		Assert.assertEquals(true, matcher.matchUnordered(nodes, wildcards));
	}

	@Test
	public void testTwoMatchUnordered() {
		OneLevelMatcher matcher = new OneLevelMatcher();
		List<String> nodes = Arrays.asList("a", "b", "c");
		List<? extends Wildcard<String>> wildcards = DefaultWildcard.build("c", "a");
		Assert.assertEquals(true, matcher.matchUnordered(nodes, wildcards));
	}

	@Test
	public void testSimpleNotMatchUnordered() {
		OneLevelMatcher matcher = new OneLevelMatcher();
		List<String> nodes = Arrays.asList("a", "b", "c");
		List<? extends Wildcard<String>> wildcards = DefaultWildcard.build("x");
		Assert.assertEquals(false, matcher.matchUnordered(nodes, wildcards));
	}

	@Test
	public void testTwoNotMatchUnordered() {
		OneLevelMatcher matcher = new OneLevelMatcher();
		List<String> nodes = Arrays.asList("a", "b", "c");
		List<? extends Wildcard<String>> wildcards = DefaultWildcard.build("a", "a");
		Assert.assertEquals(false, matcher.matchUnordered(nodes, wildcards));
	}
}
