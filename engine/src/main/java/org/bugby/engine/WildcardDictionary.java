package org.bugby.engine;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bugby.api.wildcard.TreeMatcher;

/**
 * This class contains the wildcards (matchers) that can be used to the nodes from the AST.
 * 
 * @author acraciun
 * 
 */
public class WildcardDictionary {
	private Map<String, Class<? extends TreeMatcher>> matchers = new HashMap<String, Class<? extends TreeMatcher>>();
	// TODO - use full name
	private Set<String> annotations = new HashSet<String>();

	public void addMatcherClass(String name, Class<? extends TreeMatcher> matcherClass) {
		matchers.put(name, matcherClass);
	}

	public Class<? extends TreeMatcher> findMatcherClass(String name) {
		return matchers.get(name);
	}

	public void addAnnotation(String name) {
		annotations.add(name);
	}

	public boolean isAnnotation(String name) {
		return annotations.contains(name);
	}
}
