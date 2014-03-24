package org.bugby.api.wildcard;

import com.sun.source.tree.Tree;

/**
 * 
 * This class builds matchers from the AST node of the pattern source file. It may use the wildcard annotation to know
 * which matcher to build.
 * 
 * @author acraciun
 */
public interface TreeMatcherFactory {
	/**
	 * 
	 * @param patternNode
	 * @return the matcher corresponding to the given AST node or null if the matching of the node is skipped
	 */
	public TreeMatcher build(Tree patternNode);
}
