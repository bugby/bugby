package org.bugby.pattern.example;

import japa.parser.ast.CompilationUnit;
import japa.parser.ast.Node;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.ConstructorDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.VariableDeclarator;
import japa.parser.ast.body.VariableDeclaratorId;
import japa.parser.ast.expr.BinaryExpr;
import japa.parser.ast.expr.FieldAccessExpr;
import japa.parser.ast.expr.IntegerLiteralExpr;
import japa.parser.ast.expr.MarkerAnnotationExpr;
import japa.parser.ast.expr.MethodCallExpr;
import japa.parser.ast.expr.NameExpr;
import japa.parser.ast.expr.StringLiteralExpr;
import japa.parser.ast.expr.VariableDeclarationExpr;
import japa.parser.ast.stmt.BlockStmt;
import japa.parser.ast.stmt.ExpressionStmt;
import japa.parser.ast.stmt.IfStmt;
import japa.parser.ast.type.PrimitiveType;
import japa.parser.ast.type.VoidType;

import java.util.HashMap;
import java.util.Map;

import org.bugby.pattern.example.model.CompilationUnitBridge;
import org.bugby.pattern.example.model.VirtualNodeBridge;
import org.bugby.pattern.example.model.body.ClassOrInterfaceDeclarationBridge;
import org.bugby.pattern.example.model.body.ConstructorDeclarationBridge;
import org.bugby.pattern.example.model.body.MethodDeclarationBridge;
import org.bugby.pattern.example.model.body.VariableDeclaratorBridge;
import org.bugby.pattern.example.model.body.VariableDeclaratorIdBridge;
import org.bugby.pattern.example.model.expr.BinaryExprBridge;
import org.bugby.pattern.example.model.expr.FieldAccessExprBridge;
import org.bugby.pattern.example.model.expr.IntegerLiteralExprBridge;
import org.bugby.pattern.example.model.expr.MarkerAnnotationExprBridge;
import org.bugby.pattern.example.model.expr.MethodCallExprBridge;
import org.bugby.pattern.example.model.expr.NameExprBridge;
import org.bugby.pattern.example.model.expr.StringLiteralExprBridge;
import org.bugby.pattern.example.model.expr.VariableDeclarationExprBridge;
import org.bugby.pattern.example.model.stmt.BlockStmtBridge;
import org.bugby.pattern.example.model.stmt.ExpressionStmtBridge;
import org.bugby.pattern.example.model.stmt.IfStmtBridge;
import org.bugby.pattern.example.model.type.PrimitiveTypeBridge;
import org.bugby.pattern.example.model.type.VoidTypeBridge;

public class ASTModelBridges {
	private static Map<Class<?>, ASTModelBridge> bridges = new HashMap<Class<?>, ASTModelBridge>();

	static {
		bridges.put(CompilationUnit.class, new CompilationUnitBridge());
		bridges.put(VirtualNode.class, new VirtualNodeBridge());

		bridges.put(ClassOrInterfaceDeclaration.class, new ClassOrInterfaceDeclarationBridge());
		bridges.put(ConstructorDeclaration.class, new ConstructorDeclarationBridge());
		bridges.put(MethodDeclaration.class, new MethodDeclarationBridge());
		bridges.put(VariableDeclarator.class, new VariableDeclaratorBridge());
		bridges.put(VariableDeclaratorId.class, new VariableDeclaratorIdBridge());

		bridges.put(MarkerAnnotationExpr.class, new MarkerAnnotationExprBridge());
		bridges.put(NameExpr.class, new NameExprBridge());
		bridges.put(MethodCallExpr.class, new MethodCallExprBridge());
		bridges.put(VariableDeclarationExpr.class, new VariableDeclarationExprBridge());
		bridges.put(IntegerLiteralExpr.class, new IntegerLiteralExprBridge());
		bridges.put(StringLiteralExpr.class, new StringLiteralExprBridge());
		bridges.put(BinaryExpr.class, new BinaryExprBridge());
		bridges.put(FieldAccessExpr.class, new FieldAccessExprBridge());

		bridges.put(VoidType.class, new VoidTypeBridge());
		bridges.put(PrimitiveType.class, new PrimitiveTypeBridge());

		bridges.put(IfStmt.class, new IfStmtBridge());
		bridges.put(ExpressionStmt.class, new ExpressionStmtBridge());
		bridges.put(BlockStmt.class, new BlockStmtBridge());
	}

	public static ASTModelBridge getBridge(Node node) {
		ASTModelBridge bridge = bridges.get(node.getClass());
		if (bridge == null) {
			throw new RuntimeException("Cannot find bridge for: " + node.getClass());
		}
		return bridge;
	}

}
