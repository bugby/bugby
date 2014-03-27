package org.bugby.api.javac;

import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.Tree;
import com.sun.source.util.Trees;

public class ParsedSource {
	private final CompilationUnitTree compilationUnitTree;
	private final Trees trees;
	private final Types types;
	private final Elements elements;

	public ParsedSource(CompilationUnitTree compilationUnitTree, Trees trees, Types types, Elements elements) {
		this.compilationUnitTree = compilationUnitTree;
		this.trees = trees;
		this.types = types;
		this.elements = elements;
	}

	public CompilationUnitTree getCompilationUnitTree() {
		return compilationUnitTree;
	}

	public Trees getTrees() {
		return trees;
	}

	public Types getTypes() {
		return types;
	}

	public Elements getElements() {
		return elements;
	}

	public int getLine(Tree node) {
		long startPos = trees.getSourcePositions().getStartPosition(compilationUnitTree, node);
		return startPos >= 0 ? (int) compilationUnitTree.getLineMap().getLineNumber(startPos) : 0;
	}
}
