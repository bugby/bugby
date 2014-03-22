package org.bugby.engine.model;

import japa.parser.ast.Node;
import japa.parser.ast.body.BodyDeclaration;
import japa.parser.ast.expr.AnnotationExpr;
import japa.parser.ast.expr.VariableDeclarationExpr;

import java.lang.annotation.Annotation;
import java.util.List;

public class NodeUtils {
	public static boolean hasAnnotation(Node node, Class<? extends Annotation> annotationClass) {
		List<AnnotationExpr> annotations = null;
		if (node instanceof BodyDeclaration) {
			annotations = ((BodyDeclaration) node).getAnnotations();
		} else if (node instanceof VariableDeclarationExpr) {
			annotations = ((VariableDeclarationExpr) node).getAnnotations();
		}
		if (annotations != null) {
			for (AnnotationExpr ann : annotations) {
				// TODO do this using the actual full type names
				if (ann.getName().toString().equals(annotationClass.getSimpleName())) {
					return true;
				}
			}

		}
		return false;
	}
}
