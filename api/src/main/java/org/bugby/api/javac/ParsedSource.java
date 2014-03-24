package org.bugby.api.javac;

import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.Tree;
import com.sun.source.util.Trees;

public class ParsedSource {
	private final CompilationUnitTree compilationUnitTree;
	private final Trees trees;

	public ParsedSource(CompilationUnitTree compilationUnitTree, Trees trees) {
		this.compilationUnitTree = compilationUnitTree;
		this.trees = trees;
	}

	public CompilationUnitTree getCompilationUnitTree() {
		return compilationUnitTree;
	}

	public Trees getTrees() {
		return trees;
	}

	public int getLine(Tree node) {
		long startPos = trees.getSourcePositions().getStartPosition(compilationUnitTree, node);
		return startPos >= 0 ? (int) compilationUnitTree.getLineMap().getLineNumber(startPos) : 0;
	}
}
