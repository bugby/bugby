package org.bugby.matcher.javac.source;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.bugby.api.BugbyException;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.resolution.ArtifactRequest;
import org.eclipse.aether.resolution.ArtifactResolutionException;
import org.eclipse.aether.resolution.ArtifactResult;

public class MavenArtifactSourceParser extends AbstractSourceParser {
	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(MavenArtifactSourceParser.class);
	private final ClassLoader builtProjectClassLoader;
	private final ClassLoader patternClassLoader;

	private final RepositorySystem repoSystem;

	private final RepositorySystemSession repoSession;

	private final List<RemoteRepository> remoteRepos;

	private final String sourceEncoding;

	public MavenArtifactSourceParser(String sourceEncoding, ClassLoader builtProjectClassLoader, ClassLoader patternClassLoader,
			RepositorySystem repoSystem,
			RepositorySystemSession repoSession, List<RemoteRepository> remoteRepos) {
		this.builtProjectClassLoader = builtProjectClassLoader;
		this.patternClassLoader = patternClassLoader;
		this.remoteRepos = remoteRepos;
		this.repoSystem = repoSystem;
		this.repoSession = repoSession;
		this.sourceEncoding = sourceEncoding;
	}

	private String toClassFileName(String typeName) {
		return typeName.replace('.', '/') + ".class";
	}

	private URL getJarURL(URL resourceURL) {
		JarURLConnection connection;
		try {
			connection = (JarURLConnection) resourceURL.openConnection();
			return connection.getJarFileURL();
		}
		catch (IOException e) {
			throw new BugbyException("cannot open url: " + resourceURL, e);
		}

	}

	private File sourceFile(String className) {
		return new File(className.replace('.', File.separatorChar) + ".java");
	}

	@Override
	public ParsedSource parseSource(String typeName) throws BugbyException {
		URL url = patternClassLoader.getResource(toClassFileName(typeName));
		URL artifactURL = getJarURL(url);
		log.info("URL=" + getJarURL(url));
		//		if (!haveSources) {
		File jarSourceFile = downloadSourcesForArtifact(artifactURL);

		//			haveSources = true;
		//		}
		return super.parse(jarSourceFile, sourceFile(typeName), typeName, builtProjectClassLoader, sourceEncoding);
	}

	private File downloadSourcesForArtifact(URL artifactURL) throws BugbyException {
		Artifact artifact;
		try {
			//String artifactCoords = "org.eclipse.aether:aether-util:jar:sources:0.9.0.M2";
			String artifactCoords = getSourceArtifactCoords(artifactURL);
			artifact = new DefaultArtifact(artifactCoords);
		}
		catch (IllegalArgumentException | IOException e) {
			throw new BugbyException(e.getMessage(), e);
		}

		ArtifactRequest request = new ArtifactRequest();
		request.setArtifact(artifact);
		request.setRepositories(remoteRepos);

		log.info("Resolving artifact " + artifact + " from " + remoteRepos);

		ArtifactResult result;
		try {
			result = repoSystem.resolveArtifact(repoSession, request);
		}
		catch (ArtifactResolutionException e) {
			throw new BugbyException(e.getMessage(), e);
		}

		log.info("Resolved artifact " + artifact + " to " + result.getArtifact().getFile() + " from "
				+ result.getRepository());
		return result.getArtifact().getFile();
	}

	private String getSourceArtifactCoords(URL artifactURL) throws IOException {
		try (JarFile jarFile = new JarFile(artifactURL.getPath())) {
			Enumeration<JarEntry> entries = jarFile.entries();
			while (entries.hasMoreElements()) {
				JarEntry entry = entries.nextElement();
				if (entry.getName().endsWith("/pom.properties")) {
					InputStream resourceAsStream = this.getClass().getResourceAsStream("/" + entry.getName());
					Properties prop = new Properties();
					prop.load(resourceAsStream);
					return prop.getProperty("groupId") + ":" + prop.getProperty("artifactId") + ":jar:sources:" + prop.getProperty("version");
				}
			}
			return null;
		}
	}

}
