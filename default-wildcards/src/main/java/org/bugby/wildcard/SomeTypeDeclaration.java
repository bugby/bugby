package org.bugby.wildcard;

import org.bugby.wildcard.api.Wildcard;
import org.bugby.wildcard.matcher.SomeTypeDeclarationMatcher;

@Wildcard(matcher = SomeTypeDeclarationMatcher.class)
public class SomeTypeDeclaration {

}
