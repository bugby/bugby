package org.richast;

import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.richast.scope.CompilationUnitScope;
import org.richast.scope.ScopeBuilder;
import org.richast.type.ClassLoaderWrapper;
import org.richast.visitor.SetParentVisitor;

import com.google.common.io.Closeables;

public class RichASTParser {
	public static CompilationUnit parseAndResolve(ClassLoaderWrapper builtProjectClassLoader, File inputFile,
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
}
