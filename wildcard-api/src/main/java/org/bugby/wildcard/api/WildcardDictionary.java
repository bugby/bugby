package org.bugby.wildcard.api;

import japa.parser.ast.CompilationUnit;
import japa.parser.ast.Node;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.VariableDeclarator;
import japa.parser.ast.visitor.VoidVisitorAdapter;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.richast.GenerationContext;
import org.richast.RichASTParser;
import org.richast.node.ASTNodeData;
import org.richast.type.ClassLoaderWrapper;
import org.richast.type.FieldWrapper;
import org.richast.type.MethodWrapper;
import org.richast.variable.Variable;

public class WildcardDictionary {
	private Map<String, Class<? extends WildcardNodeMatcher<? extends Node>>> matchers =
			new HashMap<String, Class<? extends WildcardNodeMatcher<? extends Node>>>();

	public void addWildcardsFromFile(ClassLoader builtProjectClassLoader, File file) {
		ClassLoaderWrapper classLoaderWrapper =
				new ClassLoaderWrapper(builtProjectClassLoader, Collections.<String> emptyList(), Collections.<String> emptyList());
		GenerationContext context = new GenerationContext(file);
		CompilationUnit cu = RichASTParser.parseAndResolve(classLoaderWrapper, file, context, "UTF-8");
		cu.accept(new WildcardVisitor(), this);
		System.out.println(matchers);
	}

	private static class WildcardVisitor extends VoidVisitorAdapter<WildcardDictionary> {

		@Override
		public void visit(MethodDeclaration n, WildcardDictionary dictionary) {
			MethodWrapper method = ASTNodeData.resolvedMethod(n);
			if (method != null) {
				Wildcard wildcardAnnotation = method.getAnnotation(Wildcard.class);
				if (wildcardAnnotation != null) {
					dictionary.addMatcher(n, wildcardAnnotation.matcher());
				}
			}
			super.visit(n, dictionary);
		}

		@Override
		public void visit(VariableDeclarator n, WildcardDictionary dictionary) {
			Variable variable = ASTNodeData.resolvedVariable(n);

			if (variable instanceof FieldWrapper) {
				FieldWrapper field = (FieldWrapper) variable;
				Wildcard wildcardAnnotation = field.getAnnotation(Wildcard.class);
				if (wildcardAnnotation != null) {
					dictionary.addMatcher(n, wildcardAnnotation.matcher());
				}
			}
			super.visit(n, dictionary);
		}
	}

	public void addMatcher(MethodDeclaration n, Class<? extends WildcardNodeMatcher<?>> matcherClass) {
		matchers.put(n.getName(), matcherClass);
	}

	public void addMatcher(VariableDeclarator n, Class<? extends WildcardNodeMatcher<?>> matcherClass) {
		matchers.put(n.getId().getName(), matcherClass);
	}

	public Class<? extends WildcardNodeMatcher<?>> findMatcherClass(String name) {
		return matchers.get(name);
	}

}
