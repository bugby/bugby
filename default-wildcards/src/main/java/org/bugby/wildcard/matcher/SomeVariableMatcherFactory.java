package org.bugby.wildcard.matcher;

import japa.parser.ast.Node;
import japa.parser.ast.body.VariableDeclarator;

import org.bugby.matcher.acr.TreeModel;
import org.bugby.matcher.tree.Tree;
import org.bugby.wildcard.api.WildcardNodeMatcher;
import org.bugby.wildcard.api.WildcardNodeMatcherFactory;
import org.richast.node.ASTNodeData;
import org.richast.scope.Scope;
import org.richast.variable.Variable;

public class SomeVariableMatcherFactory extends DefaultMatcherFactory {

	@Override
	protected WildcardNodeMatcher buildPatternNodeOnly(TreeModel<Node, Node> patternSourceTreeNodeModel,
			Node currentPatternSourceNode, Tree<WildcardNodeMatcher> parentPatternNode,
			WildcardNodeMatcherFactory defaultFactory) {
		Variable var = ASTNodeData.resolvedVariable(currentPatternSourceNode);
		Scope scope = ASTNodeData.resolvedVariableScope(currentPatternSourceNode);
		if (var != null) {
			// variable declarations are unordered, but variable usage is ordered
			boolean ordered = !(currentPatternSourceNode instanceof VariableDeclarator);
			return new SomeVariableMatcher(var.getName(), scope, ordered);
		}
		throw new RuntimeException("SomeVariableMatcher used, but no variable is defined on node:"
				+ currentPatternSourceNode);
	}

}
