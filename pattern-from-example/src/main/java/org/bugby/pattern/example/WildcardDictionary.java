package org.bugby.pattern.example;

import java.util.HashMap;
import java.util.Map;

import org.bugby.wildcard.api.WildcardNodeMatcher;

/**
 * This class contains the wildcards (matchers) that can be used to the nodes from the AST.
 * 
 * @author acraciun
 * 
 */
public class WildcardDictionary {
	private Map<String, Class<? extends WildcardNodeMatcher>> matchers = new HashMap<String, Class<? extends WildcardNodeMatcher>>();

	public void addMatcherClass(String name, Class<? extends WildcardNodeMatcher> matcherClass) {
		matchers.put(name, matcherClass);
	}

	public Class<? extends WildcardNodeMatcher> findMatcherClass(String name) {
		return matchers.get(name);
	}

}
