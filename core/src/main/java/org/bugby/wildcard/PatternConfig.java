package org.bugby.wildcard;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * this annotation marks other annotations that present in the pattern file only to contral the matching on the annotatated elements.
 * @author acraciun
 */
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PatternConfig {//
}
