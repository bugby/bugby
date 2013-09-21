package org.bugby.matcher.acr;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bugby.matcher.tree.Tree;

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
		assertEquals(positions.size(), match.size());
		for (int i = 0; i < positions.size(); ++i) {
			assertEquals(positions.get(i).size(), match.get(i).size());
			for (int j = 0; j < positions.get(i).size(); ++j) {
				assertEquals(positions.get(i).get(j).intValue(), match.get(i).get(j).getIndex());
			}
		}
	}

	protected void assertTerminalPositions(List<Integer> positions, List<IndexedValue> match) {
		assertEquals(positions.size(), match.size());

		for (int j = 0; j < positions.size(); ++j) {
			assertEquals(positions.get(j).intValue(), match.get(j).getIndex());
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

	protected Tree<IndexedValue> tree(String root, Object... children) {
		Tree<IndexedValue> parent = new Tree<IndexedValue>(new IndexedValue(root, 0));
		int index = 0;
		for (int i = 0; i < children.length; ++i) {
			if (children[i] instanceof String) {
				parent.newChild(new IndexedValue((String) children[i], ++index));
			} else {
				Tree<IndexedValue> child = (Tree<IndexedValue>) children[i];
				Tree<IndexedValue> newChild = parent.newChild(new IndexedValue(child.getValue().getValue(), ++index));
				index = addChildren(newChild, child, index);
			}
		}
		return parent;
	}

	private int addChildren(Tree<IndexedValue> newParent, Tree<IndexedValue> oldParent, int index) {
		for (int i = 0; i < oldParent.getChildrenCount(); ++i) {
			Tree<IndexedValue> newChild = newParent.newChild(new IndexedValue(oldParent.getChild(i).getValue()
					.getValue(), ++index));
			index = addChildren(newChild, oldParent.getChild(i), index);
		}
		return index;
	}

	private <T> void addChildren(Tree<T> newParent, Tree<T> oldParent) {
		for (int i = 0; i < oldParent.getChildrenCount(); ++i) {
			Tree<T> newChild = newParent.newChild(oldParent.getChild(i).getValue());
			addChildren(newChild, oldParent.getChild(i));
		}

	}

	protected Tree<Wildcard<IndexedValue>> wtree(String root, Object... children) {
		Tree<Wildcard<IndexedValue>> parent = new Tree<Wildcard<IndexedValue>>(new DefaultWildcard<IndexedValue>(
				new IndexedValue(root, 0)));
		for (int i = 0; i < children.length; ++i) {
			if (children[i] instanceof String) {
				String s = (String) children[i];
				if (s.equals("^")) {
					parent.newChild((DefaultWildcard<IndexedValue>) DefaultWildcard.BEGIN);
				} else if (s.equals("$")) {
					parent.newChild((DefaultWildcard<IndexedValue>) DefaultWildcard.END);
				} else {
					parent.newChild(new DefaultWildcard<IndexedValue>(new IndexedValue(s, i)));
				}
			} else {
				Tree<Wildcard<IndexedValue>> child = (Tree<Wildcard<IndexedValue>>) children[i];
				Tree<Wildcard<IndexedValue>> newChild = parent.newChild(new DefaultWildcard<IndexedValue>(
						new IndexedValue(((DefaultWildcard<IndexedValue>) child.getValue()).getValue().getValue(), i)));
				addChildren(newChild, child);
			}
		}
		return parent;
	}
}
