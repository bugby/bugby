package org.bugby.engine;

import japa.parser.ast.CompilationUnit;
import japa.parser.ast.ImportDeclaration;
import japa.parser.ast.LineComment;
import japa.parser.ast.Node;
import japa.parser.ast.PackageDeclaration;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.ConstructorDeclaration;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.JavadocComment;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.Parameter;
import japa.parser.ast.body.VariableDeclarator;
import japa.parser.ast.body.VariableDeclaratorId;
import japa.parser.ast.expr.AssignExpr;
import japa.parser.ast.expr.BinaryExpr;
import japa.parser.ast.expr.BooleanLiteralExpr;
import japa.parser.ast.expr.CastExpr;
import japa.parser.ast.expr.ClassExpr;
import japa.parser.ast.expr.ConditionalExpr;
import japa.parser.ast.expr.EnclosedExpr;
import japa.parser.ast.expr.FieldAccessExpr;
import japa.parser.ast.expr.IntegerLiteralExpr;
import japa.parser.ast.expr.MarkerAnnotationExpr;
import japa.parser.ast.expr.MemberValuePair;
import japa.parser.ast.expr.MethodCallExpr;
import japa.parser.ast.expr.NameExpr;
import japa.parser.ast.expr.NormalAnnotationExpr;
import japa.parser.ast.expr.NullLiteralExpr;
import japa.parser.ast.expr.ObjectCreationExpr;
import japa.parser.ast.expr.QualifiedNameExpr;
import japa.parser.ast.expr.SingleMemberAnnotationExpr;
import japa.parser.ast.expr.StringLiteralExpr;
import japa.parser.ast.expr.SuperExpr;
import japa.parser.ast.expr.ThisExpr;
import japa.parser.ast.expr.UnaryExpr;
import japa.parser.ast.expr.VariableDeclarationExpr;
import japa.parser.ast.stmt.BlockStmt;
import japa.parser.ast.stmt.BreakStmt;
import japa.parser.ast.stmt.CatchClause;
import japa.parser.ast.stmt.ContinueStmt;
import japa.parser.ast.stmt.ExpressionStmt;
import japa.parser.ast.stmt.ForStmt;
import japa.parser.ast.stmt.IfStmt;
import japa.parser.ast.stmt.ReturnStmt;
import japa.parser.ast.stmt.ThrowStmt;
import japa.parser.ast.stmt.TryStmt;
import japa.parser.ast.type.ClassOrInterfaceType;
import japa.parser.ast.type.PrimitiveType;
import japa.parser.ast.type.ReferenceType;
import japa.parser.ast.type.VoidType;

import java.util.HashMap;
import java.util.Map;

import org.bugby.engine.model.CompilationUnitBridge;
import org.bugby.engine.model.IgnoreBridge;
import org.bugby.engine.model.VirtualNodeBridge;
import org.bugby.engine.model.body.ClassOrInterfaceDeclarationBridge;
import org.bugby.engine.model.body.ConstructorDeclarationBridge;
import org.bugby.engine.model.body.FieldDeclarationBridge;
import org.bugby.engine.model.body.MethodDeclarationBridge;
import org.bugby.engine.model.body.ParameterBridge;
import org.bugby.engine.model.body.VariableDeclaratorBridge;
import org.bugby.engine.model.body.VariableDeclaratorIdBridge;
import org.bugby.engine.model.expr.AssignExprBridge;
import org.bugby.engine.model.expr.BinaryExprBridge;
import org.bugby.engine.model.expr.BooleanLiteralExprBridge;
import org.bugby.engine.model.expr.CastExprBridge;
import org.bugby.engine.model.expr.ClassExprBridge;
import org.bugby.engine.model.expr.ConditionalExprBridge;
import org.bugby.engine.model.expr.EnclosedExprBridge;
import org.bugby.engine.model.expr.FieldAccessExprBridge;
import org.bugby.engine.model.expr.IntegerLiteralExprBridge;
import org.bugby.engine.model.expr.MarkerAnnotationExprBridge;
import org.bugby.engine.model.expr.MemberValuePairBridge;
import org.bugby.engine.model.expr.MethodCallExprBridge;
import org.bugby.engine.model.expr.NameExprBridge;
import org.bugby.engine.model.expr.NormalAnnotationExprBridge;
import org.bugby.engine.model.expr.NullLiteralExprBridge;
import org.bugby.engine.model.expr.ObjectCreationExprBridge;
import org.bugby.engine.model.expr.SingleMemberAnnotationExprBridge;
import org.bugby.engine.model.expr.StringLiteralExprBridge;
import org.bugby.engine.model.expr.SuperExprBridge;
import org.bugby.engine.model.expr.ThisExprBridge;
import org.bugby.engine.model.expr.UnaryExprBridge;
import org.bugby.engine.model.expr.VariableDeclarationExprBridge;
import org.bugby.engine.model.stmt.BlockStmtBridge;
import org.bugby.engine.model.stmt.BreakStmtBridge;
import org.bugby.engine.model.stmt.CatchClauseBridge;
import org.bugby.engine.model.stmt.ContinueStmtBridge;
import org.bugby.engine.model.stmt.ExpressionStmtBridge;
import org.bugby.engine.model.stmt.ForStmtBridge;
import org.bugby.engine.model.stmt.IfStmtBridge;
import org.bugby.engine.model.stmt.ReturnStmtBridge;
import org.bugby.engine.model.stmt.ThrowStmtBridge;
import org.bugby.engine.model.stmt.TryStmtBridge;
import org.bugby.engine.model.type.ClassOrInterfaceTypeBridge;
import org.bugby.engine.model.type.PrimitiveTypeBridge;
import org.bugby.engine.model.type.ReferenceTypeBridge;
import org.bugby.engine.model.type.VoidTypeBridge;

public class ASTModelBridges {
	private static Map<Class<? extends Node>, ASTModelBridge> bridges = new HashMap<Class<? extends Node>, ASTModelBridge>();

	static {
		bridges.put(CompilationUnit.class, new CompilationUnitBridge());
		bridges.put(VirtualNode.class, new VirtualNodeBridge());

		bridges.put(ClassOrInterfaceDeclaration.class, new ClassOrInterfaceDeclarationBridge());
		bridges.put(ConstructorDeclaration.class, new ConstructorDeclarationBridge());
		bridges.put(MethodDeclaration.class, new MethodDeclarationBridge());
		bridges.put(VariableDeclarator.class, new VariableDeclaratorBridge());
		bridges.put(VariableDeclaratorId.class, new VariableDeclaratorIdBridge());
		bridges.put(Parameter.class, new ParameterBridge());
		bridges.put(FieldDeclaration.class, new FieldDeclarationBridge());

		bridges.put(MarkerAnnotationExpr.class, new MarkerAnnotationExprBridge());
		bridges.put(NormalAnnotationExpr.class, new NormalAnnotationExprBridge());
		bridges.put(SingleMemberAnnotationExpr.class, new SingleMemberAnnotationExprBridge());
		bridges.put(MemberValuePair.class, new MemberValuePairBridge());
		bridges.put(NameExpr.class, new NameExprBridge());
		bridges.put(MethodCallExpr.class, new MethodCallExprBridge());
		bridges.put(VariableDeclarationExpr.class, new VariableDeclarationExprBridge());
		bridges.put(IntegerLiteralExpr.class, new IntegerLiteralExprBridge());
		bridges.put(StringLiteralExpr.class, new StringLiteralExprBridge());
		bridges.put(BinaryExpr.class, new BinaryExprBridge());
		bridges.put(FieldAccessExpr.class, new FieldAccessExprBridge());
		bridges.put(SuperExpr.class, new SuperExprBridge());
		bridges.put(ThisExpr.class, new ThisExprBridge());
		bridges.put(ClassExpr.class, new ClassExprBridge());
		bridges.put(ObjectCreationExpr.class, new ObjectCreationExprBridge());
		bridges.put(AssignExpr.class, new AssignExprBridge());
		bridges.put(EnclosedExpr.class, new EnclosedExprBridge());
		bridges.put(ConditionalExpr.class, new ConditionalExprBridge());
		bridges.put(NullLiteralExpr.class, new NullLiteralExprBridge());
		bridges.put(BooleanLiteralExpr.class, new BooleanLiteralExprBridge());
		bridges.put(CastExpr.class, new CastExprBridge());
		bridges.put(UnaryExpr.class, new UnaryExprBridge());

		bridges.put(VoidType.class, new VoidTypeBridge());
		bridges.put(PrimitiveType.class, new PrimitiveTypeBridge());
		bridges.put(ClassOrInterfaceType.class, new ClassOrInterfaceTypeBridge());
		bridges.put(ReferenceType.class, new ReferenceTypeBridge());

		bridges.put(IfStmt.class, new IfStmtBridge());
		bridges.put(ExpressionStmt.class, new ExpressionStmtBridge());
		bridges.put(BlockStmt.class, new BlockStmtBridge());
		bridges.put(ReturnStmt.class, new ReturnStmtBridge());
		bridges.put(TryStmt.class, new TryStmtBridge());
		bridges.put(CatchClause.class, new CatchClauseBridge());
		bridges.put(ThrowStmt.class, new ThrowStmtBridge());
		bridges.put(ForStmt.class, new ForStmtBridge());
		bridges.put(BreakStmt.class, new BreakStmtBridge());
		bridges.put(ContinueStmt.class, new ContinueStmtBridge());

		bridges.put(PackageDeclaration.class, new IgnoreBridge());
		bridges.put(ImportDeclaration.class, new IgnoreBridge());
		bridges.put(LineComment.class, new IgnoreBridge());
		bridges.put(QualifiedNameExpr.class, new IgnoreBridge());
		bridges.put(JavadocComment.class, new IgnoreBridge());
	}

	public static ASTModelBridge getBridge(Node node) {
		ASTModelBridge bridge = bridges.get(node.getClass());
		if (bridge == null) {
			throw new RuntimeException("Cannot find bridge for: " + node.getClass());
		}
		return bridge;
	}

}
