package org.bugby.bugs.findbugs;

import org.bugby.annotation.GoodExampleTrigger;

@GoodExampleTrigger(
		forExample = CloneableWithoutClone.class)
public class CloneableWithoutCloneTrigger implements Cloneable {

}
