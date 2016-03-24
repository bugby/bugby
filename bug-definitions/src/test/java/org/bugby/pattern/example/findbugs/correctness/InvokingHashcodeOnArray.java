package org.bugby.pattern.example.findbugs.correctness;

import static org.bugby.wildcard.Wildcards.someArrayValue;
import static org.bugby.wildcard.Wildcards.someExpressionUsing;

/**
 * DMI: Invocation of hashCode on an array (DMI_INVOKING_HASHCODE_ON_ARRAY) The code invokes hashCode on an array. Calling hashCode on an array
 * returns the same value as System.identityHashCode, and ingores the contents and length of the array. If you need a hashCode that depends on
 * the contents of an array a, use java.util.Arrays.hashCode(a).
 *
 * TODO Description of this class
 * @author acraciun
 */
public class InvokingHashcodeOnArray {
	public void someCode() {
		//TODO someArray may or not accept a type
		someExpressionUsing(someArrayValue().hashCode());
	}
}