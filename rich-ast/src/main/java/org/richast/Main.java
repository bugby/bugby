package org.richast;

import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Collections;

import org.richast.scope.CompilationUnitScope;
import org.richast.scope.ScopeBuilder;
import org.richast.type.ClassLoaderWrapper;
import org.richast.visitor.SetParentVisitor;

import com.google.common.io.Closeables;

public class Main {
	private static CompilationUnit parseAndResolve(ClassLoaderWrapper builtProjectClassLoader, File inputFile,
			GenerationContext context, String sourceEncoding) {
		CompilationUnitScope unitScope = new CompilationUnitScope(builtProjectClassLoader, context);
		CompilationUnit cu = null;
		InputStream in = null;
		try {

			try {
				in = new FileInputStream(inputFile);
			} catch (FileNotFoundException e) {
				throw new JavascriptFileGenerationException(inputFile, null, e);
			}

			// parse the file
			cu = JavaParser.parse(in, sourceEncoding);

			// set the parent of each node
			cu.accept(new SetParentVisitor(), context);

			// ASTUtils.dumpXML(cu);

			// 1. read the scope of all declared variables and methods
			ScopeBuilder scopes = new ScopeBuilder(builtProjectClassLoader, context);
			scopes.visit(cu, unitScope);
			// rootScope.dump(" ");

		} catch (ParseException e) {
			throw new JavascriptFileGenerationException(inputFile, null, e);
		} finally {
			Closeables.closeQuietly(in);
		}
		return cu;
	}

	public static void main(String[] args) {
		ClassLoader builtProjectClassLoader = Thread.currentThread().getContextClassLoader();
		ClassLoaderWrapper classLoaderWrapper = new ClassLoaderWrapper(builtProjectClassLoader,
				Collections.<String>emptyList(), Collections.<String>emptyList());
		File inputFile = new File(args[0]);
		GenerationContext context = new GenerationContext(inputFile);
		CompilationUnit cu = parseAndResolve(classLoaderWrapper, inputFile, context, "UTF-8");
	}
}
