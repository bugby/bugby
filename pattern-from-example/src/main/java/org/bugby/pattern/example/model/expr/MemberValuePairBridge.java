package org.bugby.pattern.example.model.expr;

import japa.parser.ast.Node;
import japa.parser.ast.expr.MemberValuePair;

import java.util.List;

import org.bugby.pattern.example.model.ListUtils;

import com.google.common.base.Objects;

public class MemberValuePairBridge extends ExpressionBridge {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Node> getChildren(Node parent) {
		MemberValuePair expr = (MemberValuePair) parent;

		return (List) ListUtils.singletonListOrEmpty(expr.getValue());
	}

	@Override
	public boolean areSimilar(Node patternNode, Node sourceNode) {
		MemberValuePair patternExpr = (MemberValuePair) patternNode;
		MemberValuePair sourceExpr = (MemberValuePair) sourceNode;
		return Objects.equal(patternExpr.getName(), sourceExpr.getName());
	}
}
