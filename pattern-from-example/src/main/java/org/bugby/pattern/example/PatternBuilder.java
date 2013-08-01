package org.bugby.pattern.example;

import japa.parser.ast.CompilationUnit;

import java.io.File;
import java.util.Collections;

import org.bugby.wildcard.api.WildcardDictionary;
import org.richast.GenerationContext;
import org.richast.RichASTParser;
import org.richast.type.ClassLoaderWrapper;
import org.richast.visitor.ForEachNodeVisitor;

public class PatternBuilder {
	private WildcardDictionary wildcardDictionary;

	public WildcardDictionary getWildcardDictionary() {
		return wildcardDictionary;
	}

	public void setWildcardDictionary(WildcardDictionary wildcardDictionary) {
		this.wildcardDictionary = wildcardDictionary;
	}

	public void buildFromFile(ClassLoader builtProjectClassLoader, File file) {
		ClassLoaderWrapper classLoaderWrapper = new ClassLoaderWrapper(builtProjectClassLoader,
				Collections.<String>emptyList(), Collections.<String>emptyList());
		GenerationContext context = new GenerationContext(file);
		CompilationUnit cu = RichASTParser.parseAndResolve(classLoaderWrapper, file, context, "UTF-8");
		cu.accept(new PatternFromExampleVisitor(), this);
		// System.out.println(matchers);
	}

	private static class PatternFromExampleVisitor extends ForEachNodeVisitor<PatternBuilder> {

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
	}
}
