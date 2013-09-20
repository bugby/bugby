package org.bugby.pattern.example.model;

import japa.parser.ast.CompilationUnit;
import japa.parser.ast.Node;

import java.util.List;

import org.bugby.pattern.example.ASTModelBridge;

public class CompilationUnitBridge implements ASTModelBridge {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Node> getChildren(Node parent) {
		CompilationUnit cu = (CompilationUnit) parent;
		// skip the other children
		return ListUtils.notNull((List) cu.getTypes());
	}

	@Override
	public boolean isOrdered(Node node) {
		return true;
	}

	@Override
	public String getMatcherName(Node node) {
		return "";
	}
}
