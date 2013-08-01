package org.bugby.matcher.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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
	private final List<Tree<T>> children;

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
		this.children = new ArrayList<Tree<T>>();
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

	public Tree<T> getChild(int index) {
		return children.get(index);
	}

	public Tree<T> newChild(T aValue) {
		Tree<T> child = new Tree<T>(aValue, this);
		children.add(child);
		return child;
	}

	@Override
	public String toString() {
		Integer pid = parent == null ? null : Integer.valueOf(parent.id);
		StringBuilder sb = new StringBuilder(getIndent());
		sb.append("Tree [id=").append(id);
		sb.append(", parent=").append(pid);
		sb.append(", value=").append(value);
		sb.append("]");
		for (Tree<T> child : children) {
			sb.append("\n");
			sb.append(child.toString());
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
