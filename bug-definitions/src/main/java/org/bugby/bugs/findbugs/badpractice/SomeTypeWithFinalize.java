package org.bugby.bugs.findbugs.badpractice;

import org.bugby.api.annotation.Pattern;

@Pattern(root = false)
public class SomeTypeWithFinalize {
	@Override
	public void finalize() {
	}
}
