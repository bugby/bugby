package org.bugby.pattern.example.model.type;

import japa.parser.ast.Node;
import japa.parser.ast.type.ClassOrInterfaceType;

import java.util.List;

import org.bugby.pattern.example.ASTModelBridge;
import org.bugby.pattern.example.VirtualNode;
import org.bugby.pattern.example.model.ListUtils;

import com.google.common.base.Objects;

public class ClassOrInterfaceTypeBridge implements ASTModelBridge {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Node> getChildren(Node parent) {
		ClassOrInterfaceType decl = (ClassOrInterfaceType) parent;
		return (List) ListUtils.asList(VirtualNode.of(parent, "scope", decl.getScope(), true),
				VirtualNode.of(parent, "typeArgs", decl.getTypeArgs(), true));
	}

	@Override
	public boolean isOrdered(Node node) {
		return true;
	}

	@Override
	public String getMatcherName(Node node) {
		ClassOrInterfaceType decl = (ClassOrInterfaceType) node;
		return decl.getName();
	}

	@Override
	public boolean areSimilar(Node patternNode, Node sourceNode) {
		ClassOrInterfaceType patternType = (ClassOrInterfaceType) patternNode;
		ClassOrInterfaceType sourceType = (ClassOrInterfaceType) sourceNode;
		return Objects.equal(patternType.getName(), sourceType.getName());
	}
}
