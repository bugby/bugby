package org.bugby.engine.model.type;

import japa.parser.ast.Node;
import japa.parser.ast.type.ClassOrInterfaceType;
import japa.parser.ast.type.ReferenceType;

import java.util.List;

import org.bugby.engine.ASTModelBridge;
import org.bugby.engine.model.ListUtils;

import com.google.common.base.Objects;

public class ReferenceTypeBridge implements ASTModelBridge {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Node> getChildren(Node parent) {
		ReferenceType decl = (ReferenceType) parent;
		return (List) ListUtils.singletonListOrEmpty(decl.getType());
	}

	@Override
	public boolean isOrdered(String node) {
		return true;
	}

	@Override
	public String getMatcherName(Node node) {
		ReferenceType decl = (ReferenceType) node;
		if (decl.getType() instanceof ClassOrInterfaceType) {
			return ((ClassOrInterfaceType) decl.getType()).getName();
		}
		return "";
	}

	@Override
	public boolean areSimilar(Node patternNode, Node sourceNode) {
		ReferenceType patternType = (ReferenceType) patternNode;
		ReferenceType sourceType = (ReferenceType) sourceNode;
		return Objects.equal(patternType.getArrayCount(), sourceType.getArrayCount());
	}
}
