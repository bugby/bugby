package org.bugby.bugs.findbugs.badpractice;

import static org.bugby.wildcard.WildcardAnnotations.$Missing;
import static org.bugby.wildcard.Wildcards.someExpressionUsing;
import static org.bugby.wildcard.Wildcards.someValue;

import org.bugby.api.annotation.MethodMatching;
import org.bugby.api.annotation.Pattern;
import org.bugby.api.annotation.PatternListMatchingType;

/**
 * Class implements Cloneable but does not define or use the clone method. CN: clone method does not call super.clone() (CN_IDIOM_NO_SUPER_CALL)
 *
 * This non-final class defines a clone() method that does not call super.clone(). If this class ("A") is extended by a subclass ("B"), and the
 * subclass B calls super.clone(), then it is likely that B's clone() method will return an object of type A, which violates the standard
 * contract for clone(). If all clone() methods call super.clone(), then they are guaranteed to use Object.clone(), which always returns an
 * object of the correct type.
 *
 * @author acraciun
 */
@Pattern
public class CloneNoSuperCall2 implements Cloneable {
	@Override
	@MethodMatching(matchThrows = PatternListMatchingType.ignore)
	public Object clone() throws CloneNotSupportedException {
		$Missing();
		someExpressionUsing(super.clone());
		return someValue();
	}
}
