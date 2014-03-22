package org.bugby.engine.model.type;

import japa.parser.ast.Node;
import japa.parser.ast.type.ClassOrInterfaceType;

import java.util.List;

import org.bugby.engine.ASTModelBridge;
import org.bugby.engine.VirtualNode;
import org.bugby.engine.model.ListUtils;

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
	public boolean isOrdered(String childType) {
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
