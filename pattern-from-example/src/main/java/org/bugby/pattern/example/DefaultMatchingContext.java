package org.bugby.pattern.example;

import japa.parser.ast.Node;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.bugby.wildcard.api.MatchingContext;
import org.richast.scope.Scope;
import org.richast.type.TypeWrapper;
import org.richast.variable.Variable;

public class DefaultMatchingContext implements MatchingContext {
	private final Map<NameAndScope, Variable> variables = new HashMap<NameAndScope, Variable>();
	private final Map<NameAndScope, TypeWrapper> typeRestrictions = new HashMap<NameAndScope, TypeWrapper>();
	private final Map<String, CorrelationInfo> correlations = new HashMap<String, CorrelationInfo>();

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

	private static class CorrelationInfo {
		private final Comparator<Node> comparator;
		private final Node node;

		public CorrelationInfo(Comparator<Node> comparator, Node node) {
			this.comparator = comparator;
			this.node = node;
		}

		public Comparator<Node> getComparator() {
			return comparator;
		}

		public Node getNode() {
			return node;
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
		if (typeRestriction == null || typeRestriction.isAssignableFrom(var.getType())) {
			variables.put(key, var);
			return true;
		}
		return false;
	}

	@Override
	public void addTypeRestriction(String nameInPatternAST, Scope scopeInPatternAST, TypeWrapper type) {
		typeRestrictions.put(new NameAndScope(scopeInPatternAST, nameInPatternAST), type);
	}

	@Override
	public boolean checkCorrelation(String key, Node nodeInSourceAST, Comparator<Node> comparator) {
		CorrelationInfo correlation = correlations.get(key);
		if (correlation == null) {
			correlations.put(key, new CorrelationInfo(comparator, nodeInSourceAST));
			return true;
		}

		return correlation.getComparator().compare(correlation.getNode(), nodeInSourceAST) == 0;
	}

	@Override
	public void clearDataForNode(Node nodeInSourceAST) {
		// clear correlations
		Iterator<Map.Entry<String, CorrelationInfo>> it = correlations.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, CorrelationInfo> entry = it.next();
			if (nodeInSourceAST == entry.getValue().getNode()) {
				it.remove();
			}
		}

		// TODO clear all the other maps
	}
}
