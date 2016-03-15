package org.bugby.matcher.javac.source;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;
import javax.tools.StandardJavaFileManager;

import org.bugby.api.BugbyException;
import org.bugby.api.SourcePosition;
import org.bugby.matcher.javac.CustomClassloaderJavaFileManager;
import org.bugby.matcher.javac.CustomJavaFileObject;

import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.util.JavacTask;
import com.sun.source.util.Trees;
import com.sun.tools.javac.api.JavacTool;

abstract public class AbstractSourceParser implements SourceParser {
	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(AbstractSourceParser.class);

	private static final String CLASS_FILE_EXTENSION = ".java";

	private void addFileObject(String jarUri, String name, String rootEntryName, int rootEnd, List<JavaFileObject> result, boolean recursive) {
		boolean acceptCurrentFolder = recursive || name.indexOf('/', rootEnd) == -1;
		if (acceptCurrentFolder && name.startsWith(rootEntryName) && name.endsWith(CLASS_FILE_EXTENSION)) {
			URI uri = URI.create(jarUri + "!/" + name);
			String binaryName = name.replaceAll("/", ".");
			binaryName = binaryName.replaceAll(CLASS_FILE_EXTENSION + "$", "");

			result.add(new CustomJavaFileObject(binaryName, uri));
		}
	}

	private List<JavaFileObject> processJar(URL packageFolderURL, boolean recursive) {
		log.info("OPENING:" + packageFolderURL);
		List<JavaFileObject> result = new ArrayList<JavaFileObject>();
		try {

			//			URLConnection urlConnection = packageFolderURL.openConnection();
			//			if (!(urlConnection instanceof JarURLConnection)) {
			//				// weird file in the classpath
			//				return Collections.emptyList();
			//			}

			String jarUri = packageFolderURL.toExternalForm().split("!")[0];
			//			JarURLConnection jarConn = (JarURLConnection) urlConnection;
			//			String rootEntryName = jarConn.getEntryName();
			String rootEntryName = packageFolderURL.getPath();
			int rootEnd = rootEntryName.length() + 1;

			//Enumeration<JarEntry> entryEnum = jarConn.getJarFile().entries();
			JarFile jarFile = new JarFile(packageFolderURL.getPath());
			Enumeration<JarEntry> entryEnum = jarFile.entries();
			while (entryEnum.hasMoreElements()) {
				JarEntry jarEntry = entryEnum.nextElement();
				String name = jarEntry.getName();
				log.info("ENTRY:" + name);
				addFileObject(jarUri, name, rootEntryName, rootEnd, result, recursive);
			}
		}
		catch (Exception e) {
			throw new BugbyException("Wasn't able to open " + packageFolderURL + " as a jar file", e);
		}
		return result;
	}

	protected ParsedSource parse(File jarFile, File inputFile, String typeName, ClassLoader builtProjectClassLoader, String sourceEncoding) {
		JavaCompiler.CompilationTask task = null;
		JavacTask javacTask = null;
		try {
			JavaCompiler compiler = JavacTool.create();
			if (compiler == null) {
				throw new BugbyException(
						"A Java compiler is not available for this project. You may have configured your environment to run with JRE instead of a JDK");
			}

			StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, Charset.forName(sourceEncoding));
			Iterable<? extends JavaFileObject> fileObjects = null;
			if (jarFile == null) {
				fileObjects = fileManager.getJavaFileObjectsFromFiles(Collections.singleton(inputFile));
			} else {
				//List<JavaFileObject> allFiles = processJar(jarFile.toURL(), false);
				fileObjects =
						Collections.singleton(new CustomJavaFileObject(typeName, URI.create(jarFile.getAbsolutePath() + "!/"
								+ inputFile.getPath()), Kind.SOURCE));
			}

			List<String> options = Arrays.asList("-proc:none");
			JavaFileManager classLoaderFileManager = new CustomClassloaderJavaFileManager(builtProjectClassLoader, fileManager);
			task = compiler.getTask(null, classLoaderFileManager, null, options, null, fileObjects);
			javacTask = (JavacTask) task;

			CompilationUnitTree cu = javacTask.parse().iterator().next();

			javacTask.analyze();

			return new ParsedSource(cu, Trees.instance(javacTask), javacTask.getTypes(), javacTask.getElements());
		}
		catch (Throwable e) {
			throw new BugbyException(new SourcePosition(inputFile, 0, 0), "Cannot parse the Java file:" + e, e);
		}

	}
}
