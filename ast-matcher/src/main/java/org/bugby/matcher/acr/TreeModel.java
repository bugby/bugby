package org.bugby.matcher.acr;

import java.util.List;

public interface TreeModel<T, V> {
	public int getChildrenCount(T parent);

	public List<T> getChildren(T parent, boolean ordered);

	public List<T> getDescendants(T parent, boolean ordered);

	public V getValue(T node);

	public boolean isOrdered(T node);
}
