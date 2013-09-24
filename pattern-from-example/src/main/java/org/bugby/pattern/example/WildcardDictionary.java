package org.bugby.pattern.example;

import java.util.HashMap;
import java.util.Map;

import org.bugby.wildcard.api.WildcardNodeMatcher;
import org.bugby.wildcard.api.WildcardNodeMatcherFactory;

/**
 * This class contains the wildcards (matchers) that can be used to the nodes from the AST.
 * 
 * @author acraciun
 * 
 */
public class WildcardDictionary {
	private Map<String, Class<? extends WildcardNodeMatcher>> matchers = new HashMap<String, Class<? extends WildcardNodeMatcher>>();
	private Map<String, WildcardNodeMatcherFactory> matcherFactories = new HashMap<String, WildcardNodeMatcherFactory>();

	public void addMatcherClass(String name, Class<? extends WildcardNodeMatcher> matcherClass) {
		matchers.put(name, matcherClass);
	}

	public Class<? extends WildcardNodeMatcher> findMatcherClass(String name) {
		return matchers.get(name);
	}

	public void addMatcherFactoryClass(String name, Class<? extends WildcardNodeMatcherFactory> matcherClass) {
		WildcardNodeMatcherFactory factoryInstance;
		try {
			factoryInstance = matcherClass.newInstance();
		}
		catch (InstantiationException e) {
			throw new RuntimeException(e);
		}
		catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}

		matcherFactories.put(name, factoryInstance);
	}

	public WildcardNodeMatcherFactory findMatcherFactory(String name) {
		return matcherFactories.get(name);
	}

}
