package org.bugby.pattern.example.pmd.basic;

public class OverrideBothEqualsAndHashcodeBug2 {
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (field == null ? 0 : field.hashCode());
		result = prime * result + field1;
		return result;
	}

}
