package org.bugby.wildcard.correlation;

import java.util.Comparator;

import com.sun.source.tree.MethodTree;
import com.sun.source.tree.Tree;

public class SameMethodSignature implements Comparator<Tree> {

	@Override
	public int compare(Tree o1, Tree o2) {
		MethodTree method1 = (MethodTree) o1;
		MethodTree method2 = (MethodTree) o2;
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
		// return Arrays.equals(method1.getParameters(), method2.getParameters()) ? 0 : -1;
		return 0;
	}

}
