package org.bugby.bugs.findbugs.badpractice;

import static org.bugby.wildcard.Wildcards.someExpressionUsing;
import static org.bugby.wildcard.Wildcards.someTypedValue;

import org.bugby.api.annotation.Pattern;

/**
 *
 * UI: Usage of GetResource may be unsafe if class is extended (UI_INHERITANCE_UNSAFE_GETRESOURCE) Calling this.getClass().getResource(...) could
 * give results other than expected if this class is extended by a class in another package.
 *
 * @author acraciun
 */
@Pattern
public class InheritanceUnsafeGetResource {
	public void someCode() {
		someExpressionUsing(this.getClass().getResource(someTypedValue(String.class)));
	}
}
