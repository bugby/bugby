package org.bugby.matcher.tree;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.google.common.base.Predicate;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimaps;

abstract public class DefaultTreeModel<V> implements TreeModel<Tree<V>, V> {

	@Override
	public int getChildrenCount(Tree<V> parent) {
		return parent.getChildrenCount();
	}

	@Override
	public ListMultimap<String, Tree<V>> getChildren(final Tree<V> parent, final boolean ordered) {
		ListMultimap<String, Tree<V>> children = parent.getChildren();

		return ArrayListMultimap.create(Multimaps.filterKeys(children, new Predicate<String>() {
			@Override
			public boolean apply(String childType) {
				return isOrdered(parent, childType) == ordered;
			}
		}));
	}

	@Override
	public V getValue(Tree<V> node) {
		return node.getValue();
	}

	@Override
	public ListMultimap<String, Tree<V>> getDescendants(Tree<V> parent, boolean ordered) {
		ListMultimap<String, Tree<V>> children = parent.getChildren();
		ListMultimap<String, Tree<V>> descendants = ArrayListMultimap.create();

		for (Map.Entry<String, Collection<Tree<V>>> entry : children.asMap().entrySet()) {
			for (Tree<V> child : entry.getValue()) {
				if (isOrdered(parent, entry.getKey()) == ordered) {
					descendants.put(entry.getKey(), child);
				}
				// descendants.putAll(getDescendants(child, ordered));
				descendants.putAll(entry.getKey(), getDescendants(child, ordered).values());
			}
		}
		return descendants;
	}

	/**
	 * for ordered node only
	 * 
	 * @param node
	 * @return
	 */
	@Override
	public boolean isFirstChild(Tree<V> node) {
		// TODO optimize here

		ListMultimap<String, Tree<V>> childrenByType = getChildren(node.getParent(), true);
		for (Collection<Tree<V>> children : childrenByType.asMap().values()) {
			if (((List<Tree<V>>) children).get(0) == node) {
				return true;
			}
		}
		return false;
	}

	/**
	 * for ordered node only
	 * 
	 * @param node
	 * @return
	 */
	@Override
	public boolean isLastChild(Tree<V> node) {
		// TODO optimize here

		ListMultimap<String, Tree<V>> childrenByType = getChildren(node.getParent(), true);
		for (Collection<Tree<V>> children : childrenByType.asMap().values()) {
			if (((List<Tree<V>>) children).get(children.size() - 1) == node) {
				return true;
			}
		}
		return false;
	}

}
