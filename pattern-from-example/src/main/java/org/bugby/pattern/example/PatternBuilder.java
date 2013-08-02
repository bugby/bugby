package org.bugby.pattern.example;

import japa.parser.ast.CompilationUnit;
import japa.parser.ast.Node;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bugby.wildcard.WildcardNodeMatcherFromExample;
import org.bugby.wildcard.api.DefaultNodeMatcher;
import org.bugby.wildcard.api.WildcardDictionary;
import org.bugby.wildcard.api.WildcardNodeMatcher;
import org.richast.GenerationContext;
import org.richast.RichASTParser;
import org.richast.type.ClassLoaderWrapper;

public class PatternBuilder {
	private WildcardDictionary wildcardDictionary;
	private List<WildcardNodeMatcher<?>> matchers = new ArrayList<WildcardNodeMatcher<?>>();

	public WildcardDictionary getWildcardDictionary() {
		return wildcardDictionary;
	}

	public void setWildcardDictionary(WildcardDictionary wildcardDictionary) {
		this.wildcardDictionary = wildcardDictionary;
	}

	public void buildFromFile(ClassLoader builtProjectClassLoader, File file) {
		ClassLoaderWrapper classLoaderWrapper =
				new ClassLoaderWrapper(builtProjectClassLoader, Collections.<String> emptyList(), Collections.<String> emptyList());
		GenerationContext context = new GenerationContext(file);
		CompilationUnit cu = RichASTParser.parseAndResolve(classLoaderWrapper, file, context, "UTF-8");
		cu.accept(new PatternFromExampleVisitor(), this);
		// System.out.println(matchers);
	}

	public void addMatcher(String name, Node n) {
		//remove the ending digits
		String simpleMethodeName = name.replaceAll("\\d+$", "");
		Class<? extends WildcardNodeMatcher<?>> matcherClass = wildcardDictionary.findMatcherClass(simpleMethodeName);
		if (matcherClass != null) {
			try {
				WildcardNodeMatcher<?> matcher = matcherClass.newInstance();
				if (matcher instanceof WildcardNodeMatcherFromExample) {
					((WildcardNodeMatcherFromExample<?>) matcher).init(n);
				}
				matchers.add(matcher);
			}
			catch (InstantiationException e) {
				e.printStackTrace();
			}
			catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		} else {
			matchers.add(new DefaultNodeMatcher(n));
		}
	}

	public static void main(String[] args) {
		ClassLoader builtProjectClassLoader = Thread.currentThread().getContextClassLoader();
		WildcardDictionary wildcardDictionary = new WildcardDictionary();
		wildcardDictionary.addWildcardsFromFile(builtProjectClassLoader, new File(
				"../default-wildcards/src/main/java/org/bugby/wildcard/Wildcards.java"));
		// here add more custom wildcards by dynamic discovery

		PatternBuilder patternBuilder = new PatternBuilder();
		patternBuilder.setWildcardDictionary(wildcardDictionary);
		patternBuilder.buildFromFile(builtProjectClassLoader, new File(
				"../default-examples/src/main/java/org/bugby/bugs/pmd/CollapsibleIfStatements.java"));
		for (WildcardNodeMatcher m : patternBuilder.matchers) {
			System.out.println(m);
		}
	}
}
