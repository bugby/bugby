package org.bugby.wildcard.api;

import org.richast.scope.Scope;
import org.richast.type.TypeWrapper;
import org.richast.variable.Variable;

public interface MatchingContext {
	public Variable getVariableMapping(String nameInPatternAST, Scope scopeInPatternAST);

	public boolean setVariableMapping(String nameInPatternAST, Scope scopeInPatternAST, Variable var);

	public void addTypeRestriction(String nameInPatternAST, Scope scopeInPatternAST, TypeWrapper type);
}
