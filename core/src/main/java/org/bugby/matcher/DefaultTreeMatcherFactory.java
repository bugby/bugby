package org.bugby.matcher;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

import org.bugby.api.Correlation;
import org.bugby.api.Pattern;
import org.bugby.api.TreeMatcher;
import org.bugby.api.TreeMatcherFactory;
import org.bugby.matcher.declaration.ClassMatcher;
import org.bugby.matcher.declaration.MethodMatcher;
import org.bugby.matcher.declaration.TypeWithoutSourceMatcher;
import org.bugby.matcher.expression.ArrayAccessMatcher;
import org.bugby.matcher.expression.AssignmentMatcher;
import org.bugby.matcher.expression.BinaryMatcher;
import org.bugby.matcher.expression.CompoundAssignmentMatcher;
import org.bugby.matcher.expression.ConditionalMatcher;
import org.bugby.matcher.expression.IdentifierMatcher;
import org.bugby.matcher.expression.InstanceofMatcher;
import org.bugby.matcher.expression.LiteralMatcher;
import org.bugby.matcher.expression.MemberSelectMatcher;
import org.bugby.matcher.expression.MethodInvocationMatcher;
import org.bugby.matcher.expression.NewArrayMatcher;
import org.bugby.matcher.expression.NewClassMatcher;
import org.bugby.matcher.expression.ParenthesizedMatcher;
import org.bugby.matcher.expression.PrimitiveTypeMatcher;
import org.bugby.matcher.expression.TypeCastMatcher;
import org.bugby.matcher.expression.UnaryMatcher;
import org.bugby.matcher.javac.ElementWrapperTree;
import org.bugby.matcher.javac.InternalUtils;
import org.bugby.matcher.javac.ParsedSource;
import org.bugby.matcher.javac.SourceParser;
import org.bugby.matcher.javac.TreeUtils;
import org.bugby.matcher.statement.AssertMatcher;
import org.bugby.matcher.statement.BlockMatcher;
import org.bugby.matcher.statement.BreakMatcher;
import org.bugby.matcher.statement.CaseMatcher;
import org.bugby.matcher.statement.CatchMatcher;
import org.bugby.matcher.statement.ContinueMatcher;
import org.bugby.matcher.statement.DoWhileLoopMatcher;
import org.bugby.matcher.statement.EmptyStatementMatcher;
import org.bugby.matcher.statement.EnhancedForLoopMatcher;
import org.bugby.matcher.statement.ExpressionStatementMatcher;
import org.bugby.matcher.statement.ForLoopMatcher;
import org.bugby.matcher.statement.IfMatcher;
import org.bugby.matcher.statement.LabeledMatcher;
import org.bugby.matcher.statement.ReturnMatcher;
import org.bugby.matcher.statement.SwitchMatcher;
import org.bugby.matcher.statement.SynchronizedMatcher;
import org.bugby.matcher.statement.ThrowMatcher;
import org.bugby.matcher.statement.TryMatcher;
import org.bugby.matcher.statement.VariableMatcher;
import org.bugby.matcher.statement.WhileLoopMatcher;

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
import com.sun.source.tree.PrimitiveTypeTree;
import com.sun.source.tree.ReturnTree;
import com.sun.source.tree.SwitchTree;
import com.sun.source.tree.SynchronizedTree;
import com.sun.source.tree.ThrowTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.TryTree;
import com.sun.source.tree.TypeCastTree;
import com.sun.source.tree.UnaryTree;
import com.sun.source.tree.VariableTree;
import com.sun.source.tree.WhileLoopTree;

public class DefaultTreeMatcherFactory implements TreeMatcherFactory {
	private static Set<Class<? extends Annotation>> skipAnnotations = new HashSet<Class<? extends Annotation>>(Arrays.asList(Pattern.class,
		SuppressWarnings.class, Override.class, Correlation.class));

	private static final Map<Class<? extends Tree>, Class<? extends TreeMatcher>> matcherClasses =
			new HashMap<Class<? extends Tree>, Class<? extends TreeMatcher>>();
	static {
		matcherClasses.put(ClassTree.class, ClassMatcher.class);
		matcherClasses.put(MethodTree.class, MethodMatcher.class);
		matcherClasses.put(ArrayAccessTree.class, ArrayAccessMatcher.class);
		matcherClasses.put(AssignmentTree.class, AssignmentMatcher.class);
		matcherClasses.put(BinaryTree.class, BinaryMatcher.class);
		matcherClasses.put(CompoundAssignmentTree.class, CompoundAssignmentMatcher.class);

		matcherClasses.put(ConditionalExpressionTree.class, ConditionalMatcher.class);
		matcherClasses.put(IdentifierTree.class, IdentifierMatcher.class);
		matcherClasses.put(PrimitiveTypeTree.class, PrimitiveTypeMatcher.class);
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
		matcherClasses.put(SynchronizedTree.class, SynchronizedMatcher.class);
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
	private final ClassLoader builtProjectClassLoader;
	private ParsedSource parsedSource;

	public DefaultTreeMatcherFactory(WildcardDictionary wildcardDictionary, ClassLoader builtProjectClassLoader) {
		this.wildcardDictionary = wildcardDictionary;
		this.builtProjectClassLoader = builtProjectClassLoader;
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
			if (element.getAnnotation(Pattern.class) != null) {
				// XXX: this should be done differently, probably in the annotation
				return "SomeType";
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

	private static String sourceFileName(Class<?> clz) {
		return clz.getName().replace(".class", "").replace('.', File.separatorChar) + ".java";
	}

	private static final String[] SOURCE_PATHS = {"src/main/java", "src/test/java"};

	private static File sourcePath(String fileName) {
		for (String p : SOURCE_PATHS) {
			File f = new File(p, fileName);
			if (f.exists()) {
				return f;
			}
		}
		throw new RuntimeException("Cannot find file:" + fileName);
	}

	@Override
	public TreeMatcher buildForType(String type) {
		// TODO here I need a mechanism to find the source of a given type
		if (type.startsWith("org.bugby")) {
			CompilationUnitMatcher cuMatcher =
					(CompilationUnitMatcher) buildFromFile(sourcePath(type.replace('.', File.separatorChar) + ".java"));
			return cuMatcher.getTypeMatchers().get(0);
		}
		TypeElement element = parsedSource.getElements().getTypeElement(type);
		return new TypeWithoutSourceMatcher(new ElementWrapperTree(element), element.asType());
	}

	public Tree loadTypeDefinition(String type) {
		if (type.startsWith("org.bugby")) {
			File file = sourcePath(type.replace('.', File.separatorChar) + ".java");
			ParsedSource typeParsedSource = SourceParser.parse(file, builtProjectClassLoader, "UTF-8");
			CompilationUnitTree cu = typeParsedSource.getCompilationUnitTree();
			//TODO should look into actual type names
			return cu.getTypeDecls().get(0);
		}
		TypeElement element = parsedSource.getElements().getTypeElement(type);
		return new ElementWrapperTree(element);
	}

	public TreeMatcher buildFromFile(File file) {
		//TODO the source should not be kept as field
		parsedSource = SourceParser.parse(file, builtProjectClassLoader, "UTF-8");
		CompilationUnitTree cu = parsedSource.getCompilationUnitTree();

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
			TreeMatcher patternNodeMatcher = (TreeMatcher) constructor.newInstance(patternNode, this);
			patternNodeMatcher = addAnnotationMatchers(patternNode, patternNodeMatcher);
			return patternNodeMatcher;
		}
		catch (Exception e) {
			throw new RuntimeException("Cannont create matcher of type:" + matcherClass.getName() + " with node of type:"
					+ patternNode.getClass().getName() + ":" + e, e);
		}

	}

	/**
	 * adds the annotation matchers that would affect the patternNodeMatcher behaviour. Please note that this only applies to nodes that can have
	 * annotations.
	 * @param patternNode
	 * @param patternNodeMatcher
	 * @return
	 */
	private TreeMatcher addAnnotationMatchers(Tree patternNode, TreeMatcher aPatternNodeMatcher) {
		List<? extends AnnotationTree> annotations = getAnnotationTrees(patternNode);
		TreeMatcher patternNodeMatcher = aPatternNodeMatcher;
		for (AnnotationTree annotation : annotations) {
			patternNodeMatcher = addAnnotationMatcher(annotation, patternNode, patternNodeMatcher);
		}
		return patternNodeMatcher;
	}

	/**
	 * @param annotation
	 * @param patternNode
	 * @param patternNodeMatcher
	 * @return an matcher adding the annotation specific functionality or patternNodeMatcher if no corresponding matcher was found
	 */
	private TreeMatcher addAnnotationMatcher(AnnotationTree annotation, Tree patternNode, TreeMatcher patternNodeMatcher) {
		TypeMirror type = InternalUtils.typeOf(annotation.getAnnotationType());
		String matcherName = type.toString();
		Class<? extends TreeMatcher> matcherClass = wildcardDictionary.findMatcherClass(matcherName);
		if (matcherClass == null) {
			return patternNodeMatcher;
		}

		try {
			// TODO find here compatible constructor
			Constructor<?> constructor = matcherClass.getDeclaredConstructors()[0];
			TreeMatcher annotationNodeMatcher = (TreeMatcher) constructor.newInstance(annotation, patternNode, patternNodeMatcher);

			return annotationNodeMatcher;
		}
		catch (Exception e) {
			throw new RuntimeException("Cannont create annotation matcher of type:" + matcherClass.getName() + " with node of type:"
					+ patternNode.getClass().getName() + ":" + e, e);
		}
	}

	private List<? extends AnnotationTree> getAnnotationTrees(Tree node) {
		if (node instanceof MethodTree) {
			return ((MethodTree) node).getModifiers().getAnnotations();
		}
		if (node instanceof ClassTree) {
			return ((ClassTree) node).getModifiers().getAnnotations();
		}
		if (node instanceof VariableTree) {
			return ((VariableTree) node).getModifiers().getAnnotations();
		}
		return Collections.emptyList();
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