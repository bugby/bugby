package org.bugby.engine;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.lang.model.type.TypeMirror;

import org.bugby.api.wildcard.MatchingContext;
import org.bugby.api.wildcard.TreeMatcher;
import org.richast.scope.Scope;

import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.sun.source.tree.Tree;

public class DefaultMatchingContext implements MatchingContext {
	private final Map<String, String> variables = new HashMap<String, String>();
	private final Map<String, TypeMirror> typeRestrictions = new HashMap<String, TypeMirror>();
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
			result = prime * result + (name == null ? 0 : name.hashCode());
			result = prime * result + (scope == null ? 0 : scope.hashCode());
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
		private final Comparator<Tree> comparator;
		private final Set<Tree> nodes = Sets.newIdentityHashSet();

		public CorrelationInfo(Comparator<Tree> comparator, Tree node) {
			this.comparator = comparator;
			this.nodes.add(node);
		}

		public Comparator<Tree> getComparator() {
			return comparator;
		}

		public Tree getFirstNode() {
			return nodes.iterator().next();
		}

		public void addNode(Tree n) {
			nodes.add(n);
		}

		public void removeNode(Tree n) {
			nodes.remove(n);
		}

		public int getNodeCount() {
			return nodes.size();
		}

		@Override
		public String toString() {
			return "CI:" + nodes;
		}

	}

	@Override
	public String getVariableMapping(String nameInPatternAST) {
		return variables.get(nameInPatternAST);
	}

	@Override
	public boolean setVariableMapping(String nameInPatternAST, String currentName, TypeMirror typeRestriction) {
		TypeMirror tr = typeRestrictions.get(nameInPatternAST);
		if (typeRestriction == null || typeRestriction.isAssignableFrom(var.getType())) {
			variables.put(nameInPatternAST, currentName);
			return true;
		}
		return false;
	}

	@Override
	public void addTypeRestriction(String nameInPatternAST, TypeMirror type) {
		typeRestrictions.put(nameInPatternAST, type);
	}

	@Override
	public boolean checkCorrelation(String key, Tree nodeInSourceAST, Comparator<Tree> comparator) {
		CorrelationInfo correlation = correlations.get(key);
		if (correlation == null) {
			correlations.put(key, new CorrelationInfo(comparator, nodeInSourceAST));
			return true;
		}

		if (correlation.getComparator().compare(correlation.getFirstNode(), nodeInSourceAST) == 0) {
			correlation.addNode(nodeInSourceAST);
			return true;
		}
		return false;
	}

	@Override
	public void clearDataForNode(Tree nodeInSourceAST) {
		// clear correlations
		Iterator<Map.Entry<String, CorrelationInfo>> it = correlations.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, CorrelationInfo> entry = it.next();
			entry.getValue().removeNode(nodeInSourceAST);
			if (entry.getValue().getNodeCount() == 0) {
				it.remove();
			}
		}

		// TODO clear all the other maps
	}

	@Override
	public Multimap<TreeMatcher, Tree> matchOrdered(List<TreeMatcher> matchers, List<? extends Tree> nodes) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Multimap<TreeMatcher, Tree> matchUnordered(List<TreeMatcher> matchers, List<? extends Tree> nodes) {
		// TODO Auto-generated method stub
		return null;
	}
}
