package org.bugby.engine.model.type;

import japa.parser.ast.Node;
import japa.parser.ast.type.PrimitiveType;

import java.util.Collections;
import java.util.List;

import org.bugby.engine.ASTModelBridge;

import com.google.common.base.Objects;

public class PrimitiveTypeBridge implements ASTModelBridge {

	@Override
	public List<Node> getChildren(Node parent) {
		return Collections.emptyList();
	}

	@Override
	public boolean isOrdered(String node) {
		return true;
	}

	@Override
	public String getMatcherName(Node node) {
		return "";
	}

	@Override
	public boolean areSimilar(Node patternNode, Node sourceNode) {
		PrimitiveType patternType = (PrimitiveType) patternNode;
		PrimitiveType sourceType = (PrimitiveType) sourceNode;
		return Objects.equal(patternType.getType(), sourceType.getType());
	}
}