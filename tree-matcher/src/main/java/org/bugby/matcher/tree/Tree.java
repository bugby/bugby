package org.bugby.matcher.tree;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

/**
 * Generic tree - we'll use this for matching against the Pattern.
 * 
 * @author catac
 */
public class Tree<T> {
	private final AtomicInteger idGenerator; // internal node-id generator (one per tree)
	private final int id; // uniquely identify a node in the tree

	private final T value;
	private final Tree<T> parent; // null if root
	private final ListMultimap<String, Tree<T>> children;

	/** Create the root for a new Tree */
	public Tree(T value) {
		this(value, null);
	}

	/** Children can be created only by the parent */
	private Tree(T value, Tree<T> parent) {
		this.idGenerator = parent == null ? new AtomicInteger() : parent.idGenerator;
		this.id = idGenerator.incrementAndGet();
		this.value = value;
		this.parent = parent;
		this.children = ArrayListMultimap.create();
	}

	public int getId() {
		return id;
	}

	public T getValue() {
		return value;
	}

	public Tree<T> getParent() {
		return parent;
	}

	public int getChildrenCount() {
		return children.size();
	}

	public Tree<T> getChild(String type, int index) {
		List<Tree<T>> part = children.get(type);
		return part != null ? part.get(index) : null;
	}

	public ListMultimap<String, Tree<T>> getChildren() {
		return ArrayListMultimap.create(children);
	}

	public Tree<T> newChild(String type, T aValue) {
		Tree<T> child = new Tree<T>(aValue, this);
		children.put(type, child);
		return child;
	}

	public void removeChild(String type, Tree<T> node) {
		children.remove(type, node);
	}

	@Override
	public String toString() {
		Integer pid = parent == null ? null : Integer.valueOf(parent.id);
		StringBuilder sb = new StringBuilder(getIndent());
		sb.append("Tree [id=").append(id);
		sb.append(", parent=").append(pid);
		sb.append(", value=").append(value);
		sb.append("]");
		for (Map.Entry<String, Collection<Tree<T>>> entry : children.asMap().entrySet()) {
			sb.append("\n");
			sb.append(getIndent());
			sb.append("  " + entry.getKey() + " {");
			for (Tree<T> child : entry.getValue()) {
				sb.append("\n");
				sb.append(child.toString());
			}
			sb.append("\n");
			sb.append(getIndent());
			sb.append("  }");
		}
		return sb.toString();
	}

	private String getIndent() {
		StringBuilder sb = new StringBuilder();
		Tree<T> pointer = this;
		while (pointer.parent != null) {
			sb.append("  ");
			pointer = pointer.parent;
		}
		return sb.toString();
	}

}
