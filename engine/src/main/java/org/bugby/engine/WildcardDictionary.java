package org.bugby.engine;

import java.util.HashMap;
import java.util.Map;

import org.bugby.api.wildcard.TreeMatcher;

/**
 * This class contains the wildcards (matchers) that can be used to the nodes from the AST.
 * @author acraciun
 */
public class WildcardDictionary {
	private Map<String, Class<? extends TreeMatcher>> matchers = new HashMap<String, Class<? extends TreeMatcher>>();

	public void addMatcherClass(String name, Class<? extends TreeMatcher> matcherClass) {
		matchers.put(name, matcherClass);
	}

	public Class<? extends TreeMatcher> findMatcherClass(String name) {
		return matchers.get(name);
	}

}
