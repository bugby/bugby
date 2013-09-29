package org.bugby.wildcard.correlation;

import japa.parser.ast.Node;

import java.util.Arrays;
import java.util.Comparator;

import org.richast.node.ASTNodeData;
import org.richast.type.MethodWrapper;

public class SameMethodSignature implements Comparator<Node> {

	@Override
	public int compare(Node o1, Node o2) {
		MethodWrapper method1 = ASTNodeData.resolvedMethod(o1);
		MethodWrapper method2 = ASTNodeData.resolvedMethod(o2);
		if (method1 == null || method2 == null) {
			return -1;
		}

		if (!method1.getName().equals(method2.getName())) {
			return -1;
		}
		if (!method1.getReturnType().equals(method2.getReturnType())) {
			return -1;
		}

		// TODO should I check the generic type args too !?
		return Arrays.equals(method1.getParameterTypes(), method2.getParameterTypes()) ? 0 : -1;
	}

}
