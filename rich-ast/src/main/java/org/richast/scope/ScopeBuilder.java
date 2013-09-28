/**
 *  Copyright 2011 Alexandru Craciun, Eyal Kaspi
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.richast.scope;

import japa.parser.ast.CompilationUnit;
import japa.parser.ast.ImportDeclaration;
import japa.parser.ast.Node;
import japa.parser.ast.PackageDeclaration;
import japa.parser.ast.body.BodyDeclaration;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.ConstructorDeclaration;
import japa.parser.ast.body.EnumDeclaration;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.InitializerDeclaration;
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
import japa.parser.ast.expr.Expression;
import japa.parser.ast.expr.FieldAccessExpr;
import japa.parser.ast.expr.InstanceOfExpr;
import japa.parser.ast.expr.IntegerLiteralExpr;
import japa.parser.ast.expr.IntegerLiteralMinValueExpr;
import japa.parser.ast.expr.LongLiteralExpr;
import japa.parser.ast.expr.LongLiteralMinValueExpr;
import japa.parser.ast.expr.MethodCallExpr;
import japa.parser.ast.expr.NameExpr;
import japa.parser.ast.expr.NullLiteralExpr;
import japa.parser.ast.expr.ObjectCreationExpr;
import japa.parser.ast.expr.QualifiedNameExpr;
import japa.parser.ast.expr.StringLiteralExpr;
import japa.parser.ast.expr.SuperExpr;
import japa.parser.ast.expr.ThisExpr;
import japa.parser.ast.expr.UnaryExpr;
import japa.parser.ast.expr.VariableDeclarationExpr;
import japa.parser.ast.stmt.CatchClause;
import japa.parser.ast.stmt.ForStmt;
import japa.parser.ast.stmt.ForeachStmt;
import japa.parser.ast.stmt.SwitchEntryStmt;
import japa.parser.ast.stmt.SwitchStmt;
import japa.parser.ast.type.ClassOrInterfaceType;
import japa.parser.ast.type.PrimitiveType;
import japa.parser.ast.type.ReferenceType;
import japa.parser.ast.type.Type;
import japa.parser.ast.type.VoidType;
import japa.parser.ast.type.WildcardType;

import java.lang.reflect.Constructor;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.richast.GenerationContext;
import org.richast.JavascriptFileGenerationException;
import org.richast.node.ASTNodeData;
import org.richast.node.SourcePosition;
import org.richast.type.ClassLoaderWrapper;
import org.richast.type.ClassWrapper;
import org.richast.type.FieldWrapper;
import org.richast.type.MethodWrapper;
import org.richast.type.ParameterizedTypeImpl;
import org.richast.type.ParameterizedTypeWrapper;
import org.richast.type.PrimitiveTypes;
import org.richast.type.TypeWrapper;
import org.richast.type.TypeWrappers;
import org.richast.type.WildcardTypeImpl;
import org.richast.type.WildcardTypeWrapper;
import org.richast.utils.ClassUtils;
import org.richast.utils.NodeUtils;
import org.richast.utils.Operators;
import org.richast.utils.Option;
import org.richast.utils.PreConditions;
import org.richast.variable.LocalVariable;
import org.richast.variable.ParameterVariable;
import org.richast.visitor.ForEachNodeVisitor;

/**
 * This class resolves the variables, methods and types and writes the corresponding information in the AST nodes.
 * 
 * @author acraciun,ekaspi
 */
@SuppressWarnings("PMD.ExcessivePublicCount")
public class ScopeBuilder extends ForEachNodeVisitor<Scope> {
	private final ClassLoaderWrapper classLoader;
	private final GenerationContext context;

	private final Map<Class<?>, AnonymousClassesHelper> anonymousClassHelpers = new HashMap<Class<?>, AnonymousClassesHelper>();

	public ScopeBuilder(ClassLoaderWrapper classLoader, GenerationContext context) {
		super();
		this.classLoader = classLoader;
		this.context = context;
	}

	@Override
	protected void before(Node node, Scope arg) {
		ASTNodeData data = (ASTNodeData) node.getData();
		if (data.getScope() == null) {
			data.setScope(arg);
		}
		/*
		 * if (data.getExpressionType() == null && node instanceof Expression) { throw new
		 * IllegalStateException("The node:" + node + " is an expression but no type was set"); }
		 */
	}

	@Override
	public void visit(final PackageDeclaration n, Scope scope) {
		if (scope instanceof CompilationUnitScope) {
			((CompilationUnitScope) scope).setPackageName(n.getName().toString());
		}
		super.visit(n, scope);
	}

	private void processAsteriskImportDeclarations(CompilationUnitScope scope, List<ImportDeclaration> imports) {
		for (ImportDeclaration importDecl : imports) {
			NameExpr name = importDecl.getName();
			if (importDecl.isAsterisk()) {
				if (importDecl.isStatic()) {
					for (ClassWrapper clazz : identifyQualifiedNameExprClass(name)) {
						scope.addFields(clazz.getDeclaredNonPrivateStaticFields());
						scope.addMethods(clazz.getDeclaredNonPrivateStaticMethods());
						scope.addTypes(clazz.getDeclaredNonPrivateStaticClasses());
					}
				} else {
					scope.addTypeImportOnDemand(name);
				}
			}
		}
	}

	private void processNonAsteriskImportDeclarations(CompilationUnitScope scope, List<ImportDeclaration> imports) {
		for (ImportDeclaration importDecl : imports) {
			NameExpr name = importDecl.getName();
			if (!importDecl.isAsterisk()) {
				if (importDecl.isStatic() && name instanceof QualifiedNameExpr) {
					QualifiedNameExpr expr = (QualifiedNameExpr) name;
					for (ClassWrapper clazz : identifyQualifiedNameExprClass(expr.getQualifier())) {
						String fieldOrTypeOrMethodName = name.getName();
						scope.addFields(clazz.findField(fieldOrTypeOrMethodName));
						scope.addMethods(fieldOrTypeOrMethodName, clazz.findMethods(fieldOrTypeOrMethodName));
						scope.addTypes(clazz.getDeclaredClass(fieldOrTypeOrMethodName));
					}
				}
				scope.addTypes(identifyQualifiedNameExprClass(name));
			}
		}
	}

	@Override
	public void visit(final CompilationUnit n, Scope inputScope) {
		PreConditions.checkStateNode(n, inputScope instanceof CompilationUnitScope,
				"A compilationUnitScope was expected. Got %s", inputScope);

		CompilationUnitScope scope = (CompilationUnitScope) inputScope;
		// asterisk declaration have lower priority => process them first (JLS Â§7.5.2)
		if (n.getImports() != null) {
			processAsteriskImportDeclarations(scope, n.getImports());
			processNonAsteriskImportDeclarations(scope, n.getImports());
		}

		super.visit(n, scope);
	}

	private ClassScope addClassToScope(AbstractScope parentScope, ClassWrapper clazz) {
		parentScope.addType(clazz);

		ClassScope scope = new ClassScope(clazz, parentScope, context);

		return scope;
	}

	@Override
	public void visit(final ClassOrInterfaceDeclaration n, Scope scope) {
		// Checks.checkClassDeclaration(n, context);
		AbstractScope parentScope = (AbstractScope) scope;
		TypeWithScope type = scope.resolveType(n.getName());
		PreConditions.checkStateNodeNotNull(n, type, "%s class cannot be resolved in the scope", n.getName());
		Scope classScope = addClassToScope(parentScope, (ClassWrapper) type.getType());
		ASTNodeData.resolvedType(n, type.getType());

		checkForDeadCode(n, context);

		super.visit(n, classScope);
		// Checks.postCheckClassDeclaration(n, context);
	}

	private Node closestTypeDeclaration(Node n) {
		for (Node p = ASTNodeData.parent(n); p != null; p = ASTNodeData.parent(p)) {
			if (p instanceof ClassOrInterfaceDeclaration) {
				return p;
			}
			if (p instanceof ObjectCreationExpr && ((ObjectCreationExpr) p).getAnonymousClassBody() != null) {
				return p;
			}
		}
		return null;
	}

	private void checkForDeadCode(ClassOrInterfaceDeclaration n, GenerationContext context) {
		List<ObjectCreationExpr> creationNodes = NodeUtils.findDescendantsOfType(n, ObjectCreationExpr.class);
		int count = 0;

		for (ObjectCreationExpr e : creationNodes) {
			// should make sure it's a anonymous inner type defined in directly in this class (and not in one of other
			// named or anonymous inner class)
			if (e.getAnonymousClassBody() != null && closestTypeDeclaration(e) == n) {
				++count;
			}
		}
		if (count > 0) {
			Class<?> clazz = ((ClassWrapper) ASTNodeData.resolvedType(n)).getClazz();
			AnonymousClassesHelper helper = new AnonymousClassesHelper(clazz);
			anonymousClassHelpers.put(clazz, helper);
			if (count != helper.getAnonymousClassesCount()) {
				throw new JavascriptFileGenerationException(context.getInputFile(), new SourcePosition(n),
						"The code for some anonymous inner classes was not generated. "
								+ "Please check if you don't have any dead code in your class and remove it"
								+ " (for example an 'if' statement on a final boolean variable that yields false). ");
			}
		}
	}

	@Override
	public void visit(final MethodDeclaration n, Scope currentScope) {
		// Checks.checkMethodDeclaration(n, context);

		// the scope is where the method is defined, i.e. the classScope
		((ASTNodeData) n.getData()).setScope(currentScope);
		ClassWrapper ownerClass = currentScope.closest(ClassScope.class).getClazz();
		List<MethodWrapper> methods = ownerClass.findMethods(n.getName());
		PreConditions.checkStateNode(n, !methods.isEmpty(), "Method [%s] not  found in the class [%s] line %d",
				n.getName(), ownerClass.getName(), n.getBeginLine());

		// this is safe as only one method is allowed with a given name
		MethodWrapper method = methods.get(0);
		ASTNodeData.resolvedMethod(n, method);

		BasicScope scope = handleMethodDeclaration(n, n.getParameters(), method.getParameterTypes(),
				method.getTypeParameters(), currentScope);
		super.visit(n, scope);
	}

	private BasicScope handleMethodDeclaration(Node node, final List<Parameter> parameters,
			java.lang.reflect.Type[] resolvedParameterTypes,
			TypeVariable<? extends GenericDeclaration>[] resolvedTypeParameters, Scope currentScope) {
		return handleMethodDeclaration(node, parameters, TypeWrappers.wrap(resolvedParameterTypes),
				TypeWrappers.wrap(resolvedTypeParameters), currentScope);
	}

	private BasicScope handleMethodDeclaration(Node node, final List<Parameter> parameters,
			TypeWrapper[] resolvedParameterTypes, TypeWrapper[] resolvedTypeParameters, Scope currentScope) {

		BasicScope scope = new BasicScope(currentScope, context);
		if (resolvedTypeParameters != null) {
			for (TypeWrapper tv : resolvedTypeParameters) {
				scope.addType(tv);
			}
		}

		if (parameters != null) {
			PreConditions.checkStateNode(node, parameters.size() == resolvedParameterTypes.length,
					"The number of parameters (%d) should be the same as the number of types (%d) ", parameters.size(),
					resolvedParameterTypes.length);
			for (int i = 0; i < parameters.size(); ++i) {
				TypeWrapper clazz = resolvedParameterTypes[i];
				scope.addVariable(new ParameterVariable(clazz, parameters.get(i).getId().getName()));
			}
		}

		return scope;
	}

	private String fullName(ClassOrInterfaceType type) {
		StringBuilder s = new StringBuilder(type.getName());
		for (ClassOrInterfaceType p = type.getScope(); p != null; p = p.getScope()) {
			s.insert(0, p.getName() + ".");
		}
		return s.toString();
	}

	private Type dereferenceType(Type type) {
		if (type instanceof ReferenceType) {
			int arrayCount = 0;

			ReferenceType refType = (ReferenceType) type;
			arrayCount = refType.getArrayCount();
			if (arrayCount > 0 && !isMainArgs(type)) {
				throw new JavascriptFileGenerationException(context.getInputFile(), new SourcePosition(type),
						"You cannot use Java arrays because they are incompatible with Javascript arrays. "
								+ "Use org.stjs.javascript.Array<T> instead. "
								+ "You can use also the method org.stjs.javascript.Global.$castArray to convert an "
								+ "existent Java array to the corresponding Array type."
								+ "The only exception is void main(String[] args).");
			}
			return refType.getType(); // type is a primitive or class
		}
		return type;
	}

	private int getArrayCount(Type type) {
		if (type instanceof ReferenceType) {

			ReferenceType refType = (ReferenceType) type;
			return refType.getArrayCount();
		}
		return 0;
	}

	private TypeWrapper resolveRegularType(Scope scope, ClassOrInterfaceType classType) {
		// type can be the name of a class/interface
		// or can be a part of the package name for a fully qualified name
		// or can be an inner class name
		TypeWithScope rawType = scope.resolveType(fullName(classType));
		if (rawType == null) {
			return null;
		}
		TypeWrapper resolvedType;

		// PreConditions.checkStateNode(n,rawType != null, "Cannot resolve type [%s]", classType.getName());
		if (classType.getTypeArgs() == null) {
			resolvedType = rawType.getType();
		} else {
			List<java.lang.reflect.Type> args = new ArrayList<java.lang.reflect.Type>();
			for (Type arg : classType.getTypeArgs()) {
				args.add(resolveType(scope, arg).getType());
			}
			resolvedType = new ParameterizedTypeWrapper(new ParameterizedTypeImpl(rawType.getType().getType(),
					args.toArray(new java.lang.reflect.Type[args.size()]), null));

		}
		return resolvedType;
	}

	private TypeWrapper resolveWildcardType(Scope scope, WildcardType wildcardType) {
		TypeWrapper resolvedType;
		java.lang.reflect.Type[] upperBound = wildcardType.getExtends() == null ? new java.lang.reflect.Type[0]
				: new java.lang.reflect.Type[]{ resolveType(scope, wildcardType.getExtends()).getType() };
		java.lang.reflect.Type[] lowerBound = wildcardType.getSuper() == null ? new java.lang.reflect.Type[0]
				: new java.lang.reflect.Type[]{ resolveType(scope, wildcardType.getSuper()).getType() };
		resolvedType = new WildcardTypeWrapper(new WildcardTypeImpl(lowerBound, upperBound));
		return resolvedType;
	}

	private TypeWrapper resolveType(Scope scope, Type aType) {
		Type type = aType;
		try {
			type = dereferenceType(type);
			TypeWrapper resolvedType;
			if (type instanceof PrimitiveType) {
				PrimitiveType primitiveType = (PrimitiveType) type;
				resolvedType = PrimitiveTypes.primitiveReflectionType(primitiveType);
			} else if (type instanceof VoidType) {
				resolvedType = new ClassWrapper(void.class);
			} else if (type instanceof ClassOrInterfaceType) {
				ClassOrInterfaceType classType = (ClassOrInterfaceType) type;
				resolvedType = resolveRegularType(scope, classType);
			} else if (type instanceof WildcardType) {
				WildcardType wildcardType = (WildcardType) type;
				resolvedType = resolveWildcardType(scope, wildcardType);
			} else {
				throw new JavascriptFileGenerationException(context.getInputFile(), new SourcePosition(type),
						"Unexpected type:" + type);
			}
			int arrayCount = getArrayCount(aType);
			return ClassUtils.arrayOf(resolvedType, arrayCount);
		}
		catch (IllegalArgumentException ex) {
			throw new JavascriptFileGenerationException(context.getInputFile(), new SourcePosition(type),
					ex.getMessage(), ex);
		}
	}

	/**
	 * check if the given type is the argument of the public static void main(String[] args) method
	 * 
	 * @param type
	 * @return
	 */
	private boolean isMainArgs(Type type) {
		Node n = ASTNodeData.parent(type);
		if (!(n instanceof Parameter)) {
			return false;
		}
		n = ASTNodeData.parent(n);
		if (!(n instanceof MethodDeclaration)) {
			return false;
		}
		return NodeUtils.isMainMethod((MethodDeclaration) n);
	}

	@Override
	public void visit(VariableDeclarator n, Scope arg) {
		VariableWithScope var = arg.resolveVariable(n.getId().getName());
		if (var != null) {
			ASTNodeData.resolvedVariable(n, var.getVariable());
			ASTNodeData.resolvedVariableScope(n, var.getScope());
			ASTNodeData.resolvedType(n, var.getVariable().getType());
			// Checks.checkScope(n, context);
		}
		super.visit(n, arg);
	}

	@Override
	public void visit(VariableDeclaratorId n, Scope scope) {
		// Checks.checkVariableDeclaratorId(n, context);
		super.visit(n, scope);
	}

	@Override
	public void visit(VariableDeclarationExpr n, Scope scope) {
		PreConditions.checkStateNode(n, scope instanceof BasicScope,
				"The variable [%s] is not defined inside a BasicScope", n);

		// Checks.checkVariableDeclarationExpr(n, context);

		BasicScope basicScope = (BasicScope) scope;
		if (n.getVars() != null) {
			TypeWrapper clazz = resolveType(basicScope, n.getType());
			ASTNodeData.resolvedType(n, clazz);
			for (VariableDeclarator var : n.getVars()) {
				basicScope.addVariable(new LocalVariable(clazz, var.getId().getName()));
			}
		}

		super.visit(n, scope);
	}

	@Override
	public void visit(CatchClause n, Scope currentScope) {
		// TODO : this is broken in Java7 because a catch block might declare more than one exception
		// would need a new javap
		BasicScope scope = new BasicScope(currentScope, context);
		scope.addVariable(new ParameterVariable(resolveType(scope, n.getExcept().getType()), n.getExcept().getId()
				.getName()));
		super.visit(n, scope);
	}

	@Override
	public void visit(ConstructorDeclaration n, Scope currentScope) {
		// the scope is where the constructor is defined, i.e. the classScope
		((ASTNodeData) n.getData()).setScope(currentScope);
		Class<?> ownerClass = currentScope.closest(ClassScope.class).getClazz().getClazz();
		Constructor<?> c = ClassUtils.findConstructor(ownerClass);
		BasicScope scope;
		if (c == null) {
			// synthetic constructor
			scope = handleMethodDeclaration(n, n.getParameters(), new java.lang.reflect.Type[0],
					new TypeVariable<?>[0], currentScope);
		} else {
			java.lang.reflect.Type[] parameterTypes = c.getGenericParameterTypes();
			// enums receive label and ordinal as first arguments
			// for non-static inner classes the constructor contains as first parameter the type of the outer type
			int skipParams = parameterTypes.length - (n.getParameters() == null ? 0 : n.getParameters().size());

			if (skipParams > 0) {
				parameterTypes = Arrays.copyOfRange(parameterTypes, skipParams, parameterTypes.length);
			}
			scope = handleMethodDeclaration(n, n.getParameters(), parameterTypes, c.getTypeParameters(), currentScope);
		}
		super.visit(n, new BasicScope(scope, context));
	}

	@Override
	public void visit(ForeachStmt n, Scope currentScope) {
		super.visit(n, new BasicScope(currentScope, context));
	}

	@Override
	public void visit(ForStmt n, Scope currentScope) {
		super.visit(n, new BasicScope(currentScope, context));
	}

	@Override
	public void visit(InitializerDeclaration n, Scope currentScope) {
		super.visit(n, new BasicScope(currentScope, context));
	}

	@Override
	public void visit(final EnumDeclaration n, final Scope currentScope) {
		// Checks.checkEnumDeclaration(n, context);
		AbstractScope parentScope = (AbstractScope) currentScope;
		TypeWithScope type = currentScope.resolveType(n.getName());
		PreConditions.checkStateNodeNotNull(n, type, "%s class cannot be resolved in the scope", n.getName());
		Scope enumClassScope = addClassToScope(parentScope, (ClassWrapper) type.getType());
		ASTNodeData.resolvedType(n, type.getType());

		super.visit(n, enumClassScope);
		// Checks.postCheckEnumDeclaration(n, context);
	}

	private void visitTypeArgs(ObjectCreationExpr n, Scope scope) {
		if (n.getTypeArgs() != null) {
			for (Type t : n.getTypeArgs()) {
				t.accept(this, scope);
			}
		}
	}

	private void visitArgs(ObjectCreationExpr n, Scope scope) {
		if (n.getArgs() != null) {
			for (Expression e : n.getArgs()) {
				e.accept(this, scope);
			}
		}
	}

	@Override
	public void visit(ObjectCreationExpr n, Scope scope) {
		if (n.getScope() != null) {
			n.getScope().accept(this, scope);
		}
		visitTypeArgs(n, scope);
		n.getType().accept(this, scope);
		visitArgs(n, scope);

		// Checks.checkObjectCreationExpr(n, context);

		if (n.getAnonymousClassBody() == null) {
			ASTNodeData.resolvedType(n, resolveType(scope, n.getType()));
			ASTNodeData.scope(n, scope);
		} else {
			ClassScope classScope = scope.closest(ClassScope.class);

			ClassWrapper anonymousClass = searchAnonymousClass(classScope.getClazz().getClazz(), n, scope);
			PreConditions.checkStateNodeNotNull(n, anonymousClass,
					"Could not find anoynmous class for node at line %d", n.getBeginLine());

			ClassScope anonymousClassScope = addClassToScope((AbstractScope) scope, anonymousClass);

			ASTNodeData.resolvedType(n, anonymousClass);
			ASTNodeData.scope(n, anonymousClassScope);

			for (BodyDeclaration member : n.getAnonymousClassBody()) {
				member.accept(this, anonymousClassScope);
			}
		}
	}

	private ClassWrapper searchAnonymousClass(Class<?> clazz, ObjectCreationExpr n, Scope scope) {
		AnonymousClassesHelper helper = anonymousClassHelpers.get(clazz);
		if (helper == null) {
			helper = new AnonymousClassesHelper(clazz);
			anonymousClassHelpers.put(clazz, helper);
		}
		String className = helper.findAnonymousClass(n, scope, this);
		if (className == null) {
			return null;
		}
		return classLoader.loadClass(className).getOrThrow("Cannot load class:" + className);
	}

	private Option<ClassWrapper> identifyQualifiedNameExprClass(NameExpr expr) {
		try {
			return classLoader.loadClassOrInnerClass(expr.toString());
		}
		catch (IllegalArgumentException ex) {
			throw new JavascriptFileGenerationException(context.getInputFile(), new SourcePosition(expr),
					ex.getMessage(), ex);
		}
	}

	/**
	 * type references -> set the resolved type
	 */
	@Override
	public void visit(ClassOrInterfaceType n, Scope arg) {
		super.visit(n, arg);
		ASTNodeData.resolvedType(n, resolveType(arg, n));
	}

	@Override
	public void visit(PrimitiveType n, Scope arg) {
		super.visit(n, arg);
		ASTNodeData.resolvedType(n, resolveType(arg, n));
	}

	@Override
	public void visit(ReferenceType n, Scope arg) {
		super.visit(n, arg);
		ASTNodeData.resolvedType(n, resolveType(arg, n));
	}

	@Override
	public void visit(VoidType n, Scope arg) {
		super.visit(n, arg);
		ASTNodeData.resolvedType(n, resolveType(arg, n));
	}

	@Override
	public void visit(WildcardType n, Scope arg) {
		super.visit(n, arg);
		ASTNodeData.resolvedType(n, resolveType(arg, n));
	}

	/*** expressions -> set the type ***/

	@Override
	public void visit(ArrayAccessExpr n, Scope arg) {
		super.visit(n, arg);
		TypeWrapper arrayType = ASTNodeData.resolvedType(n.getName());
		if (arrayType.getType() instanceof GenericArrayType) {
			ASTNodeData.resolvedType(n,
					TypeWrappers.wrap(((GenericArrayType) arrayType.getType()).getGenericComponentType()));
		} else if (arrayType.getType() instanceof Class<?>) {
			ASTNodeData.resolvedType(n, TypeWrappers.wrap(((Class<?>) arrayType.getType()).getComponentType()));
		} else {
			throw new IllegalStateException("Unknown reflect type for node:" + n);
		}
	}

	@Override
	public void visit(ArrayCreationExpr n, Scope arg) {
		super.visit(n, arg);
		ASTNodeData.resolvedType(n, ClassUtils.arrayOf(resolveType(arg, n.getType()), n.getArrayCount()));
	}

	@Override
	public void visit(ArrayInitializerExpr n, Scope arg) {
		super.visit(n, arg);
		if (n.getValues() != null && n.getValues().size() > 0) {
			// not exactly sure what to put here - but it doesn't matter much
			ASTNodeData.resolvedType(n, ASTNodeData.resolvedType(n.getValues().get(0)));
		}
	}

	@Override
	public void visit(AssignExpr n, Scope arg) {
		super.visit(n, arg);
		ASTNodeData.resolvedType(n, ASTNodeData.resolvedType(n.getTarget()));
	}

	@Override
	public void visit(BinaryExpr n, Scope arg) {
		super.visit(n, arg);
		if (Operators.isLogical(n.getOperator())) {
			ASTNodeData.resolvedType(n, TypeWrappers.wrap(boolean.class));
		} else {
			ASTNodeData
					.resolvedType(n, TypeWrappers.wrap(PrimitiveTypes.expressionResultType(
							ASTNodeData.resolvedType(n.getLeft()).getType(), ASTNodeData.resolvedType(n.getRight())
									.getType())));
		}

	}

	@Override
	public void visit(CastExpr n, Scope arg) {
		super.visit(n, arg);
		TypeWrapper type = ASTNodeData.resolvedType(n.getType());
		ASTNodeData.resolvedType(n, type);
	}

	@Override
	public void visit(ClassExpr n, Scope arg) {
		super.visit(n, arg);
		TypeWrapper type = ASTNodeData.resolvedType(n.getType());
		ASTNodeData.resolvedType(n, TypeWrappers.wrap(new ParameterizedTypeImpl(Class.class,
				new java.lang.reflect.Type[]{ type.getType() }, null)));
	}

	@Override
	public void visit(ConditionalExpr n, Scope arg) {
		super.visit(n, arg);
		ASTNodeData.resolvedType(n, ASTNodeData.resolvedType(n.getThenExpr() instanceof NullLiteralExpr ? n
				.getElseExpr() : n.getThenExpr()));
	}

	@Override
	public void visit(EnclosedExpr n, Scope arg) {
		super.visit(n, arg);
		ASTNodeData.resolvedType(n, ASTNodeData.resolvedType(n.getInner()));
	}

	@Override
	public void visit(InstanceOfExpr n, Scope arg) {
		super.visit(n, arg);
		ASTNodeData.resolvedType(n, TypeWrappers.wrap(boolean.class));

	}

	@Override
	public void visit(StringLiteralExpr n, Scope arg) {
		super.visit(n, arg);
		ASTNodeData.resolvedType(n, TypeWrappers.wrap(String.class));
	}

	@Override
	public void visit(IntegerLiteralExpr n, Scope arg) {
		super.visit(n, arg);
		ASTNodeData.resolvedType(n, TypeWrappers.wrap(int.class));
	}

	@Override
	public void visit(LongLiteralExpr n, Scope arg) {
		super.visit(n, arg);
		ASTNodeData.resolvedType(n, TypeWrappers.wrap(long.class));
	}

	@Override
	public void visit(IntegerLiteralMinValueExpr n, Scope arg) {
		super.visit(n, arg);
		ASTNodeData.resolvedType(n, TypeWrappers.wrap(int.class));

	}

	@Override
	public void visit(LongLiteralMinValueExpr n, Scope arg) {
		super.visit(n, arg);
		ASTNodeData.resolvedType(n, TypeWrappers.wrap(long.class));

	}

	@Override
	public void visit(CharLiteralExpr n, Scope arg) {
		super.visit(n, arg);
		ASTNodeData.resolvedType(n, TypeWrappers.wrap(char.class));
	}

	@Override
	public void visit(DoubleLiteralExpr n, Scope arg) {
		super.visit(n, arg);
		ASTNodeData.resolvedType(n, TypeWrappers
				.wrap(n.getValue().endsWith("f") || n.getValue().endsWith("F") ? float.class : double.class));
	}

	@Override
	public void visit(BooleanLiteralExpr n, Scope arg) {
		super.visit(n, arg);
		ASTNodeData.resolvedType(n, TypeWrappers.wrap(boolean.class));
	}

	@Override
	public void visit(NullLiteralExpr n, Scope arg) {
		super.visit(n, arg);
		ASTNodeData.resolvedType(n, null);
	}

	private String location(Node n) {
		return context.getInputFile() + ":" + n.getBeginLine();
	}

	@Override
	public void visit(MethodCallExpr n, Scope arg) {
		super.visit(n, arg);
		MethodWrapper method;
		TypeWrapper[] argumentTypes;
		if (n.getArgs() == null) {
			argumentTypes = new TypeWrapper[0];
		} else {
			argumentTypes = new TypeWrapper[n.getArgs().size()];
			for (int i = 0; i < n.getArgs().size(); ++i) {
				argumentTypes[i] = ASTNodeData.resolvedType(n.getArgs().get(i));
			}
		}
		if (n.getScope() == null) {
			MethodsWithScope ms = arg.resolveMethod(n.getName(), argumentTypes);
			PreConditions.checkStateNodeNotNull(n, ms, "%s The method %s could not be resolved", location(n),
					n.getName());
			method = ms.getMethod();
		} else {
			TypeWrapper scopeType = ASTNodeData.resolvedType(n.getScope());
			PreConditions.checkStateNodeNotNull(n, scopeType, "%s The method %s's scope could not be resolved",
					location(n), n.getName());
			method = scopeType.findMethod(n.getName(), argumentTypes).getOrThrow(
					location(n) + "-> type:" + scopeType.getName() + " m:" + n.getName());
		}

		ASTNodeData.resolvedType(n, method.getReturnType());
		ASTNodeData.resolvedMethod(n, method);
		// Checks.checkScope(n, context);
	}

	@Override
	public void visit(FieldDeclaration n, Scope arg) {
		// Checks.checkFieldDeclaration(n, context);
		super.visit(n, arg);
	}

	@Override
	public void visit(FieldAccessExpr n, Scope arg) {
		super.visit(n, arg);
		TypeWrapper scopeType = ASTNodeData.resolvedType(n.getScope());
		if (scopeType == null) {
			// if the scope's type is null, it's because is part of a qualified class name x.y.z -> so try to
			// resolve it
			TypeWithScope exprType = arg.resolveType(n.toString());
			if (exprType == null) {
				ASTNodeData.resolvedType(n, null);
			} else {
				ASTNodeData.resolvedType(n, exprType.getType());
			}
		} else {
			// if not -> it can be either an inner type or a field in the type
			Option<FieldWrapper> field = scopeType.findField(n.getField());
			if (field.isDefined()) {
				ASTNodeData.resolvedType(n, field.getOrThrow().getType());
				ASTNodeData.resolvedVariable(n, field.getOrThrow());
			} else {
				TypeWithScope innerType = arg.resolveType(scopeType.getName() + "$" + n.getField());
				PreConditions.checkStateNodeNotNull(n, innerType,
						"%s no inner type nor field could be resolved for '%s'", location(n), n.getField());
				ASTNodeData.resolvedType(n, innerType.getType());
			}
		}

	}

	private boolean isClassNamePart(Node parent) {
		return parent instanceof QualifiedNameExpr || parent instanceof ImportDeclaration
				|| parent instanceof PackageDeclaration;
	}

	@Override
	public void visit(NameExpr n, Scope arg) {
		// Checks.checkNameExpr(n, context);
		super.visit(n, arg);
		Node parent = ASTNodeData.parent(n);
		if (isClassNamePart(parent)) {
			// don't bother as is only part of a package or import declaration
			ASTNodeData.resolvedType(n, null);
		} else if (parent instanceof SwitchEntryStmt) {
			// this is an enum label -> the type is the enum from the selector
			ASTNodeData.resolvedType(n,
					ASTNodeData.resolvedType(((SwitchStmt) ASTNodeData.parent(parent)).getSelector()));
		} else {

			// here n can be:
			// 1) start of a fully qualified class x.y.z
			// 2) the name of a class
			// 3) a field
			// 4) a variable or a parameter
			// 5) an enum constant
			VariableWithScope var = arg.resolveVariable(n.getName());
			if (var == null) {
				try {
					TypeWithScope type = arg.resolveType(n.getName());
					if (type != null) {
						ASTNodeData.resolvedType(n, type.getType());
					}
				}
				catch (IllegalArgumentException ex) {
					throw new JavascriptFileGenerationException(context.getInputFile(), new SourcePosition(n),
							ex.getMessage(), ex);
				}
			} else {
				ASTNodeData.resolvedVariable(n, var.getVariable());
				ASTNodeData.resolvedVariableScope(n, var.getScope());
				ASTNodeData.resolvedType(n, var.getVariable().getType());
				// Checks.checkScope(n, context);

			}
		}

	}

	@Override
	public void visit(QualifiedNameExpr n, Scope arg) {
		// don't bother as is only part of a package or import declaration
		ASTNodeData.resolvedType(n, null);
		super.visit(n, arg);
	}

	@Override
	public void visit(ThisExpr n, Scope arg) {
		// Checks.checkThisExpr(n, context);

		super.visit(n, arg);
		if (n.getClassExpr() == null) {
			ClassScope classScope = arg.closest(ClassScope.class);
			ASTNodeData.resolvedType(n, classScope.getClazz());
		} else {
			ASTNodeData.resolvedType(n, ASTNodeData.resolvedType(n.getClassExpr()));
		}
	}

	@Override
	public void visit(SuperExpr n, Scope arg) {
		// Checks.checkSuperExpr(n, context);
		super.visit(n, arg);
		ClassWrapper thisClazz;
		if (n.getClassExpr() == null) {
			ClassScope classScope = arg.closest(ClassScope.class);
			thisClazz = classScope.getClazz();
		} else {
			thisClazz = (ClassWrapper) ASTNodeData.resolvedType(n.getClassExpr());
		}
		ASTNodeData.resolvedType(n, thisClazz.getSuperclass().getOrElse(TypeWrappers.wrap(Object.class)));
	}

	@Override
	public void visit(UnaryExpr n, Scope arg) {
		super.visit(n, arg);
		ASTNodeData.resolvedType(n, ASTNodeData.resolvedType(n.getExpr()));
	}

	@Override
	public void visit(Parameter n, Scope arg) {
		// TODO Auto-generated method stub
		super.visit(n, arg);
		VariableWithScope var = arg.resolveVariable(n.getId().getName());
		if (var != null) {
			ASTNodeData.resolvedVariable(n, var.getVariable());
			ASTNodeData.resolvedVariableScope(n, var.getScope());
			ASTNodeData.resolvedType(n, var.getVariable().getType());
		}
	}
}