package org.bugby.bugs.findbugs.badpractice;

import org.bugby.api.annotation.Pattern;

/**
 *
 * ISC: Needless instantiation of class that only supplies static methods (ISC_INSTANTIATE_STATIC_CLASS)
 * 
 * This class allocates an object that is based on a class that only supplies static methods. This object does not need to be created, just
 * access the static methods directly using the class name as a qualifier.
 * 
 * 
 * @author acraciun
 */
@Pattern
public class InstantiateStaticClass {
	public void someCode() {
		new SomeClassWithStaticMethodsOnly();
	}
}