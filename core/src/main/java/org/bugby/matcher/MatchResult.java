package org.bugby.matcher;

import java.io.File;

import org.bugby.api.TreeMatcher;
import org.bugby.matcher.javac.ParsedSource;

import com.google.common.collect.Multimap;
import com.sun.source.tree.Tree;

public class MatchResult {
	private final ParsedSource parsedSource;
	private final Multimap<TreeMatcher, Tree> matches;

	public MatchResult(ParsedSource parsedSource, Multimap<TreeMatcher, Tree> matches) {
		this.parsedSource = parsedSource;
		this.matches = matches;
	}

	public ParsedSource getParsedSource() {
		return parsedSource;
	}

	public Multimap<TreeMatcher, Tree> getMatches() {
		return matches;
	}

	public Tree getBestMatch() {
		int maxLine = -1;
		Tree maxNode = null;

		for (Tree node : matches.values()) {
			if (parsedSource.getLine(node) >= maxLine) {
				maxLine = parsedSource.getLine(node);
				maxNode = node;
			}
		}

		if (maxNode != null) {
			System.err.println("Found match at: (" + new File(parsedSource.getCompilationUnitTree().getSourceFile().getName()).getName() + ":"
					+ parsedSource.getLine(maxNode) + ") ->" + maxNode);
		} else {
			System.out.println("No match was found");
		}
		return maxNode;
	}
}
