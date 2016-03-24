package org.bugby.pattern.example.findbugs.correctness;

import java.util.Collection;

/**
 *
 * DMI: Collections should not contain themselves (DMI_COLLECTIONS_SHOULD_NOT_CONTAIN_THEMSELVES) This call to a generic collection's method
 * would only make sense if a collection contained itself (e.g., if s.contains(s) were true). This is unlikely to be true and would cause
 * problems if it were true (such as the computation of the hash code resulting in infinite recursion). It is likely that the wrong value is
 * being passed as a parameter.
 * 
 * @author acraciun
 */
public class CollectionsShouldNotContainThemselves {
	public void someCode1(Collection c) {
		c.add(c);
		//TODO add others add methods !?
	}
}
