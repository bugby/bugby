package org.bugby.bugs.findbugs.badpractice;

import static org.bugby.wildcard.Wildcards.someTypedValue;

import java.io.IOException;
import java.io.InputStream;

import org.bugby.api.annotation.Pattern;

/**
 * RR: Method ignores results of InputStream.read() (RR_NOT_CHECKED) This method ignores the return value of one of the variants of
 * java.io.InputStream.read() which can return multiple bytes. If the return value is not checked, the caller will not be able to correctly
 * handle the case where fewer bytes were read than the caller requested. This is a particularly insidious kind of bug, because in many programs,
 * reads from input streams usually do read the full amount of data requested, causing the program to fail only sporadically.
 * 
 * @author acraciun
 */
@Pattern
public class ReadNotChecked {
	public void someCode() throws IOException {
		someTypedValue(InputStream.class).read();
	}
}
