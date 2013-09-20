package org.bugby.pattern.example;

import japa.parser.ast.Node;
import japa.parser.ast.visitor.GenericVisitor;
import japa.parser.ast.visitor.VoidVisitor;

import java.util.List;

import org.bugby.pattern.example.model.ListUtils;

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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private VirtualNode(String name, List<? extends Node> children) {
		this.name = name;
		this.children = ListUtils.notNull((List) children);
	}

	private VirtualNode(String name, Node child) {
		this(name, ListUtils.singletonListOrEmpty(child));
	}

	public static VirtualNode of(String name, List<? extends Node> children) {
		if (children == null) {
			return null;
		}
		return new VirtualNode(name, children);
	}

	public static VirtualNode of(String name, Node child) {
		if (child == null) {
			return null;
		}
		return new VirtualNode(name, child);
	}

	public String getName() {
		return name;
	}

	public List<Node> getChildren() {
		return children;
	}

	@Override
	public <R, A> R accept(GenericVisitor<R, A> v, A arg) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <A> void accept(VoidVisitor<A> v, A arg) {
		// TODO Auto-generated method stub

	}

}
