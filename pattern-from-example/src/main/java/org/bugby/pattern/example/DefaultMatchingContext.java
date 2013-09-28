package org.bugby.pattern.example;

import java.util.HashMap;
import java.util.Map;

import org.bugby.wildcard.api.MatchingContext;
import org.richast.scope.Scope;
import org.richast.type.TypeWrapper;
import org.richast.variable.Variable;

public class DefaultMatchingContext implements MatchingContext {
	private final Map<NameAndScope, Variable> variables = new HashMap<NameAndScope, Variable>();
	private final Map<NameAndScope, TypeWrapper> typeRestrictions = new HashMap<NameAndScope, TypeWrapper>();

	private static class NameAndScope {
		private final Scope scope;
		private final String name;

		public NameAndScope(Scope scope, String name) {
			this.scope = scope;
			this.name = name;
		}

		public Scope getScope() {
			return scope;
		}

		public String getName() {
			return name;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			result = prime * result + ((scope == null) ? 0 : scope.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			NameAndScope other = (NameAndScope) obj;
			if (name == null) {
				if (other.name != null) {
					return false;
				}
			} else if (!name.equals(other.name)) {
				return false;
			}

			return scope == other.scope;
		}

	}

	@Override
	public Variable getVariableMapping(String nameInPatternAST, Scope scopeInPatternAST) {
		return variables.get(new NameAndScope(scopeInPatternAST, nameInPatternAST));
	}

	@Override
	public boolean setVariableMapping(String nameInPatternAST, Scope scopeInPatternAST, Variable var) {
		NameAndScope key = new NameAndScope(scopeInPatternAST, nameInPatternAST);
		TypeWrapper typeRestriction = typeRestrictions.get(key);
		if (typeRestriction == null || var.getType().isAssignableFrom(typeRestriction)) {
			variables.put(key, var);
			return true;
		}
		return false;
	}

	@Override
	public void addTypeRestriction(String nameInPatternAST, Scope scopeInPatternAST, TypeWrapper type) {
		typeRestrictions.put(new NameAndScope(scopeInPatternAST, nameInPatternAST), type);
	}

}
