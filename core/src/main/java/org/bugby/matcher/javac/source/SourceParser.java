package org.bugby.matcher.javac.source;

import org.bugby.api.BugbyException;

/**
 *
 * interface to be implemented by the different ways to get the AST tree corresponding to a given type name
 * @author acraciun
 */
public interface SourceParser {
	ParsedSource parseSource(String typeName) throws BugbyException;
}
