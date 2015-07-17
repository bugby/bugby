package org.bugby.matcher;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

import org.bugby.api.TreeMatcher;

/**
 * This class contains the wildcards (matchers) that can be used to the nodes from the AST.
 * 
 * @author acraciun
 */
public class WildcardDictionary {
	private Map<String, Class<? extends TreeMatcher>> matchers = new HashMap<String, Class<? extends TreeMatcher>>();
	private Map<String, Class<? extends Annotation>> pseudoAnnotations = new HashMap<String, Class<? extends Annotation>>();

	public void addMatcherClass(String name, Class<? extends TreeMatcher> matcherClass) {
		matchers.put(name, matcherClass);
	}

	public Class<? extends TreeMatcher> findMatcherClass(String name) {
		return matchers.get(name);
	}

	public void addPseudoAnnotation(String name, Class<? extends Annotation> clz) {
		pseudoAnnotations.put(name, clz);
	}

	public Class<? extends Annotation> getPseudoAnnotation(String name) {
		return pseudoAnnotations.get(name);
	}

}
