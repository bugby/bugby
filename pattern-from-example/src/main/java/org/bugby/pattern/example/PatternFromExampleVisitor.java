package org.bugby.pattern.example;

import japa.parser.ast.CompilationUnit;
import japa.parser.ast.ImportDeclaration;
import japa.parser.ast.Node;
import japa.parser.ast.PackageDeclaration;
import japa.parser.ast.expr.MethodCallExpr;
import japa.parser.ast.stmt.BlockStmt;

import org.richast.visitor.ForEachNodeVisitor;

public class PatternFromExampleVisitor extends ForEachNodeVisitor<PatternBuilder> {
	private Node lastAdded = null;

	@Override
	protected void before(Node node, PatternBuilder builder) {
		if (node != lastAdded) {
			builder.addMatcher("", node);
		}
	}

	@Override
	public void visit(MethodCallExpr n, PatternBuilder builder) {
		builder.addMatcher(n.getName(), n);
		lastAdded = n;
		super.visit(n, builder);
	}

	@Override
	public void visit(PackageDeclaration n, PatternBuilder arg) {
		//skip
	}

	@Override
	public void visit(ImportDeclaration n, PatternBuilder arg) {
		//skip
	}

	@Override
	public void visit(CompilationUnit n, PatternBuilder arg) {
		lastAdded = n;//don't add
		super.visit(n, arg);
	}

	@Override
	public void visit(BlockStmt n, PatternBuilder arg) {
		lastAdded = n;//don't add
		super.visit(n, arg);
	}
}
