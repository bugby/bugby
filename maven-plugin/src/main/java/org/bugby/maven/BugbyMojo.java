/**
 * Copyright 2011 Alexandru Craciun, Eyal Kaspi
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.bugby.maven;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.bugby.Bugby;
import org.bugby.api.TreeMatcher;
import org.bugby.api.annotation.Pattern;
import org.bugby.matcher.DefaultTreeMatcherFactory;
import org.bugby.matcher.javac.source.MavenArtifactSourceParser;
import org.bugby.matcher.javac.source.ProjectFileSourceParser;
import org.bugby.matcher.javac.source.ReflectionSourceParser;
import org.bugby.matcher.javac.source.SourceParser;
import org.codehaus.plexus.util.DirectoryScanner;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.repository.RemoteRepository;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.slf4j.impl.StaticLoggerBinder;
import org.sonatype.plexus.build.incremental.BuildContext;

/**
 * This is the Maven plugin that launches the Javascript generator. The plugin needs a list of packages containing the Java classes that will
 * processed to generate the corresponding Javascript classes. The Javascript files are generated in a configured target folder.
 * @author <a href='mailto:ax.craciun@gmail.com'>Alexandru Craciun</a>
 * @goal verify
 * @phase process-classes
 * @requiresDependencyResolution compile
 */
public class BugbyMojo extends AbstractMojo {
	private static final Logger LOG = Logger.getLogger(BugbyMojo.class.getName());

	private static final Object PACKAGE_INFO_JAVA = "package-info.java";

	/**
	 * @parameter property="project"
	 * @required
	 * @readonly
	 */
	protected MavenProject project;

	/**
	 * @component
	 */
	protected BuildContext buildContext;

	/**
	 * A list of inclusion filters for the compiler.
	 * @parameter
	 */
	protected Set<String> includes = new HashSet<String>();

	/**
	 * A list of exclusion filters for the compiler.
	 * @parameter
	 */
	protected Set<String> excludes = new HashSet<String>();

	/**
	 * Sets the granularity in milliseconds of the last modification date for testing whether a source needs recompilation.
	 * @parameter property="lastModGranularityMs" default-value="0"
	 */
	protected int staleMillis;

	/**
	 * @parameter property="sourceEncoding" default-value="${project.build.sourceEncoding}"
	 */
	private String sourceEncoding;

	/**
	 * A list of annotations to be generated
	 * @parameter
	 */
	protected Set<String> annotations = new HashSet<String>();

	/**
	 * @parameter default-value="${project.build.outputDirectory}"
	 */
	private File buildOutputDirectory;

	/**
	 * The entry point to Aether, i.e. the component doing all the work.
	 *
	 * @component
	 */
	private RepositorySystem repoSystem;

	/**
	 * The current repository/network configuration of Maven.
	 *
	 * @parameter default-value="${repositorySystemSession}"
	 * @readonly
	 */
	private RepositorySystemSession repoSession;

	/**
	 * The project's remote repositories to use for the resolution.
	 *
	 * @parameter default-value="${project.remoteProjectRepositories}"
	 * @readonly
	 */
	private List<RemoteRepository> remoteRepos;

	private ClassLoader getBuiltProjectClassLoader() throws MojoExecutionException {
		try {
			List<String> runtimeClasspathElements = getClasspathElements();
			URL[] runtimeUrls = new URL[runtimeClasspathElements.size()];
			for (int i = 0; i < runtimeClasspathElements.size(); i++) {
				String element = runtimeClasspathElements.get(i);
				getLog().debug("Classpath:" + element);
				runtimeUrls[i] = new File(element).toURI().toURL();
			}
			return new URLClassLoader(runtimeUrls, Thread.currentThread().getContextClassLoader().getParent());
		}
		catch (Exception ex) {
			throw new MojoExecutionException("Cannot get builtProjectClassLoader " + ex, ex);
		}
	}

	@SuppressWarnings("unchecked")
	protected List<String> getClasspathElements() throws DependencyResolutionRequiredException {
		return project.getCompileClasspathElements();
	}

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		StaticLoggerBinder.getSingleton().setMavenLog(this.getLog());

		List<SourceParser> sourceParsers = Arrays.<SourceParser> asList(
			new ProjectFileSourceParser(sourceEncoding, getBuiltProjectClassLoader()),
			new MavenArtifactSourceParser(sourceEncoding, getBuiltProjectClassLoader(), Thread.currentThread().getContextClassLoader(),
					repoSystem, repoSession, remoteRepos),
			new ReflectionSourceParser(sourceEncoding, getBuiltProjectClassLoader())
				);
		DefaultTreeMatcherFactory matcherFactory =
				(DefaultTreeMatcherFactory) Bugby.newTreeMatcherFactory(getBuiltProjectClassLoader(), sourceParsers);

		Collection<URL> dependencies = ClasspathHelper.forClassLoader();
		for (URL dependency : dependencies) {
			boolean haveSources = false;
			Reflections reflections = new Reflections(dependency);
			for (Class<?> bugClass : reflections.getTypesAnnotatedWith(Pattern.class)) {
				Pattern pattern = bugClass.getAnnotation(Pattern.class);
				if (pattern != null && !pattern.root()) {
					getLog().info("NOT root:" + bugClass);
					continue;
				}

				TreeMatcher rootMatcher = matcherFactory.buildForType(bugClass.getName());
			}
		}

	}

	//	@Override
	//	public void execute() throws MojoExecutionException, MojoFailureException {
	//		GenerationDirectory gendir = getGeneratedSourcesDirectory();
	//
	//		long t1 = System.currentTimeMillis();
	//		getLog().info("Generating JavaScript files to " + gendir.getGeneratedSourcesAbsolutePath());
	//
	//		ClassLoader builtProjectClassLoader = getBuiltProjectClassLoader();
	//
	//		GeneratorConfigurationBuilder configBuilder = new GeneratorConfigurationBuilder();
	//		configBuilder.generateArrayHasOwnProperty(generateArrayHasOwnProperty);
	//		configBuilder.generateSourceMap(generateSourceMap);
	//		if (sourceEncoding != null) {
	//			configBuilder.sourceEncoding(sourceEncoding);
	//		}
	//
	//		// configBuilder.allowedPackage("org.stjs.javascript");
	//		configBuilder.allowedPackage("org.junit");
	//		// configBuilder.allowedPackage("org.stjs.testing");
	//
	//		if (allowedPackages != null) {
	//			configBuilder.allowedPackages(allowedPackages);
	//		}
	//		if (annotations != null) {
	//			configBuilder.annotations(annotations);
	//		}
	//
	//		// scan all the packages
	//		for (String sourceRoot : getCompileSourceRoots()) {
	//			File sourceDir = new File(sourceRoot);
	//			Collection<String> packages = accumulatePackages(sourceDir);
	//			configBuilder.allowedPackages(packages);
	//		}
	//		configBuilder.stjsClassLoader(builtProjectClassLoader);
	//		configBuilder.targetFolder(getBuildOutputDirectory());
	//		configBuilder.generationFolder(gendir);
	//
	//		GeneratorConfiguration configuration = configBuilder.build();
	//		Generator generator = new Generator(configuration);
	//
	//		int generatedFiles = 0;
	//		boolean hasFailures = false;
	//		// scan the modified sources
	//		for (String sourceRoot : getCompileSourceRoots()) {
	//			File sourceDir = new File(sourceRoot);
	//			SourceMapping mapping = new SuffixMapping(".java", ".js");
	//			SourceMapping stjsMapping = new SuffixMapping(".java", ".stjs");
	//
	//			List<File> sources = accumulateSources(gendir, sourceDir, mapping, stjsMapping, staleMillis);
	//			for (File source : sources) {
	//				if (source.getName().equals(PACKAGE_INFO_JAVA)) {
	//					getLog().debug("Skipping " + source);
	//					continue;
	//				}
	//				File absoluteSource = new File(sourceDir, source.getPath());
	//				try {
	//					File absoluteTarget =
	//							(File) mapping.getTargetFiles(gendir.getGeneratedSourcesAbsolutePath(), source.getPath()).iterator().next();
	//					if (getLog().isDebugEnabled()) {
	//						getLog().debug("Generating " + absoluteTarget);
	//					}
	//					buildContext.removeMessages(absoluteSource);
	//
	//					if (!absoluteTarget.getParentFile().exists() && !absoluteTarget.getParentFile().mkdirs()) {
	//						getLog().error("Cannot create output directory:" + absoluteTarget.getParentFile());
	//						continue;
	//					}
	//					String className = getClassNameForSource(source.getPath());
	//					ClassWithJavascript stjsClass = generator.generateJavascript(className, sourceDir);
	//					if (!(stjsClass instanceof BridgeClass)) {
	//						++generatedFiles;
	//					}
	//
	//				}
	//				catch (InclusionScanException e) {
	//					throw new MojoExecutionException("Cannot scan the source directory:" + e, e);
	//				}
	//				catch (MultipleFileGenerationException e) {
	//					for (JavascriptFileGenerationException jse : e.getExceptions()) {
	//						buildContext.addMessage(jse.getSourcePosition().getFile(), jse.getSourcePosition().getLine(),
	//							jse.getSourcePosition().getColumn(), jse.getMessage(), BuildContext.SEVERITY_ERROR, null);
	//					}
	//					hasFailures = true;
	//					// continue with the next file
	//				}
	//				catch (JavascriptFileGenerationException e) {
	//					buildContext.addMessage(e.getSourcePosition().getFile(), e.getSourcePosition().getLine(), e.getSourcePosition().getColumn(),
	//						e.getMessage(), BuildContext.SEVERITY_ERROR, null);
	//					hasFailures = true;
	//					// continue with the next file
	//				}
	//				catch (Exception e) {
	//					// TODO - maybe should filter more here
	//					buildContext.addMessage(absoluteSource, 1, 1, e.toString(), BuildContext.SEVERITY_ERROR, e);
	//					hasFailures = true;
	//					// throw new MojoExecutionException("Error generating javascript:" + e, e);
	//				}
	//			}
	//		}
	//		generator.close();
	//		long t2 = System.currentTimeMillis();
	//		getLog().info("Generated " + generatedFiles + " JavaScript files in " + (t2 - t1) + " ms");
	//		if (generatedFiles > 0) {
	//			filesGenerated(generator, gendir);
	//		}
	//
	//		if (hasFailures) {
	//			throw new MojoFailureException("Errors generating JavaScript");
	//		}
	//	}
	//
	//
	//
	//	/**
	//	 * packs all the files in a single file
	//	 * @param generator
	//	 * @param gendir
	//	 * @throws MojoFailureException
	//	 * @throws MojoExecutionException
	//	 */
	//	protected void packFiles(Generator generator, GenerationDirectory gendir) throws MojoFailureException, MojoExecutionException {
	//		if (!pack) {
	//			return;
	//		}
	//		OutputStream allSourcesFile = null;
	//		Writer packMapStream = null;
	//		ClassLoader builtProjectClassLoader = getBuiltProjectClassLoader();
	//		Map<String, File> currentProjectsFiles = new HashMap<String, File>();
	//
	//		// pack the files
	//		try {
	//			File outputFile = new File(gendir.getGeneratedSourcesAbsolutePath(), project.getArtifactId() + ".js");
	//			allSourcesFile = new BufferedOutputStream(new FileOutputStream(outputFile));
	//			for (String sourceRoot : getCompileSourceRoots()) {
	//				File sourceDir = new File(sourceRoot);
	//				List<File> sources = new ArrayList<File>();
	//				SourceMapping mapping = new SuffixMapping(".java", ".js");
	//				SourceMapping stjsMapping = new SuffixMapping(".java", ".stjs");
	//
	//				// take all the files
	//				sources = accumulateSources(gendir, sourceDir, mapping, stjsMapping, Integer.MIN_VALUE);
	//				for (File source : sources) {
	//
	//					File absoluteTarget =
	//							(File) mapping.getTargetFiles(gendir.getGeneratedSourcesAbsolutePath(), source.getPath()).iterator().next();
	//
	//					String className = getClassNameForSource(source.getPath());
	//					if (!absoluteTarget.exists()) {
	//						getLog().debug(className + " is a bridge. Don't add it to the pack file");
	//						continue;
	//					}
	//					// add this file to the hashmap to know that this class is part of the project
	//					currentProjectsFiles.put(className, absoluteTarget);
	//					if (getLog().isDebugEnabled()) {
	//						getLog().debug("Packing " + absoluteTarget);
	//					}
	//
	//				}
	//			}
	//
	//			// check for cycles
	//			detectCycles(dependencyGraph);
	//
	//		}
	//		catch (Exception ex) {
	//			throw new MojoFailureException("Error when packing files:" + ex.getMessage(), ex);
	//		}
	//		finally {
	//
	//			try {
	//				Closeables.close(allSourcesFile, true);
	//			}
	//			catch (IOException e) {
	//				LOG.log(Level.SEVERE, "IOException should not have been thrown.", e);
	//			}
	//
	//			try {
	//				Closeables.close(packMapStream, true);
	//			}
	//			catch (IOException e) {
	//				LOG.log(Level.SEVERE, "IOException should not have been thrown.", e);
	//			}
	//		}
	//
	//	}

	/**
	 * @return the list of Java source files to processed (those which are older than the corresponding Javascript file). The returned files are
	 *         relative to the given source directory.
	 */
	private Collection<String> accumulatePackages(File sourceDir) throws MojoExecutionException {
		final Collection<String> result = new HashSet<String>();
		if (sourceDir == null || !sourceDir.exists()) {
			return result;
		}

		DirectoryScanner ds = new DirectoryScanner();
		ds.setFollowSymlinks(true);
		ds.addDefaultExcludes();
		ds.setBasedir(sourceDir);
		ds.setIncludes(new String[] {"**/*.java"});
		ds.scan();
		for (String fileName : ds.getIncludedFiles()) {
			File file = new File(fileName);
			// Supports classes without packages
			result.add(file.getParent() == null ? "" : file.getParent().replace(File.separatorChar, '.'));
		}

		/*
		 * // Trim root path from file paths for (File file : staleFiles) { String filePath = file.getPath(); String
		 * basePath = sourceDir.getAbsoluteFile().toString(); result.add(new File(filePath.substring(basePath.length() +
		 * 1))); }
		 */
		return result;
	}

	private String getClassNameForSource(String sourcePath) {
		// remove ending .java and replace / by .
		return sourcePath.substring(0, sourcePath.length() - 5).replace(File.separatorChar, '.');
	}

	/**
	 * @return the list of Java source files to processed (those which are older than the corresponding Javascript file). The returned files are
	 *         relative to the given source directory.
	 * @throws IOException
	 * @throws MalformedURLException
	 */
	//	@SuppressWarnings("unchecked")
	//	private List<File> accumulateSources(GenerationDirectory gendir, File sourceDir,
	//			int stale) throws MojoExecutionException {
	//		final List<File> result = new ArrayList<File>();
	//		if (sourceDir == null || !sourceDir.exists()) {
	//			return result;
	//		}
	//		SourceInclusionScanner jsScanner = getSourceInclusionScanner(stale);
	//
	//		SourceInclusionScanner stjsScanner = getSourceInclusionScanner(stale);
	//		stjsScanner.addSourceMapping(stjsMapping);
	//
	//		final Set<File> staleFiles = new LinkedHashSet<File>();
	//
	//		for (File f : sourceDir.listFiles()) {
	//			if (!f.isDirectory()) {
	//				continue;
	//			}
	//
	//			try {
	//				staleFiles.addAll(jsScanner.getIncludedSources(f.getParentFile(), gendir.getGeneratedSourcesAbsolutePath()));
	//				staleFiles.addAll(stjsScanner.getIncludedSources(f.getParentFile(), getBuildOutputDirectory()));
	//			}
	//			catch (InclusionScanException e) {
	//				throw new MojoExecutionException(
	//						"Error scanning source root: \'" + sourceDir.getPath() + "\' " + "for stale files to recompile.", e);
	//			}
	//		}
	//
	//		// Trim root path from file paths
	//		for (File file : staleFiles) {
	//			String filePath = file.getPath();
	//			String basePath = sourceDir.getAbsoluteFile().toString();
	//			result.add(new File(filePath.substring(basePath.length() + 1)));
	//		}
	//		return result;
	//	}
	//
	//	protected SourceInclusionScanner getSourceInclusionScanner(int staleMillis) {
	//		SourceInclusionScanner scanner;
	//
	//		if (includes.isEmpty() && excludes.isEmpty()) {
	//			scanner = new StaleClassSourceScanner(staleMillis, getBuildOutputDirectory());
	//		} else {
	//			if (includes.isEmpty()) {
	//				includes.add("**/*.java");
	//			}
	//			scanner = new StaleClassSourceScanner(staleMillis, includes, excludes, getBuildOutputDirectory());
	//		}
	//
	//		return scanner;
	//	}

}
