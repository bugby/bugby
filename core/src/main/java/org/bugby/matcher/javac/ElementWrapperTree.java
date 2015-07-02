package org.bugby.matcher.javac;

import javax.lang.model.element.Element;

import com.sun.source.tree.Tree;
import com.sun.source.tree.TreeVisitor;

/**
 * this is a special tree node that embeds the reference to another type (or element)
 * @author acraciun
 */
public class ElementWrapperTree implements Tree {
	private final Element element;

	public ElementWrapperTree(Element element) {
		this.element = element;
	}

	@Override
	public <R, D> R accept(TreeVisitor<R, D> visitor, D ctx) {
		return null;
	}

	@Override
	public Kind getKind() {
		//not really but close
		return Tree.Kind.IDENTIFIER;
	}

	public Element getElement() {
		return element;
	}

	@Override
	public String toString() {
		return "Wrapper of " + element;
	}

}
