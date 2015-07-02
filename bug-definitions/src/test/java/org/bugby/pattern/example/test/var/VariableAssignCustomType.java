package org.bugby.pattern.example.test.var;

import org.bugby.api.Pattern;

@Pattern
public class VariableAssignCustomType {
	public void someCode(MyCustomType someVar) {
		if (someVar == null) {
			return;
		}
	}

}
