package org.bugby.pattern.example.pmd;

public class OverrideBothEqualsAndHashcodeCheck2 {
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
		OverrideBothEqualsAndHashcodeCheck2 other = (OverrideBothEqualsAndHashcodeCheck2) obj;
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

}
