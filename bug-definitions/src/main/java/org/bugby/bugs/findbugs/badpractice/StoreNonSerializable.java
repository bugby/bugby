package org.bugby.bugs.findbugs.badpractice;

/**
 *
 * J2EE: Store of non serializable object into HttpSession (J2EE_STORE_OF_NON_SERIALIZABLE_OBJECT_INTO_SESSION) This code seems to be storing a
 * non-serializable object into an HttpSession. If this session is passivated or migrated, an error will result.
 * 
 * @author acraciun
 */
public class StoreNonSerializable {
	public void someCode() {
		//TODO move to servlet API
		//someTypedValue(HttpServletSession.class).setAttribute(someValue(), someTypedValue(NonSerializable.class));
	}
}
