package org.bugby.wildcard;

import org.bugby.wildcard.api.Wildcard;
import org.bugby.wildcard.matcher.SomeTypeMatcher;

/**
 * matches any type
 * 
 * @author acraciun
 * 
 */
@Wildcard(matcher = SomeTypeMatcher.class)
public class SomeType {

}
