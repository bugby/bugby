package org.bugby.wildcard;

import org.bugby.wildcard.api.WildcardFactory;
import org.bugby.wildcard.matcher.SomeTypeMatcherFactory;

/**
 * matches any type
 * 
 * @author acraciun
 * 
 */
@WildcardFactory(SomeTypeMatcherFactory.class)
public class SomeType {

}
