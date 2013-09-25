package org.bugby.wildcard.api;

import org.richast.scope.Scope;
import org.richast.variable.Variable;

public interface MatchingContext {
	public Variable getVariableMapping(String nameInPatternAST, Scope scopeInPatternAST);

	public void setVariableMapping(String nameInPatternAST, Scope scopeInPatternAST, Variable var);
}
