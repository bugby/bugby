package org.bugby.engine;

import japa.parser.ast.Node;
import japa.parser.ast.visitor.GenericVisitor;
import japa.parser.ast.visitor.VoidVisitor;

import java.util.List;

import org.bugby.engine.model.ListUtils;
import org.richast.node.ASTNodeData;

/**
 * these are nodes that do not exist in the basic model, but can be used for matching (it's used to segregate the
 * different type of children for a given node) For example a if node has 3 virtual child nodes: condition, then, else.
 * 
 * @author acraciun
 * 
 */
public class VirtualNode extends Node {
	private final String name;
	private final List<Node> children;
	private final boolean ordered;
	private final Node parent;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private VirtualNode(Node parent, String name, List<? extends Node> children, boolean ordered) {
		super(parent.getBeginLine(), parent.getBeginColumn(), parent.getEndLine(), parent.getEndColumn());
		this.name = name;
		this.children = ListUtils.notNull((List) children);
		this.ordered = ordered;
		this.parent = parent;
		// change parents
		setData(new ASTNodeData());
		ASTNodeData.parent(this, parent);
		for (Node child : children) {
			ASTNodeData.parent(child, this);
		}
	}

	private VirtualNode(Node parent, String name, Node child, boolean ordered) {
		this(parent, name, ListUtils.singletonListOrEmpty(child), ordered);
	}

	public static VirtualNode of(Node parent, String name, List<? extends Node> children, boolean ordered) {
		if (children == null) {
			return null;
		}
		return new VirtualNode(parent, name, children, ordered);
	}

	public static VirtualNode of(Node parent, String name, Node child, boolean ordered) {
		if (child == null) {
			return null;
		}
		return new VirtualNode(parent, name, child, ordered);
	}

	public Node getParent() {
		return parent;
	}

	public String getName() {
		return name;
	}

	public List<Node> getChildren() {
		return children;
	}

	public boolean isOrdered() {
		return ordered;
	}

	@Override
	public <R, A> R accept(GenericVisitor<R, A> v, A arg) {
		R ret = null;
		for (Node child : children) {
			ret = child.accept(v, arg);
		}
		return ret;
	}

	@Override
	public <A> void accept(VoidVisitor<A> v, A arg) {
		for (Node child : children) {
			child.accept(v, arg);
		}
	}

}
