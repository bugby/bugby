package org.bugby.bugs.findbugs;

import org.bugby.annotation.GoodExampleTrigger;

@GoodExampleTrigger(
		forExample = ClonableWithoutClone.class)
public class ClonableWithoutCloneTrigger implements Cloneable {

}
