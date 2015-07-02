package org.bugby.api;

import org.bugby.matcher.javac.ElementWrapperTree;

import com.sun.source.tree.Tree;

/**
 * This class builds matchers from the AST node of the pattern source file. It may use the wildcard annotation to know which matcher to build.
 * @author acraciun
 */
public interface TreeMatcherFactory {
	/**
	 * @param patternNode
	 * @return the matcher corresponding to the given AST node or null if the matching of the node is skipped
	 */
	public TreeMatcher build(Tree patternNode);

	public TreeMatcher buildForType(String type);

	/**
	 * tries to parse the given type name. In case the source of the given type is unavailable a {@link ElementWrapperTree} must be returned
	 * @param type
	 * @return
	 */
	public Tree loadTypeDefinition(String type);
}
