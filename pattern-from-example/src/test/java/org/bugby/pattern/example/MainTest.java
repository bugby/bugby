package org.bugby.pattern.example;

public class MainTest {
	public static void main(String[] args) {
		Main.check("../default-examples/src/main/java/org/bugby/bugs/pmd/CollapsibleIfStatements.java",
				"src/test/java/org/bugby/pattern/example/pmd/CollapsibleIfStatementsCheck4.java");
		// Main.check("../default-examples/src/main/java/org/bugby/bugs/findbugs/AmbiguousInvocationOfOuterOrInner.java",
		// "src/test/java/org/bugby/pattern/example/findbugs/AmbiguousInvocationOfOuterOrInnerCheck1.java");
		// Main.check(
		// "../default-examples/src/main/java/org/bugby/bugs/findbugs/AmbiguousInvocationOfOuterOrInnerTrigger.java",
		// "src/test/java/org/bugby/pattern/example/findbugs/AmbiguousInvocationOfOuterOrInnerTriggerCheck1.java");
	}
}
