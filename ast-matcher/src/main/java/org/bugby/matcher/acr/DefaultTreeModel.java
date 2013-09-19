package org.bugby.matcher.acr;

import java.util.ArrayList;
import java.util.List;

import org.bugby.matcher.tree.Tree;

abstract class DefaultTreeModel<V> implements TreeModel<Tree<V>, V> {

	@Override
	public int getChildrenCount(Tree<V> parent) {
		return parent.getChildrenCount();
	}

	@Override
	public List<Tree<V>> getChildren(Tree<V> parent, boolean ordered) {
		List<Tree<V>> children = new ArrayList<Tree<V>>();
		for (int i = 0; i < parent.getChildrenCount(); ++i) {
			Tree<V> child = parent.getChild(i);
			if (isOrdered(child) == ordered) {
				children.add(child);
			}
		}
		return children;
	}

	@Override
	public V getValue(Tree<V> node) {
		return node.getValue();
	}

	@Override
	public List<Tree<V>> getDescendants(Tree<V> parent, boolean ordered) {
		List<Tree<V>> descendants = new ArrayList<Tree<V>>();
		for (int i = 0; i < parent.getChildrenCount(); ++i) {
			Tree<V> child = parent.getChild(i);
			if (isOrdered(child) == ordered) {
				descendants.add(child);
				descendants.addAll(getDescendants(child, ordered));
			}
		}
		return descendants;
	}

}
