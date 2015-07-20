package org.bugby.api;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;

import org.bugby.matcher.javac.TreeUtils;

import com.sun.source.tree.IdentifierTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.VariableTree;

public class Variables {
	public static Element getVariableElement(Tree node) {
		if (node instanceof VariableTree) {
			return TreeUtils.elementFromDeclaration((VariableTree) node);
		}
		if (node instanceof IdentifierTree) {
			return TreeUtils.elementFromUse((IdentifierTree) node);
		}
		return null;
	}

	public static void removeVariables(MatchingContext context, List<MatchingPath> solution) {
		for (MatchingPath mp : solution) {
			context.removeValue(new MatchingValueKey("VAR", Variables.getVariableElement(mp.getMatcher().getPatternNode())));
		}
	}

	public static void setVariables(MatchingContext context, List<MatchingPath> solution) {
		for (MatchingPath mp : solution) {
			Element patternNodeElement = Variables.getVariableElement(mp.getMatcher().getPatternNode());
			Element sourceNodeElement = Variables.getVariableElement(mp.getSourceNode());
			System.out.println("!!ASSIGN:" + patternNodeElement + " -> " + sourceNodeElement);
			context.putValue(new MatchingValueKey("VAR", patternNodeElement), sourceNodeElement);
		}
	}

	public static SolutionFoundCallback variablesForEachSolution(final MatchingContext context, final Callable<Boolean> matchSolution,
			final AtomicBoolean foundMatch) {
		return new SolutionFoundCallback() {
			@Override
			public void solutionFound(List<MatchingPath> solution) {
				if (foundMatch.get()) {
					//keep the first solution
					//TODO shall i search more !?
					return;
				}
				Variables.setVariables(context, solution);
				boolean ok = false;
				try {
					ok = matchSolution.call();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
				//cleanup
				Variables.removeVariables(context, solution);
				foundMatch.set(ok);
			}
		};
	}

	public static List<Tree> extractAllVariables(Tree node) {
		List<IdentifierTree> vars = TreeUtils.descendantsOfType(node, IdentifierTree.class);
		Map<Element, Tree> varsByElement = new LinkedHashMap<Element, Tree>();
		for (Tree var : vars) {
			Element element = TreeUtils.elementFromUse((IdentifierTree) var);
			if (element.getKind() == ElementKind.EXCEPTION_PARAMETER || element.getKind() == ElementKind.FIELD
					|| element.getKind() == ElementKind.LOCAL_VARIABLE || element.getKind() == ElementKind.PARAMETER) {
				varsByElement.put(element, var);
			}
		}
		return new ArrayList<Tree>(varsByElement.values());
	}
}
