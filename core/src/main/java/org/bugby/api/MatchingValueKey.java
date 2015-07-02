package org.bugby.api;

public final class MatchingValueKey {
	private final String matcherName;
	private final Object valueKey;

	public MatchingValueKey(String matcherName, Object valueKey) {
		this.matcherName = matcherName;
		this.valueKey = valueKey;
	}

	public String getMatcherName() {
		return matcherName;
	}

	public Object getValueKey() {
		return valueKey;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((matcherName == null) ? 0 : matcherName.hashCode());
		result = prime * result + ((valueKey == null) ? 0 : valueKey.hashCode());
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
		if (valueKey == null) {
			if (other.valueKey != null) {
				return false;
			}
		} else if (!valueKey.equals(other.valueKey)) {
			return false;
		}
		return true;
	}

}
