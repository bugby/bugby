package org.bugby.api.wildcard;

import java.util.HashMap;
import java.util.Map;

import com.sun.source.tree.MethodInvocationTree;

public class WildcardPatternBuildContext {
	private Map<String, MethodInvocationTree> patternAnnotations = new HashMap<String, MethodInvocationTree>();

	public void pushAnnotationNode(MethodInvocationTree patternAnnotationNode) {
		patternAnnotations.put(patternAnnotationNode.getMethodSelect().toString(), patternAnnotationNode);
	}

	public Map<String, MethodInvocationTree> retrieveAnnotations() {
		Map<String, MethodInvocationTree> ret = patternAnnotations;
		patternAnnotations = new HashMap<String, MethodInvocationTree>();
		return ret;
	}
}
