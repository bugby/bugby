package org.bugby.api.wildcard;

import japa.parser.ast.Node;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Comparator;

/**
 * annotate more than one element in the pattern tree with this annotation, to tell to the matcher to execute the
 * comparison on both annotated node. If the comparator does not return 0, than the node combination is not considered a
 * match.
 * 
 * @author acraciun
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Correlation {
	String key();

	Class<? extends Comparator<Node>> comparator();
}
