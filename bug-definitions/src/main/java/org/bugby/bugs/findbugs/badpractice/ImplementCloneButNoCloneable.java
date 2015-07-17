package org.bugby.bugs.findbugs.badpractice;

import static org.bugby.wildcard.Wildcards.someValue;

import org.bugby.wildcard.MissingInterface;
import org.bugby.wildcard.Pattern;

/**
 * CN: Class defines clone() but doesn't implement Cloneable (CN_IMPLEMENTS_CLONE_BUT_NOT_CLONEABLE) <br>
 * This class defines a clone() method but the class doesn't implement Cloneable. There are some situations in which this is OK (e.g., you want
 * to control how subclasses can clone themselves), but just make sure that this is what you intended.
 * @author acraciun
 */
@Pattern
public class ImplementCloneButNoCloneable implements MissingInterface<Cloneable> {
	@Override
	public Object clone() {
		return someValue();
	}
}
