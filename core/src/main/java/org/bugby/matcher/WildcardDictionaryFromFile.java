package org.bugby.matcher;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

import org.bugby.api.Pattern;
import org.bugby.matcher.javac.ElementUtils;
import org.bugby.matcher.javac.ParsedSource;
import org.bugby.matcher.javac.TreeUtils;

import com.sun.source.tree.AnnotationTree;
import com.sun.source.tree.ClassTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.VariableTree;
import com.sun.source.util.TreeScanner;

public class WildcardDictionaryFromFile {
	public static void addWildcardsFromFile(WildcardDictionary dictionary, ClassLoader builtProjectClassLoader, ParsedSource parsedSource) {
		new WildcardVisitor(builtProjectClassLoader).scan(parsedSource.getCompilationUnitTree(), dictionary);
	}

	private static class WildcardVisitor extends TreeScanner<Boolean, WildcardDictionary> {
		private final ClassLoader builtProjectClassLoader;

		public WildcardVisitor(ClassLoader builtProjectClassLoader) {
			this.builtProjectClassLoader = builtProjectClassLoader;
		}

		@SuppressWarnings({"rawtypes", "unchecked"})
		private void addMatcher(WildcardDictionary dictionary, String name, TypeMirror wildcardAnnotation) {
			if (wildcardAnnotation != null) {
				Class clz;
				try {
					clz = builtProjectClassLoader.loadClass(wildcardAnnotation.toString());
					dictionary.addMatcherClass(name, clz);
				}
				catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		@Override
		public Boolean visitMethod(MethodTree node, WildcardDictionary d) {
			TypeMirror wildcardAnnotation =
					(TypeMirror) ElementUtils.getAnnotationValue(TreeUtils.elementFromDeclaration(node), Pattern.class, "value");
			addMatcher(d, node.getName().toString(), wildcardAnnotation);
			return super.visitMethod(node, d);
		}

		@Override
		public Boolean visitVariable(VariableTree node, WildcardDictionary d) {
			TypeMirror wildcardAnnotation =
					(TypeMirror) ElementUtils.getAnnotationValue(TreeUtils.elementFromDeclaration(node), Pattern.class, "value");
			addMatcher(d, node.getName().toString(), wildcardAnnotation);
			return super.visitVariable(node, d);
		}

		@Override
		public Boolean visitClass(ClassTree node, WildcardDictionary d) {
			TypeMirror wildcardAnnotation =
					(TypeMirror) ElementUtils.getAnnotationValue(TreeUtils.elementFromDeclaration(node), Pattern.class, "value");
			//TODO - annotation matchers should probably be full name here
			TypeElement element = TreeUtils.elementFromDeclaration(node);
			addMatcher(d, element.getQualifiedName().toString(), wildcardAnnotation);
			addMatcher(d, node.getSimpleName().toString(), wildcardAnnotation);
			return super.visitClass(node, d);
		}

		@Override
		public Boolean visitAnnotation(AnnotationTree node, WildcardDictionary d) {
			//			TypeMirror wildcardAnnotation = (TypeMirror) ElementUtils.getAnnotationValue(TreeUtils.elementFromDeclaration(node), Wildcard.class,
			//					"value");
			//			addMatcher(d, node.getName().toString(), wildcardAnnotation);
			//			d.addAnnotation(node.getAnnotationType().toString());
			return super.visitAnnotation(node, d);
		}

	}

}
