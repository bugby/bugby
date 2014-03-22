package org.bugby.matcher.tree;

import com.google.common.collect.ListMultimap;

public interface TreeModel<T, V> {
	public int getChildrenCount(T parent);

	public ListMultimap<String, T> getChildren(T parent, boolean ordered);

	public ListMultimap<String, T> getDescendants(T parent, boolean ordered);

	public V getValue(T node);

	public boolean isOrdered(T node, String childType);

	/**
	 * for ordered node only
	 * 
	 * @param node
	 * @return
	 */
	public boolean isFirstChild(T node);

	/**
	 * for ordered node only
	 * 
	 * @param node
	 * @return
	 */
	public boolean isLastChild(T node);
}
