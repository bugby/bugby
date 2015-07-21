package org.bugby.bugs.findbugs.badpractice;

import org.bugby.api.annotation.Missing;
import org.bugby.api.annotation.ModifiersMatching;
import org.bugby.api.annotation.Pattern;

@Pattern(root = false)
public class SomeClassWithStaticMethodsOnly {
	@Missing
	@ModifiersMatching(STATIC = true)
	public void method() {
	}
}
