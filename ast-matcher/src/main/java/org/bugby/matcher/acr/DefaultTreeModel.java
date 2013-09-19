package org.bugby.matcher.acr;

import java.util.ArrayList;
import java.util.List;

import org.bugby.matcher.tree.Tree;

public class DefaultTreeModel<V> implements TreeModel<Tree<V>, V> {

	@Override
	public int getChildrenCount(Tree<V> parent) {
		return parent.getChildrenCount();
	}

	@Override
	public List<Tree<V>> getChildren(Tree<V> parent) {
		return parent.getChildren();
	}

	@Override
	public V getValue(Tree<V> node) {
		return node.getValue();
	}

	@Override
	public List<Tree<V>> getDescendants(Tree<V> parent) {
		List<Tree<V>> descendants = new ArrayList<Tree<V>>();
		for (int i = 0; i < parent.getChildrenCount(); ++i) {
			descendants.add(parent.getChild(i));
			descendants.addAll(getDescendants(parent.getChild(i)));
		}
		return descendants;
	}

}
