package org.richast;

import japa.parser.ast.CompilationUnit;

import java.io.File;
import java.util.Collections;

import org.richast.type.ClassLoaderWrapper;

public class Main {

	public static void main(String[] args) {
		ClassLoader builtProjectClassLoader = Thread.currentThread().getContextClassLoader();
		ClassLoaderWrapper classLoaderWrapper = new ClassLoaderWrapper(builtProjectClassLoader,
				Collections.<String>emptyList(), Collections.<String>emptyList());
		File inputFile = new File(args[0]);
		GenerationContext context = new GenerationContext(inputFile);
		CompilationUnit cu = RichASTParser.parseAndResolve(classLoaderWrapper, inputFile, context, "UTF-8");
	}
}
