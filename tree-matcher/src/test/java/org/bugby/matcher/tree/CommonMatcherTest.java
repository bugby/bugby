package org.bugby.matcher.tree;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Multimap;

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

	protected void assertTerminalPositions(List<Integer> positions, String value,
			Multimap<Tree<Wildcard<IndexedValue>>, Tree<IndexedValue>> match) {
		List<IndexedValue> terminalMatch = new ArrayList<IndexedValue>();
		for (Map.Entry<Tree<Wildcard<IndexedValue>>, Tree<IndexedValue>> entry : match.entries()) {
			if (((DefaultWildcard<IndexedValue>) entry.getKey().getValue()).getValue().getValue().equals(value)) {
				terminalMatch.add(entry.getValue().getValue());
			}
		}
		assertEquals(positions.size(), terminalMatch.size());

		for (IndexedValue tm : terminalMatch) {
			assertTrue(positions.contains(tm.getIndex()));
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

	protected Tree<IndexedValue> tree(String root, Object... children) {
		Tree<IndexedValue> parent = new Tree<IndexedValue>(new IndexedValue(root, 0));
		int index = 0;
		for (Object element : children) {
			if (element instanceof String) {
				parent.newChild(type((String) element), new IndexedValue((String) element, ++index));
			} else {
				Tree<IndexedValue> child = (Tree<IndexedValue>) element;
				Tree<IndexedValue> newChild = parent.newChild(type(child.getValue().getValue()), new IndexedValue(child.getValue().getValue(),
						++index));
				index = addChildren(newChild, child, index);
			}
		}
		return parent;
	}

	private int addChildren(Tree<IndexedValue> newParent, Tree<IndexedValue> oldParent, int index) {
		for (Map.Entry<String, Collection<Tree<IndexedValue>>> entry : oldParent.getChildren().asMap().entrySet()) {
			List<Tree<IndexedValue>> children = (List<Tree<IndexedValue>>) entry.getValue();
			for (int i = 0; i < children.size(); ++i) {
				Tree<IndexedValue> newChild = newParent.newChild(entry.getKey(),
						new IndexedValue(children.get(i).getValue().getValue(), ++index));
				index = addChildren(newChild, children.get(i), index);
			}
		}

		return index;
	}

	private <T> void addChildren(Tree<T> newParent, Tree<T> oldParent) {
		for (Map.Entry<String, Collection<Tree<T>>> entry : oldParent.getChildren().asMap().entrySet()) {
			List<Tree<T>> children = (List<Tree<T>>) entry.getValue();
			for (int i = 0; i < children.size(); ++i) {
				Tree<T> newChild = newParent.newChild(entry.getKey(), children.get(i).getValue());
				addChildren(newChild, children.get(i));
			}
		}

	}

	protected Tree<Wildcard<IndexedValue>> wtree(String root, Object... children) {
		Tree<Wildcard<IndexedValue>> parent = new Tree<Wildcard<IndexedValue>>(new DefaultWildcard<IndexedValue>(new IndexedValue(root, 0)));
		for (int i = 0; i < children.length; ++i) {
			if (children[i] instanceof String) {
				String s = (String) children[i];
				if (s.equals("^")) {
					parent.newChild("order", (DefaultWildcard<IndexedValue>) DefaultWildcard.BEGIN);
				} else if (s.equals("$")) {
					parent.newChild("order", (DefaultWildcard<IndexedValue>) DefaultWildcard.END);
				} else {
					parent.newChild(type(s), new DefaultWildcard<IndexedValue>(new IndexedValue(s, i)));
				}
			} else {
				Tree<Wildcard<IndexedValue>> child = (Tree<Wildcard<IndexedValue>>) children[i];
				String key = ((DefaultWildcard<IndexedValue>) child.getValue()).getValue().getValue();
				Tree<Wildcard<IndexedValue>> newChild = parent.newChild(type(key), new DefaultWildcard<IndexedValue>(new IndexedValue(key, i)));
				addChildren(newChild, child);
			}
		}
		return parent;
	}
}
