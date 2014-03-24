package org.bugby.wildcard.matcher;

import java.util.HashMap;
import java.util.Map;

import javax.lang.model.type.TypeMirror;

import org.bugby.api.javac.TreeUtils;
import org.bugby.api.wildcard.DefaultTreeMatcher;
import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;
import org.richast.scope.Scope;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.VariableTree;

/**
 * when used like this <br>
 * public void someCode(){ // ... bla bla // }
 * 
 * @author acraciun
 */
public class SomeCodeMatcher extends DefaultTreeMatcher implements TreeMatcher {
	private final MethodTree patternNode;
	private final TreeMatcher bodyMatcher;
	private final Map<String, TypeMirror> typeRestrictions;
	private final Scope patternScope;

	public SomeCodeMatcher(MethodTree patternNode, TreeMatcherFactory factory) {
		this.patternNode = patternNode;
		this.bodyMatcher = factory.build(patternNode.getBody());
		typeRestrictions = new HashMap<String, TypeMirror>();
		for (VariableTree param : patternNode.getParameters()) {
			typeRestrictions.put(param.getName().toString(), TreeUtils.elementFromDeclaration(param).asType());
			// patternScope = ASTNodeData.scope(param);
		}
		patternScope = null;
	}

	@Override
	public Multimap<TreeMatcher, Tree> matches(Tree node, MatchingContext context) {
		// TODO should match intantiation blocks
		if (!(node instanceof MethodTree)) {
			return HashMultimap.create();
		}
		MethodTree mt = (MethodTree) node;

		for (Map.Entry<String, TypeMirror> entry : typeRestrictions.entrySet()) {
			context.addTypeRestriction(entry.getKey(), entry.getValue());
		}
		Multimap<TreeMatcher, Tree> result = null;
		result = matchChild(result, node, mt.getBody(), bodyMatcher, context);

		// for (Map.Entry<String, TypeMirror> entry : typeRestrictions.entrySet()) {
		// addTypeRestriction(entry.getKey(), patternScope, entry.getValue());
		// }
		context.clearDataForNode(patternNode);

		return result;
	}

}
