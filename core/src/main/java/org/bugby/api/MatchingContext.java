package org.bugby.api;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;

import org.bugby.matcher.javac.source.ParsedSource;

import com.google.common.collect.Multimap;
import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.Tree;

public interface MatchingContext {

	List<List<MatchingPath>> matchOrdered(List<TreeMatcher> matchers, List<? extends Tree> nodes);

	List<List<MatchingPath>> matchOrdered(List<TreeMatcher> matchers, List<? extends Tree> nodes, SolutionFoundCallback solutionFoundCallback);

	List<List<MatchingPath>> matchUnordered(List<TreeMatcher> matchers, List<? extends Tree> nodes);

	List<List<MatchingPath>> matchUnordered(List<TreeMatcher> matchers, List<? extends Tree> nodes, SolutionFoundCallback solutionFoundCallback);

	Multimap<TreeMatcher, Tree> getMatches();

	List<Tree> getSiblingsOf(Tree node);

	<V> void putValue(MatchingValueKey key, V value);

	<V> V getValue(MatchingValueKey key);

	void removeValue(MatchingValueKey key);

	CompilationUnitTree getCompilationUnitTree();

	/**
	 * contains as last node the current matcher being checked. It assumes that the next check is actually part of the path, until the matches
	 * method of the matcher returns.
	 * @return
	 */
	MatchingPath getCurrentMatchingPath();

	/**
	 * @return true if there is a match between the pattern and the source corresponding to this context
	 */
	boolean matches();

	ParsedSource getParsedSource();

	boolean compatibleTypes(TypeMirror patternType, TypeMirror sourceNodeType);

	boolean sameType(TypeMirror patternType, TypeMirror sourceNodeType);

	Map<MatchingValueKey, Object> getValues();

	/**
	 * use this method to read fields of type Class<?> from an annotation config, as otherwise you may get MirroredTypeException.
	 * @param element
	 * @param annotationType
	 * @param fieldName
	 * @return
	 */
	Class<?> getClassAnnotationField(Element element, Class<? extends Annotation> annotationType, String fieldName);

}
