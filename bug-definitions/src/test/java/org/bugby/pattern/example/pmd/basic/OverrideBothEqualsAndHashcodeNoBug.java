package org.bugby.pattern.example.pmd.basic;

public class OverrideBothEqualsAndHashcodeNoBug {
	private int field1;
	private String field;

	public int getField1() {
		return field1;
	}

	public void setField1(int field1) {
		this.field1 = field1;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
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
		OverrideBothEqualsAndHashcodeNoBug other = (OverrideBothEqualsAndHashcodeNoBug) obj;
		if (field == null) {
			if (other.field != null) {
				return false;
			}
		} else if (!field.equals(other.field)) {
			return false;
		}
		if (field1 != other.field1) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (field == null ? 0 : field.hashCode());
		result = prime * result + field1;
		return result;
	}

}
