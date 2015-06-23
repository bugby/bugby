package org.bugby.api.wildcard;

public final class MatchingValueKey {
	private final String matcherName;
	private final String valueName;

	public MatchingValueKey(String matcherName, String valueName) {
		this.matcherName = matcherName;
		this.valueName = valueName;
	}

	public String getMatcherName() {
		return matcherName;
	}

	public String getValueName() {
		return valueName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (matcherName == null ? 0 : matcherName.hashCode());
		result = prime * result + (valueName == null ? 0 : valueName.hashCode());
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
		MatchingValueKey other = (MatchingValueKey) obj;
		if (matcherName == null) {
			if (other.matcherName != null) {
				return false;
			}
		} else if (!matcherName.equals(other.matcherName)) {
			return false;
		}
		if (valueName == null) {
			if (other.valueName != null) {
				return false;
			}
		} else if (!valueName.equals(other.valueName)) {
			return false;
		}
		return true;
	}

}
