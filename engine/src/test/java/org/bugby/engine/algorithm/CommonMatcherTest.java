package org.bugby.engine.algorithm;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommonMatcherTest {
	protected static final int EOL = -1;

	protected static class IndexedValue {

		protected final String value;
		protected final int index;

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
			result = prime * result + (value == null ? 0 : value.hashCode());
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

		@Override
		public String toString() {
			return "{" + index + "/" + value + "}";
		}
	}

	protected List<IndexedValue> values(String... strings) {
		List<IndexedValue> v = new ArrayList<IndexedValue>(strings.length);
		for (int i = 0; i < strings.length; ++i) {
			v.add(new IndexedValue(strings[i], i));
		}
		return v;
	}

	protected IndexedValue[] array(String... strings) {
		return values(strings).toArray(new IndexedValue[strings.length]);
	}

	protected void assertPositions(List<List<Integer>> positions, List<List<IndexedValue>> match) {
		System.out.println(match);
		assertEquals(positions.size(), match.size());
		for (int i = 0; i < positions.size(); ++i) {
			assertEquals(positions.get(i).size(), match.get(i).size());
			for (int j = 0; j < positions.get(i).size(); ++j) {
				assertEquals(positions.get(i).get(j).intValue(), match.get(i).get(j).getIndex());
			}
		}
	}

	protected List<List<Integer>> positions1(Integer... n) {
		return Arrays.asList(Arrays.asList(n));
	}

	protected List<List<Integer>> positionsMore(Integer... n) {
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

	protected String type(String v) {
		// ordered if only lower case, unorderder otherwise
		return v.equals(v.toLowerCase()) ? "order" : "unorder";
	}

}
