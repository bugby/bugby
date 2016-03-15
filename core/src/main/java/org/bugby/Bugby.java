package org.bugby;

import java.util.List;

import org.bugby.api.TreeMatcherFactory;
import org.bugby.api.annotation.Correlation;
import org.bugby.api.annotation.MatchCount;
import org.bugby.api.annotation.Missing;
import org.bugby.api.annotation.OrSet;
import org.bugby.matcher.DefaultTreeMatcherFactory;
import org.bugby.matcher.javac.source.SourceParser;
import org.bugby.wildcard.WildcardAnnotations;
import org.bugby.wildcard.Wildcards;
import org.bugby.wildcard.type.SomeType;

public class Bugby {

	public static TreeMatcherFactory newTreeMatcherFactory(ClassLoader builtProjectClassLoader, List<SourceParser> sourceParsers) {

		// 2. read patterns
		DefaultTreeMatcherFactory matcherFactory = new DefaultTreeMatcherFactory(builtProjectClassLoader);
		matcherFactory.setSourceParsers(sourceParsers);
		//TODO here add more custom wildcards by dynamic discovery
		matcherFactory.addWildcardsFromClass(Wildcards.class);
		matcherFactory.addWildcardsFromClass(SomeType.class);
		matcherFactory.addWildcardsFromClass(WildcardAnnotations.class);
		matcherFactory.addWildcardsFromClass(MatchCount.class);
		matcherFactory.addWildcardsFromClass(Missing.class);
		matcherFactory.addWildcardsFromClass(OrSet.class);
		matcherFactory.addWildcardsFromClass(Correlation.class);
		return matcherFactory;
	}

	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("Usage: Main <pathToPatternFile> <pathToSourceFileToCheck>");
			return;
		}

		// see MainTest for some examples
		//check(args[0], args[1]);

	}
}
