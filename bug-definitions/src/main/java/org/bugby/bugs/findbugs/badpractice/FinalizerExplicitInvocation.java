package org.bugby.bugs.findbugs.badpractice;

import static org.bugby.wildcard.Wildcards.someTypedValue;

import org.bugby.api.annotation.Pattern;

/**
 *
 * FI: Explicit invocation of finalizer (FI_EXPLICIT_INVOCATION)
 *
 * This method contains an explicit invocation of the finalize() method on an object. Because finalizer methods are supposed to be executed once,
 * and only by the VM, this is a bad idea.
 *
 * If a connected set of objects beings finalizable, then the VM will invoke the finalize method on all the finalizable object, possibly at the
 * same time in different threads. Thus, it is a particularly bad idea, in the finalize method for a class X, invoke finalize on objects
 * referenced by X, because they may already be getting finalized in a separate thread.
 *
 * @author acraciun
 */
@Pattern
public class FinalizerExplicitInvocation {
	public void someCode() {
		someTypedValue(SomeTypeWithFinalize.class).finalize();
	}
}
