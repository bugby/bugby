package org.bugby.engine.model.body;

import japa.parser.ast.Node;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;

import java.util.List;

import org.bugby.annotation.BadExample;
import org.bugby.annotation.GoodExample;
import org.bugby.annotation.GoodExampleTrigger;
import org.bugby.engine.ASTModelBridge;
import org.bugby.engine.VirtualNode;
import org.bugby.engine.model.ListUtils;
import org.bugby.wildcard.SomeType;
import org.richast.node.ASTNodeData;
import org.richast.type.TypeWrapper;

import com.google.common.base.Objects;

public class ClassOrInterfaceDeclarationBridge implements ASTModelBridge {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Node> getChildren(Node parent) {
		ClassOrInterfaceDeclaration decl = (ClassOrInterfaceDeclaration) parent;
		return (List) ListUtils.asList(VirtualNode.of(parent, "annotations", decl.getAnnotations(), false),
				VirtualNode.of(parent, "typeParameters", decl.getTypeParameters(), false),
				VirtualNode.of(parent, "extends", decl.getExtends(), false),
				VirtualNode.of(parent, "implements", decl.getImplements(), false),
				VirtualNode.of(parent, "members", decl.getMembers(), false));
	}

	@Override
	public boolean isOrdered(Node node) {
		return false;
	}

	@Override
	public String getMatcherName(Node node) {
		TypeWrapper type = ASTNodeData.resolvedType(node);
		if (type.hasAnnotation(BadExample.class) || type.hasAnnotation(GoodExample.class)
				|| type.hasAnnotation(GoodExampleTrigger.class)) {
			return SomeType.class.getSimpleName();
		}
		ClassOrInterfaceDeclaration decl = (ClassOrInterfaceDeclaration) node;
		return decl.getName();
	}

	@Override
	public boolean areSimilar(Node patternNode, Node sourceNode) {
		return Objects.equal(ASTNodeData.resolvedType(patternNode), ASTNodeData.resolvedType(sourceNode));
	}
}
