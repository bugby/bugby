package org.bugby.api.wildcard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.sun.source.tree.Tree;

/**
 * this represents the path to a matching pair (matcher, node)
 * @author acraciun
 */
public class MatchingPath {
	private final MatchingContext context;
	private final TreeMatcher matcher;
	private final Tree sourceNode;
	private MatchingPath parent;
	private final List<MatchingPath> children;
	private final List<MatchingValueKey> valueKeys;

	public MatchingPath(MatchingContext context, TreeMatcher matcher, Tree sourceNode, MatchingPath parent) {
		this.context = context;
		this.matcher = matcher;
		this.sourceNode = sourceNode;
		this.parent = parent;
		this.children = new ArrayList<MatchingPath>();
		this.valueKeys = new ArrayList<MatchingValueKey>();
		if (parent != null) {
			parent.addChild(this);
		}
	}

	public TreeMatcher getMatcher() {
		return matcher;
	}

	public Tree getSourceNode() {
		return sourceNode;
	}

	public MatchingPath getParent() {
		return parent;
	}

	public List<MatchingPath> getChildren() {
		return Collections.unmodifiableList(children);
	}

	public MatchingPath getChild(TreeMatcher childMatcher, Tree childNode) {
		for (MatchingPath child : children) {
			if (child.matcher == childMatcher && child.sourceNode == childNode) {
				return child;
			}
		}
		return null;
	}

	protected void addChild(MatchingPath child) {
		children.add(child);
	}

	protected void removeChild(MatchingPath child) {
		children.remove(child);
	}

	public void addValueKey(MatchingValueKey key) {
		valueKeys.add(key);
	}

	public List<MatchingValueKey> getValueKeys() {
		return Collections.unmodifiableList(valueKeys);
	}

	public void remove() {
		for (MatchingPath child : children) {
			child.remove();
		}
		if (parent != null) {
			parent.removeChild(this);
		}
		for (MatchingValueKey key : valueKeys) {
			context.removeValue(key);
		}
		parent = null;
	}

}
