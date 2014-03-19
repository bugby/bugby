package org.bugby.api.wildcard;

import japa.parser.ast.expr.MethodCallExpr;

import java.util.HashMap;
import java.util.Map;

public class WildcardPatternBuildContext {
	private Map<String, MethodCallExpr> patternAnnotations = new HashMap<String, MethodCallExpr>();

	public void pushAnnotationNode(MethodCallExpr patternAnnotationNode) {
		patternAnnotations.put(patternAnnotationNode.getName(), patternAnnotationNode);
	}

	public Map<String, MethodCallExpr> retrieveAnnotations() {
		Map<String, MethodCallExpr> ret = patternAnnotations;
		patternAnnotations = new HashMap<String, MethodCallExpr>();
		return ret;
	}
}
