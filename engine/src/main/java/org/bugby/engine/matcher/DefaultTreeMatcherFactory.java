package org.bugby.engine.matcher;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;

import org.bugby.annotation.BadExample;
import org.bugby.annotation.GoodExample;
import org.bugby.annotation.GoodExampleTrigger;
import org.bugby.api.javac.InternalUtils;
import org.bugby.api.javac.SourceParser;
import org.bugby.api.javac.TreeUtils;
import org.bugby.api.wildcard.Correlation;
import org.bugby.api.wildcard.TreeMatcher;
import org.bugby.api.wildcard.TreeMatcherFactory;
import org.bugby.engine.WildcardDictionary;
import org.bugby.engine.matcher.declaration.ClassMatcher;
import org.bugby.engine.matcher.declaration.MethodMatcher;
import org.bugby.engine.matcher.expression.ArrayAccessMatcher;
import org.bugby.engine.matcher.expression.AssignmentMatcher;
import org.bugby.engine.matcher.expression.BinaryMatcher;
import org.bugby.engine.matcher.expression.CompoundAssignmentMatcher;
import org.bugby.engine.matcher.expression.ConditionalMatcher;
import org.bugby.engine.matcher.expression.IdentifierMatcher;
import org.bugby.engine.matcher.expression.InstanceofMatcher;
import org.bugby.engine.matcher.expression.LiteralMatcher;
import org.bugby.engine.matcher.expression.MemberSelectMatcher;
import org.bugby.engine.matcher.expression.MethodInvocationMatcher;
import org.bugby.engine.matcher.expression.NewArrayMatcher;
import org.bugby.engine.matcher.expression.NewClassMatcher;
import org.bugby.engine.matcher.expression.ParenthesizedMatcher;
import org.bugby.engine.matcher.expression.TypeCastMatcher;
import org.bugby.engine.matcher.expression.UnaryMatcher;
import org.bugby.engine.matcher.statement.AssertMatcher;
import org.bugby.engine.matcher.statement.BlockMatcher;
import org.bugby.engine.matcher.statement.BreakMatcher;
import org.bugby.engine.matcher.statement.CaseMatcher;
import org.bugby.engine.matcher.statement.CatchMatcher;
import org.bugby.engine.matcher.statement.ContinueMatcher;
import org.bugby.engine.matcher.statement.DoWhileLoopMatcher;
import org.bugby.engine.matcher.statement.EmptyStatementMatcher;
import org.bugby.engine.matcher.statement.EnhancedForLoopMatcher;
import org.bugby.engine.matcher.statement.ExpressionStatementMatcher;
import org.bugby.engine.matcher.statement.ForLoopMatcher;
import org.bugby.engine.matcher.statement.IfMatcher;
import org.bugby.engine.matcher.statement.LabeledMatcher;
import org.bugby.engine.matcher.statement.ReturnMatcher;
import org.bugby.engine.matcher.statement.SwitchMatcher;
import org.bugby.engine.matcher.statement.ThrowMatcher;
import org.bugby.engine.matcher.statement.TryMatcher;
import org.bugby.engine.matcher.statement.VariableMatcher;
import org.bugby.engine.matcher.statement.WhileLoopMatcher;
import org.bugby.wildcard.SomeType;

import com.sun.source.tree.AnnotationTree;
import com.sun.source.tree.ArrayAccessTree;
import com.sun.source.tree.AssertTree;
import com.sun.source.tree.AssignmentTree;
import com.sun.source.tree.BinaryTree;
import com.sun.source.tree.BlockTree;
import com.sun.source.tree.BreakTree;
import com.sun.source.tree.CaseTree;
import com.sun.source.tree.CatchTree;
import com.sun.source.tree.ClassTree;
import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.CompoundAssignmentTree;
import com.sun.source.tree.ConditionalExpressionTree;
import com.sun.source.tree.ContinueTree;
import com.sun.source.tree.DoWhileLoopTree;
import com.sun.source.tree.EmptyStatementTree;
import com.sun.source.tree.EnhancedForLoopTree;
import com.sun.source.tree.ExpressionStatementTree;
import com.sun.source.tree.ExpressionTree;
import com.sun.source.tree.ForLoopTree;
import com.sun.source.tree.IdentifierTree;
import com.sun.source.tree.IfTree;
import com.sun.source.tree.InstanceOfTree;
import com.sun.source.tree.LabeledStatementTree;
import com.sun.source.tree.LiteralTree;
import com.sun.source.tree.MemberSelectTree;
import com.sun.source.tree.MethodInvocationTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.NewArrayTree;
import com.sun.source.tree.NewClassTree;
import com.sun.source.tree.ParenthesizedTree;
import com.sun.source.tree.ReturnTree;
import com.sun.source.tree.SwitchTree;
import com.sun.source.tree.ThrowTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.TryTree;
import com.sun.source.tree.TypeCastTree;
import com.sun.source.tree.UnaryTree;
import com.sun.source.tree.VariableTree;
import com.sun.source.tree.WhileLoopTree;

public class DefaultTreeMatcherFactory implements TreeMatcherFactory {
	private static Set<Class<? extends Annotation>> skipAnnotations = new HashSet<Class<? extends Annotation>>(Arrays.asList(GoodExample.class,
			GoodExampleTrigger.class, BadExample.class, SuppressWarnings.class, Override.class, Correlation.class));

	private static final Map<Class<? extends Tree>, Class<? extends TreeMatcher>> matcherClasses = new HashMap<Class<? extends Tree>, Class<? extends TreeMatcher>>();
	static {
		matcherClasses.put(ClassTree.class, ClassMatcher.class);
		matcherClasses.put(MethodTree.class, MethodMatcher.class);
		matcherClasses.put(ArrayAccessTree.class, ArrayAccessMatcher.class);
		matcherClasses.put(AssignmentTree.class, AssignmentMatcher.class);
		matcherClasses.put(BinaryTree.class, BinaryMatcher.class);
		matcherClasses.put(CompoundAssignmentTree.class, CompoundAssignmentMatcher.class);

		matcherClasses.put(ConditionalExpressionTree.class, ConditionalMatcher.class);
		matcherClasses.put(IdentifierTree.class, IdentifierMatcher.class);
		matcherClasses.put(InstanceOfTree.class, InstanceofMatcher.class);
		matcherClasses.put(LiteralTree.class, LiteralMatcher.class);
		matcherClasses.put(MemberSelectTree.class, MemberSelectMatcher.class);
		matcherClasses.put(MethodInvocationTree.class, MethodInvocationMatcher.class);
		matcherClasses.put(NewArrayTree.class, NewArrayMatcher.class);
		matcherClasses.put(NewClassTree.class, NewClassMatcher.class);
		matcherClasses.put(ParenthesizedTree.class, ParenthesizedMatcher.class);
		matcherClasses.put(TypeCastTree.class, TypeCastMatcher.class);
		matcherClasses.put(UnaryTree.class, UnaryMatcher.class);
		matcherClasses.put(AssertTree.class, AssertMatcher.class);
		matcherClasses.put(BlockTree.class, BlockMatcher.class);
		matcherClasses.put(BreakTree.class, BreakMatcher.class);
		matcherClasses.put(CaseTree.class, CaseMatcher.class);
		matcherClasses.put(CatchTree.class, CatchMatcher.class);
		matcherClasses.put(ContinueTree.class, ContinueMatcher.class);
		matcherClasses.put(DoWhileLoopTree.class, DoWhileLoopMatcher.class);
		matcherClasses.put(EmptyStatementTree.class, EmptyStatementMatcher.class);
		matcherClasses.put(EnhancedForLoopTree.class, EnhancedForLoopMatcher.class);
		matcherClasses.put(ExpressionStatementTree.class, ExpressionStatementMatcher.class);
		matcherClasses.put(ForLoopTree.class, ForLoopMatcher.class);
		matcherClasses.put(IfTree.class, IfMatcher.class);
		matcherClasses.put(LabeledStatementTree.class, LabeledMatcher.class);
		matcherClasses.put(ReturnTree.class, ReturnMatcher.class);
		matcherClasses.put(SwitchTree.class, SwitchMatcher.class);
		matcherClasses.put(ThrowTree.class, ThrowMatcher.class);
		matcherClasses.put(TryTree.class, TryMatcher.class);
		matcherClasses.put(VariableTree.class, VariableMatcher.class);
		matcherClasses.put(WhileLoopTree.class, WhileLoopMatcher.class);

		matcherClasses.put(CompilationUnitTree.class, CompilationUnitMatcher.class);
	}
	private final WildcardDictionary wildcardDictionary;

	public DefaultTreeMatcherFactory(WildcardDictionary wildcardDictionary) {
		this.wildcardDictionary = wildcardDictionary;
	}

	private String getMethodName(MethodInvocationTree method) {
		if (method.getMethodSelect() instanceof MemberSelectTree) {
			return ((MemberSelectTree) method.getMethodSelect()).getIdentifier().toString();
		}
		if (method.getMethodSelect() instanceof IdentifierTree) {
			return ((IdentifierTree) method.getMethodSelect()).getName().toString();
		}
		return null;
	}

	private String getMatcherName(Tree node) {
		if (node instanceof IdentifierTree) {
			return ((IdentifierTree) node).getName().toString();
		}
		if (node instanceof VariableTree) {
			return ((VariableTree) node).getName().toString();
		}
		if (node instanceof ClassTree) {
			Element element = TreeUtils.elementFromDeclaration((ClassTree) node);
			if (element.getAnnotation(BadExample.class) != null || //
					element.getAnnotation(GoodExample.class) != null || //
					element.getAnnotation(GoodExampleTrigger.class) != null) {
				return SomeType.class.getSimpleName();
			}
			return ((ClassTree) node).getSimpleName().toString();
		}
		if (node instanceof MethodTree) {
			return ((MethodTree) node).getName().toString();
		}
		if (node instanceof ExpressionStatementTree) {
			// method(); -> is taken as a matcher together (semi-colon included)
			ExpressionTree expr = ((ExpressionStatementTree) node).getExpression();
			if (expr instanceof MethodInvocationTree) {
				return getMethodName((MethodInvocationTree) expr);
			}
		}
		if (node instanceof MethodInvocationTree) {
			return getMethodName((MethodInvocationTree) node);
		}
		return null;
	}

	public TreeMatcher buildFromFile(ClassLoader builtProjectClassLoader, File file) {
		CompilationUnitTree cu = SourceParser.parse(file, builtProjectClassLoader, "UTF-8").getCompilationUnitTree();

		return build(cu);
	}

	@Override
	public TreeMatcher build(Tree patternNode) {

		if (skip(patternNode)) {
			return null;
		}
		Class<? extends TreeMatcher> matcherClass = null;
		String name = getMatcherName(patternNode);
		if (name != null) {
			// remove the ending digits
			String baseName = name.replaceAll("\\d+$", "");
			matcherClass = wildcardDictionary.findMatcherClass(baseName);
		}

		if (matcherClass == null) {
			matcherClass = getDefaultMatcherClass(patternNode);
		}

		if (matcherClass == null) {
			return null;
		}

		try {
			// TODO find here compatible constructor
			Constructor<?> constructor = matcherClass.getDeclaredConstructors()[0];
			return (TreeMatcher) constructor.newInstance(patternNode, this);
		} catch (Exception e) {
			throw new RuntimeException("Cannont create matcher of type:" + matcherClass.getName() + " with node of type:"
					+ patternNode.getClass().getName() + ":" + e, e);
		}

	}

	private Class<?> getTreeInteface(Class<?> clazz) {
		Type[] interfaces = clazz.getGenericInterfaces();
		for (Type iface : interfaces) {
			if (iface instanceof Class<?>) {
				Class<?> type = (Class<?>) iface;
				if (Tree.class.isAssignableFrom(type)) {
					return type;
				}
			}
		}
		return null;
	}

	private Class<? extends TreeMatcher> getDefaultMatcherClass(Tree node) {
		Class<?> treeInteface = getTreeInteface(node.getClass());

		return matcherClasses.get(treeInteface);

	}

	protected boolean skip(AnnotationTree ann) {
		TypeMirror type = InternalUtils.typeOf(ann.getAnnotationType());
		// return skipAnnotations.contains(ann.getName().toString()) ||
		// wildcardDictionary.isAnnotation(ann.getName().toString());
		return false;
	}

	private boolean skip(Tree node) {
		if (node == null) {
			return true;
		}
		if (node instanceof AnnotationTree) {
			return skip((AnnotationTree) node);
		}
		// if (NodeUtils.hasAnnotation(node, IgnoreFromMatching.class)) {
		// return true;
		// }

		return false;
	}
}
