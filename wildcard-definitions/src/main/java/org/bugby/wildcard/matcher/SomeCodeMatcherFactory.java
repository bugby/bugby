package org.bugby.wildcard.matcher;

import japa.parser.ast.Node;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.Parameter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.bugby.api.wildcard.WildcardNodeMatcher;
import org.bugby.api.wildcard.WildcardNodeMatcherFactory;
import org.bugby.api.wildcard.WildcardPatternBuildContext;
import org.bugby.matcher.tree.Tree;
import org.bugby.matcher.tree.TreeModel;
import org.richast.node.ASTNodeData;
import org.richast.scope.Scope;
import org.richast.type.TypeWrapper;

public class SomeCodeMatcherFactory extends DefaultMatcherFactory {

	@Override
	public Tree<WildcardNodeMatcher> buildPatternNode(TreeModel<Node, Node> patternSourceTreeNodeModel, String currentPatternSourceNodeType,
			Node currentPatternSourceNode, Tree<WildcardNodeMatcher> parentPatternNode, WildcardNodeMatcherFactory defaultFactory,
			WildcardPatternBuildContext buildContext) {
		if (currentPatternSourceNode instanceof MethodDeclaration) {
			// when used like this
			// public void someCode(){
			// ... bla bla
			// }
			MethodDeclaration decl = (MethodDeclaration) currentPatternSourceNode;
			Map<String, TypeWrapper> typeRestrictions = new HashMap<String, TypeWrapper>();
			Scope patternScope = null;
			if (decl.getParameters() != null) {
				for (Parameter param : decl.getParameters()) {
					typeRestrictions.put(param.getId().getName(), ASTNodeData.resolvedType(param));
					patternScope = ASTNodeData.scope(param);
				}
			}
			WildcardNodeMatcher matcher = new SomeCodeMatcher(patternScope, typeRestrictions);
			Tree<WildcardNodeMatcher> newPatternNode = parentPatternNode.newChild(currentPatternSourceNodeType, matcher);
			// delegate directly to the body
			defaultFactory.buildPatternNode(patternSourceTreeNodeModel, currentPatternSourceNodeType, decl.getBody(), newPatternNode,
					defaultFactory, buildContext);
			return newPatternNode;
		}
		return super.buildPatternNode(patternSourceTreeNodeModel, currentPatternSourceNodeType, currentPatternSourceNode, parentPatternNode,
				defaultFactory, buildContext);
	}

	@Override
	protected WildcardNodeMatcher buildPatternNodeOnly(TreeModel<Node, Node> patternSourceTreeNodeModel, Node currentPatternSourceNode,
			Tree<WildcardNodeMatcher> parentPatternNode, WildcardNodeMatcherFactory defaultFactory) {
		return new SomeCodeMatcher(null, Collections.<String, TypeWrapper> emptyMap());
	}
}
