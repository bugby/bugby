package org.bugby.api.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Comparator;

import com.sun.source.tree.Tree;

/**
 * annotate more than one element in the pattern tree with this annotation, to tell to the matcher to execute the
 * comparison on both annotated node. If the comparator does not return 0, than the node combination is not considered a
 * match.
 * 
 * @author acraciun
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@PatternConfig
public @interface Correlation {
	String key();

	Class<? extends Comparator<Tree>> comparator();
}
