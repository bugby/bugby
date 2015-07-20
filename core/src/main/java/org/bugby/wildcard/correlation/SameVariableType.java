package org.bugby.wildcard.correlation;

import java.util.Comparator;

import javax.lang.model.element.Element;

import org.bugby.matcher.javac.TreeUtils;

import com.sun.source.tree.Tree;
import com.sun.source.tree.VariableTree;

public class SameVariableType implements Comparator<Tree> {

	@Override
	public int compare(Tree o1, Tree o2) {
		if (!(o1 instanceof VariableTree)) {
			return -1;
		}
		if (!(o2 instanceof VariableTree)) {
			return -1;
		}
		VariableTree v1 = (VariableTree) o1;
		VariableTree v2 = (VariableTree) o2;

		Element e1 = TreeUtils.elementFromDeclaration(v1);
		Element e2 = TreeUtils.elementFromDeclaration(v2);

		if (e1.asType().toString().equals(e2.asType().toString())) {
			return 0;
		}
		return -1;
	}

}
