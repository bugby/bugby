package org.bugby.matcher.javac.source;

import java.io.File;

import org.bugby.api.BugbyException;

/**
 *
 * this parses sources that are in the project's source folders
 * @author acraciun
 */
public class ProjectFileSourceParser extends AbstractSourceParser {
	//	private static final String[] SOURCE_PATHS = {"src/main/java", "src/test/java", "../core/src/main/java"};
	private static final String[] SOURCE_PATHS = {"src/main/java", "src/test/java"};

	private final String sourceEncoding;
	private final ClassLoader buildProjectClassLoader;

	private final File[] roots;
	private final String projectRootPath;

	public ProjectFileSourceParser(String sourceEncoding, ClassLoader buildProjectClassLoader) {
		this(sourceEncoding, ".", buildProjectClassLoader);
	}

	public ProjectFileSourceParser(String sourceEncoding, String projectRootPath, ClassLoader buildProjectClassLoader) {
		this.sourceEncoding = sourceEncoding;
		this.buildProjectClassLoader = buildProjectClassLoader;
		this.projectRootPath = projectRootPath;
		roots = new File[SOURCE_PATHS.length];
		for (int i = 0; i < SOURCE_PATHS.length; ++i) {
			roots[i] = new File(projectRootPath, SOURCE_PATHS[i]);
		}
	}

	private File sourcePath(String fileName) {
		for (File p : roots) {
			File f = new File(p, fileName);
			if (f.exists()) {
				return f;
			}
		}
		throw new BugbyException("Cannot find file:" + fileName);
	}

	private File sourceFile(String className) {
		return sourcePath(className.replace('.', File.separatorChar) + ".java");
	}

	@Override
	public ParsedSource parseSource(String typeName) throws BugbyException {
		return super.parse(null, sourceFile(typeName), typeName, buildProjectClassLoader, sourceEncoding);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [" + projectRootPath + "]";
	}
}
