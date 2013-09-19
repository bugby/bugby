package org.bugby.matcher.acr;

import java.util.List;

public interface TreeModel<T, V> {
	public int getChildrenCount(T parent);

	public List<T> getChildren(T parent);

	public V getValue(T node);

	public List<T> getDescendants(T parent);
}
