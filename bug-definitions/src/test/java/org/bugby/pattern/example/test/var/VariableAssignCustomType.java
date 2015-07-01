package org.bugby.pattern.example.test.var;

import org.bugby.annotation.BadExample;

@BadExample
public class VariableAssignCustomType {
	public void someCode(MyCustomType someVar) {
		if (someVar == null) {
			return;
		}
	}

}
