package org.bugby.bugs.findbugs.badpractice;

import static org.bugby.wildcard.Wildcards.someStringLiteralContaining;

import org.bugby.api.annotation.Pattern;

/**
 *
 * FS: Format string should use %n rather than \n (VA_FORMAT_STRING_USES_NEWLINE)
 * 
 * This format string include a newline character (\n). In format strings, it is generally preferable better to use %n, which will produce the
 * platform-specific line separator.
 * 
 * @author acraciun
 */
@Pattern
public class FormatStringUsesNewLine {
	public void someCode() {
		String.format(someStringLiteralContaining("\\\\n"));
	}
}
