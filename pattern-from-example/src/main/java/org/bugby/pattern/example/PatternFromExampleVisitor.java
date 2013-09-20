package org.bugby.pattern.example;

import japa.parser.ast.BlockComment;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.ImportDeclaration;
import japa.parser.ast.LineComment;
import japa.parser.ast.Node;
import japa.parser.ast.PackageDeclaration;
import japa.parser.ast.TypeParameter;
import japa.parser.ast.body.AnnotationDeclaration;
import japa.parser.ast.body.AnnotationMemberDeclaration;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.ConstructorDeclaration;
import japa.parser.ast.body.EmptyMemberDeclaration;
import japa.parser.ast.body.EmptyTypeDeclaration;
import japa.parser.ast.body.EnumConstantDeclaration;
import japa.parser.ast.body.EnumDeclaration;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.InitializerDeclaration;
import japa.parser.ast.body.JavadocComment;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.Parameter;
import japa.parser.ast.body.VariableDeclarator;
import japa.parser.ast.body.VariableDeclaratorId;
import japa.parser.ast.expr.ArrayAccessExpr;
import japa.parser.ast.expr.ArrayCreationExpr;
import japa.parser.ast.expr.ArrayInitializerExpr;
import japa.parser.ast.expr.AssignExpr;
import japa.parser.ast.expr.BinaryExpr;
import japa.parser.ast.expr.BooleanLiteralExpr;
import japa.parser.ast.expr.CastExpr;
import japa.parser.ast.expr.CharLiteralExpr;
import japa.parser.ast.expr.ClassExpr;
import japa.parser.ast.expr.ConditionalExpr;
import japa.parser.ast.expr.DoubleLiteralExpr;
import japa.parser.ast.expr.EnclosedExpr;
import japa.parser.ast.expr.FieldAccessExpr;
import japa.parser.ast.expr.InstanceOfExpr;
import japa.parser.ast.expr.IntegerLiteralExpr;
import japa.parser.ast.expr.IntegerLiteralMinValueExpr;
import japa.parser.ast.expr.LongLiteralExpr;
import japa.parser.ast.expr.LongLiteralMinValueExpr;
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
import japa.parser.ast.stmt.AssertStmt;
import japa.parser.ast.stmt.BlockStmt;
import japa.parser.ast.stmt.BreakStmt;
import japa.parser.ast.stmt.CatchClause;
import japa.parser.ast.stmt.ContinueStmt;
import japa.parser.ast.stmt.DoStmt;
import japa.parser.ast.stmt.EmptyStmt;
import japa.parser.ast.stmt.ExplicitConstructorInvocationStmt;
import japa.parser.ast.stmt.ExpressionStmt;
import japa.parser.ast.stmt.ForStmt;
import japa.parser.ast.stmt.ForeachStmt;
import japa.parser.ast.stmt.IfStmt;
import japa.parser.ast.stmt.LabeledStmt;
import japa.parser.ast.stmt.ReturnStmt;
import japa.parser.ast.stmt.SwitchEntryStmt;
import japa.parser.ast.stmt.SwitchStmt;
import japa.parser.ast.stmt.SynchronizedStmt;
import japa.parser.ast.stmt.ThrowStmt;
import japa.parser.ast.stmt.TryStmt;
import japa.parser.ast.stmt.TypeDeclarationStmt;
import japa.parser.ast.stmt.WhileStmt;
import japa.parser.ast.type.ClassOrInterfaceType;
import japa.parser.ast.type.PrimitiveType;
import japa.parser.ast.type.ReferenceType;
import japa.parser.ast.type.VoidType;
import japa.parser.ast.type.WildcardType;

import java.util.Stack;

import org.bugby.matcher.tree.Tree;
import org.bugby.wildcard.api.WildcardNodeMatcher;
import org.richast.visitor.ForEachNodeVisitor;

public class PatternFromExampleVisitor extends ForEachNodeVisitor<PatternBuilder> {
	private Node lastAdded = null;
	private Stack<Tree<WildcardNodeMatcher>> parents = new Stack<Tree<WildcardNodeMatcher>>();
	private Stack<Node> addedNodes = new Stack<Node>();

	@Override
	protected void before(Node node, PatternBuilder builder) {
		if (node != lastAdded) {
			addMatcher(builder, "", node);
		}
	}

	@Override
	protected void after(Node node, PatternBuilder arg) {
		if (!addedNodes.isEmpty() && addedNodes.peek() == node) {
			addedNodes.pop();
			parents.pop();
		}
	}

	private void addMatcher(PatternBuilder builder, String name, Node node) {
		Tree<WildcardNodeMatcher> parentPatternNode = parents.isEmpty() ? null : parents.peek();
		Tree<WildcardNodeMatcher> newNode = builder.addMatcher(parentPatternNode, name, node);
		parents.push(newNode);
		addedNodes.push(node);
	}

	private void dontAdd(Node n) {
		lastAdded = n;// don't add
	}

	@Override
	public void visit(MethodCallExpr n, PatternBuilder builder) {
		addMatcher(builder, n.getName(), n);
		lastAdded = n;
		super.visit(n, builder);
	}

	@Override
	public void visit(VariableDeclarator n, PatternBuilder builder) {
		addMatcher(builder, n.getId().getName(), n);
		lastAdded = n;
		super.visit(n, builder);
	}

	@Override
	public void visit(PackageDeclaration n, PatternBuilder arg) {
		// skip
	}

	@Override
	public void visit(ImportDeclaration n, PatternBuilder arg) {
		// skip
	}

	@Override
	public void visit(CompilationUnit n, PatternBuilder builder) {
		super.visit(n, builder);
	}

	@Override
	public void visit(BlockStmt n, PatternBuilder builder) {
		dontAdd(n);
		super.visit(n, builder);
	}

	@Override
	public void visit(AnnotationDeclaration n, PatternBuilder builder) {

		super.visit(n, builder);

	}

	@Override
	public void visit(AnnotationMemberDeclaration n, PatternBuilder builder) {

		super.visit(n, builder);

	}

	@Override
	public void visit(ArrayAccessExpr n, PatternBuilder builder) {

		super.visit(n, builder);

	}

	@Override
	public void visit(ArrayCreationExpr n, PatternBuilder builder) {

		super.visit(n, builder);

	}

	@Override
	public void visit(ArrayInitializerExpr n, PatternBuilder builder) {

		super.visit(n, builder);

	}

	@Override
	public void visit(AssertStmt n, PatternBuilder builder) {

		super.visit(n, builder);

	}

	@Override
	public void visit(AssignExpr n, PatternBuilder builder) {

		super.visit(n, builder);

	}

	@Override
	public void visit(BinaryExpr n, PatternBuilder builder) {

		super.visit(n, builder);

	}

	@Override
	public void visit(BlockComment n, PatternBuilder builder) {

		super.visit(n, builder);

	}

	@Override
	public void visit(BooleanLiteralExpr n, PatternBuilder builder) {

		super.visit(n, builder);

	}

	@Override
	public void visit(BreakStmt n, PatternBuilder builder) {

		super.visit(n, builder);

	}

	@Override
	public void visit(CastExpr n, PatternBuilder builder) {

		super.visit(n, builder);

	}

	@Override
	public void visit(CatchClause n, PatternBuilder builder) {

		super.visit(n, builder);

	}

	@Override
	public void visit(CharLiteralExpr n, PatternBuilder builder) {

		super.visit(n, builder);

	}

	@Override
	public void visit(ClassExpr n, PatternBuilder builder) {

		super.visit(n, builder);

	}

	@Override
	public void visit(ClassOrInterfaceDeclaration n, PatternBuilder builder) {

		super.visit(n, builder);

	}

	@Override
	public void visit(ClassOrInterfaceType n, PatternBuilder builder) {

		super.visit(n, builder);

	}

	@Override
	public void visit(ConditionalExpr n, PatternBuilder builder) {

		super.visit(n, builder);

	}

	@Override
	public void visit(ConstructorDeclaration n, PatternBuilder builder) {

		super.visit(n, builder);

	}

	@Override
	public void visit(ContinueStmt n, PatternBuilder builder) {

		super.visit(n, builder);

	}

	@Override
	public void visit(DoStmt n, PatternBuilder builder) {

		super.visit(n, builder);

	}

	@Override
	public void visit(DoubleLiteralExpr n, PatternBuilder builder) {

		super.visit(n, builder);

	}

	@Override
	public void visit(EmptyMemberDeclaration n, PatternBuilder builder) {

		super.visit(n, builder);

	}

	@Override
	public void visit(EmptyStmt n, PatternBuilder builder) {

		super.visit(n, builder);

	}

	@Override
	public void visit(EmptyTypeDeclaration n, PatternBuilder builder) {

		super.visit(n, builder);

	}

	@Override
	public void visit(EnclosedExpr n, PatternBuilder builder) {

		super.visit(n, builder);

	}

	@Override
	public void visit(EnumConstantDeclaration n, PatternBuilder builder) {

		super.visit(n, builder);

	}

	@Override
	public void visit(EnumDeclaration n, PatternBuilder builder) {

		super.visit(n, builder);

	}

	@Override
	public void visit(ExplicitConstructorInvocationStmt n, PatternBuilder builder) {

		super.visit(n, builder);

	}

	@Override
	public void visit(ExpressionStmt n, PatternBuilder builder) {
		dontAdd(n);
		super.visit(n, builder);

	}

	@Override
	public void visit(FieldAccessExpr n, PatternBuilder builder) {

		super.visit(n, builder);

	}

	@Override
	public void visit(FieldDeclaration n, PatternBuilder builder) {

		super.visit(n, builder);

	}

	@Override
	public void visit(ForeachStmt n, PatternBuilder builder) {

		super.visit(n, builder);

	}

	@Override
	public void visit(ForStmt n, PatternBuilder builder) {

		super.visit(n, builder);

	}

	@Override
	public void visit(IfStmt n, PatternBuilder builder) {

		super.visit(n, builder);

	}

	@Override
	public void visit(InitializerDeclaration n, PatternBuilder builder) {

		super.visit(n, builder);

	}

	@Override
	public void visit(InstanceOfExpr n, PatternBuilder builder) {

		super.visit(n, builder);

	}

	@Override
	public void visit(IntegerLiteralExpr n, PatternBuilder builder) {

		super.visit(n, builder);

	}

	@Override
	public void visit(IntegerLiteralMinValueExpr n, PatternBuilder builder) {

		super.visit(n, builder);

	}

	@Override
	public void visit(JavadocComment n, PatternBuilder builder) {

		super.visit(n, builder);

	}

	@Override
	public void visit(LabeledStmt n, PatternBuilder builder) {

		super.visit(n, builder);

	}

	@Override
	public void visit(LineComment n, PatternBuilder builder) {
		dontAdd(n);
		super.visit(n, builder);

	}

	@Override
	public void visit(LongLiteralExpr n, PatternBuilder builder) {

		super.visit(n, builder);

	}

	@Override
	public void visit(LongLiteralMinValueExpr n, PatternBuilder builder) {

		super.visit(n, builder);

	}

	@Override
	public void visit(MarkerAnnotationExpr n, PatternBuilder builder) {
		// skip (TODO - some annotation must be kept!
		// super.visit(n, builder);
	}

	@Override
	public void visit(MemberValuePair n, PatternBuilder builder) {
		dontAdd(n);
		super.visit(n, builder);

	}

	@Override
	public void visit(MethodDeclaration n, PatternBuilder builder) {
		addMatcher(builder, n.getName(), n);
		lastAdded = n;
		super.visit(n, builder);

	}

	@Override
	public void visit(NameExpr n, PatternBuilder builder) {

		super.visit(n, builder);

	}

	@Override
	public void visit(NormalAnnotationExpr n, PatternBuilder builder) {

		super.visit(n, builder);

	}

	@Override
	public void visit(NullLiteralExpr n, PatternBuilder builder) {

		super.visit(n, builder);

	}

	@Override
	public void visit(ObjectCreationExpr n, PatternBuilder builder) {

		super.visit(n, builder);

	}

	@Override
	public void visit(Parameter n, PatternBuilder builder) {

		super.visit(n, builder);

	}

	@Override
	public void visit(PrimitiveType n, PatternBuilder builder) {

		super.visit(n, builder);

	}

	@Override
	public void visit(QualifiedNameExpr n, PatternBuilder builder) {

		super.visit(n, builder);

	}

	@Override
	public void visit(ReferenceType n, PatternBuilder builder) {

		super.visit(n, builder);

	}

	@Override
	public void visit(ReturnStmt n, PatternBuilder builder) {

		super.visit(n, builder);

	}

	@Override
	public void visit(SingleMemberAnnotationExpr n, PatternBuilder builder) {

		super.visit(n, builder);

	}

	@Override
	public void visit(StringLiteralExpr n, PatternBuilder builder) {

		super.visit(n, builder);

	}

	@Override
	public void visit(SuperExpr n, PatternBuilder builder) {

		super.visit(n, builder);

	}

	@Override
	public void visit(SwitchEntryStmt n, PatternBuilder builder) {

		super.visit(n, builder);

	}

	@Override
	public void visit(SwitchStmt n, PatternBuilder builder) {

		super.visit(n, builder);

	}

	@Override
	public void visit(SynchronizedStmt n, PatternBuilder builder) {

		super.visit(n, builder);

	}

	@Override
	public void visit(ThisExpr n, PatternBuilder builder) {

		super.visit(n, builder);

	}

	@Override
	public void visit(ThrowStmt n, PatternBuilder builder) {

		super.visit(n, builder);

	}

	@Override
	public void visit(TryStmt n, PatternBuilder builder) {

		super.visit(n, builder);

	}

	@Override
	public void visit(TypeDeclarationStmt n, PatternBuilder builder) {

		super.visit(n, builder);

	}

	@Override
	public void visit(TypeParameter n, PatternBuilder builder) {

		super.visit(n, builder);

	}

	@Override
	public void visit(UnaryExpr n, PatternBuilder builder) {

		super.visit(n, builder);

	}

	@Override
	public void visit(VariableDeclarationExpr n, PatternBuilder builder) {

		super.visit(n, builder);

	}

	@Override
	public void visit(VariableDeclaratorId n, PatternBuilder builder) {
		dontAdd(n);
		super.visit(n, builder);

	}

	@Override
	public void visit(VoidType n, PatternBuilder builder) {
		// XXX put it back
		// super.visit(n, builder);

	}

	@Override
	public void visit(WhileStmt n, PatternBuilder builder) {

		super.visit(n, builder);

	}

	@Override
	public void visit(WildcardType n, PatternBuilder builder) {

		super.visit(n, builder);

	}
}
