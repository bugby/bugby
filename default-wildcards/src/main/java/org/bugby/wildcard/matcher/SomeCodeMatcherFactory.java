package org.bugby.wildcard.matcher;

import japa.parser.ast.Node;
import japa.parser.ast.body.MethodDeclaration;

import org.bugby.matcher.acr.TreeModel;
import org.bugby.matcher.tree.Tree;
import org.bugby.wildcard.api.WildcardNodeMatcher;
import org.bugby.wildcard.api.WildcardNodeMatcherFactory;

public class SomeCodeMatcherFactory extends DefaultMatcherFactory {

	@Override
	public Tree<WildcardNodeMatcher> buildPatternNode(TreeModel<Node, Node> patternSourceTreeNodeModel,
			Node currentPatternSourceNode, Tree<WildcardNodeMatcher> parentPatternNode,
			WildcardNodeMatcherFactory defaultFactory) {
		if (currentPatternSourceNode instanceof MethodDeclaration) {
			// when used like this
			// public void someCode(){
			// ... bla bla
			// }
			MethodDeclaration decl = (MethodDeclaration) currentPatternSourceNode;
			WildcardNodeMatcher matcher = new SomeCodeMatcher();
			Tree<WildcardNodeMatcher> newPatternNode = parentPatternNode.newChild(matcher);
			// delegate directly to the body
			defaultFactory.buildPatternNode(patternSourceTreeNodeModel, decl.getBody(), newPatternNode, defaultFactory);
			return newPatternNode;
		}
		return super.buildPatternNode(patternSourceTreeNodeModel, currentPatternSourceNode, parentPatternNode,
				defaultFactory);
	}

	@Override
	protected WildcardNodeMatcher buildPatternNodeOnly(TreeModel<Node, Node> patternSourceTreeNodeModel,
			Node currentPatternSourceNode, Tree<WildcardNodeMatcher> parentPatternNode,
			WildcardNodeMatcherFactory defaultFactory) {
		return new SomeCodeMatcher();
	}
}
