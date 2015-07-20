package org.bugby.api.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * this is used to "annotate" statements as this is not possible using regular annotations.
 * 
 * @author acraciun
 *
 */
@Target(ElementType.METHOD)
public @interface PseudoAnnotation {
	Class<? extends Annotation> value();
}
