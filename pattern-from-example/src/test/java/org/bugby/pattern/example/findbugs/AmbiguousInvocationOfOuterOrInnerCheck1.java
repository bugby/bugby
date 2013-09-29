package org.bugby.pattern.example.findbugs;

public class AmbiguousInvocationOfOuterOrInnerCheck1 {

	public void doSomething(int a) {

	}

	public class BaseType {
		public void doSomething(int a) {

		}
	}

	public class InnerType extends BaseType {
		public void someCode(int b) {
			doSomething(b + 10);
		}
	}

}
