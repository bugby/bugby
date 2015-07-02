package org.bugby.matcher.javac;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;

import org.bugby.api.BugbyException;
import org.bugby.api.SourcePosition;

import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.util.JavacTask;
import com.sun.source.util.Trees;
import com.sun.tools.javac.api.JavacTool;

public class SourceParser {

	public static ParsedSource parse(File inputFile, ClassLoader builtProjectClassLoader, String sourceEncoding) {
		JavaCompiler.CompilationTask task = null;
		JavacTask javacTask = null;
		try {
			JavaCompiler compiler = JavacTool.create();
			if (compiler == null) {
				throw new BugbyException(
						"A Java compiler is not available for this project. You may have configured your environment to run with JRE instead of a JDK");
			}

			StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, Charset.forName(sourceEncoding));
			JavaFileManager classLoaderFileManager = new CustomClassloaderJavaFileManager(builtProjectClassLoader, fileManager);

			Iterable<? extends JavaFileObject> fileObjects = fileManager.getJavaFileObjectsFromFiles(Collections.singleton(inputFile));
			List<String> options = Arrays.asList("-proc:none");
			task = compiler.getTask(null, classLoaderFileManager, null, options, null, fileObjects);
			javacTask = (JavacTask) task;

			// context.setTrees(Trees.instance(javacTask));
			// context.setElements(javacTask.getElements());
			// context.setTypes(javacTask.getTypes());

			CompilationUnitTree cu = javacTask.parse().iterator().next();

			javacTask.analyze();

			return new ParsedSource(cu, Trees.instance(javacTask), javacTask.getTypes(), javacTask.getElements());
		} catch (IOException e) {
			throw new BugbyException(new SourcePosition(inputFile, 0, 0), "Cannot parse the Java file:" + e);
		}

	}
}
