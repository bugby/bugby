package org.bugby.engine;

import java.io.File;

import javax.lang.model.type.TypeMirror;

import org.bugby.api.javac.ElementUtils;
import org.bugby.api.javac.ParsedSource;
import org.bugby.api.javac.SourceParser;
import org.bugby.api.javac.TreeUtils;
import org.bugby.api.wildcard.Wildcard;

import com.sun.source.tree.AnnotationTree;
import com.sun.source.tree.ClassTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.VariableTree;
import com.sun.source.util.TreeScanner;

public class WildcardDictionaryFromFile {
	public static void addWildcardsFromFile(WildcardDictionary dictionary, ClassLoader builtProjectClassLoader, File file) {
		ParsedSource parsedSource = SourceParser.parse(file, builtProjectClassLoader, "UTF-8");

		new WildcardVisitor(builtProjectClassLoader).scan(parsedSource.getCompilationUnitTree(), dictionary);
	}

	private static class WildcardVisitor extends TreeScanner<Boolean, WildcardDictionary> {
		private final ClassLoader builtProjectClassLoader;

		public WildcardVisitor(ClassLoader builtProjectClassLoader) {
			this.builtProjectClassLoader = builtProjectClassLoader;
		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
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
			TypeMirror wildcardAnnotation = (TypeMirror) ElementUtils.getAnnotationValue(TreeUtils.elementFromDeclaration(node), Wildcard.class,
					"value");
			addMatcher(d, node.getName().toString(), wildcardAnnotation);
			return super.visitMethod(node, d);
		}

		@Override
		public Boolean visitVariable(VariableTree node, WildcardDictionary d) {
			TypeMirror wildcardAnnotation = (TypeMirror) ElementUtils.getAnnotationValue(TreeUtils.elementFromDeclaration(node), Wildcard.class,
					"value");
			addMatcher(d, node.getName().toString(), wildcardAnnotation);
			return super.visitVariable(node, d);
		}

		@Override
		public Boolean visitClass(ClassTree node, WildcardDictionary d) {
			TypeMirror wildcardAnnotation = (TypeMirror) ElementUtils.getAnnotationValue(TreeUtils.elementFromDeclaration(node), Wildcard.class,
					"value");
			addMatcher(d, node.getSimpleName().toString(), wildcardAnnotation);
			return super.visitClass(node, d);
		}

		@Override
		public Boolean visitAnnotation(AnnotationTree node, WildcardDictionary d) {
			d.addAnnotation(node.getAnnotationType().toString());
			return super.visitAnnotation(node, d);
		}

	}

}
