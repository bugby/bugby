package org.bugby.pattern.example;

import japa.parser.ast.CompilationUnit;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.VariableDeclarator;
import japa.parser.ast.visitor.VoidVisitorAdapter;

import java.io.File;
import java.util.Collections;

import org.bugby.wildcard.api.Wildcard;
import org.richast.GenerationContext;
import org.richast.RichASTParser;
import org.richast.node.ASTNodeData;
import org.richast.type.ClassLoaderWrapper;
import org.richast.type.FieldWrapper;
import org.richast.type.MethodWrapper;
import org.richast.type.TypeWrapper;
import org.richast.utils.ClassUtils;
import org.richast.variable.Variable;

public class WildcardDictionaryFromFile {
	public static void addWildcardsFromFile(WildcardDictionary dictionary, ClassLoader builtProjectClassLoader,
			File file) {
		ClassLoaderWrapper classLoaderWrapper = new ClassLoaderWrapper(builtProjectClassLoader,
				Collections.<String>emptyList(), Collections.<String>emptyList());
		GenerationContext context = new GenerationContext(file);
		CompilationUnit cu = RichASTParser.parseAndResolve(classLoaderWrapper, file, context, "UTF-8");
		cu.accept(new WildcardVisitor(), dictionary);
		// System.out.println(matchers);
	}

	private static class WildcardVisitor extends VoidVisitorAdapter<WildcardDictionary> {

		@Override
		public void visit(MethodDeclaration n, WildcardDictionary dictionary) {
			MethodWrapper method = ASTNodeData.resolvedMethod(n);
			if (method != null) {
				Wildcard wildcardAnnotation = method.getAnnotation(Wildcard.class);
				if (wildcardAnnotation != null) {
					dictionary.addMatcherClass(n.getName(), wildcardAnnotation.matcher());
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
					dictionary.addMatcherClass(n.getId().getName(), wildcardAnnotation.matcher());
				}
			}
			super.visit(n, dictionary);
		}

		@Override
		public void visit(ClassOrInterfaceDeclaration n, WildcardDictionary dictionary) {
			TypeWrapper type = ASTNodeData.resolvedType(n);
			if (type != null) {
				Wildcard wildcardAnnotation = ClassUtils.getAnnotation(type, Wildcard.class);
				if (wildcardAnnotation != null) {
					dictionary.addMatcherClass(n.getName(), wildcardAnnotation.matcher());
				}
			}
			super.visit(n, dictionary);
		}
	}

}
