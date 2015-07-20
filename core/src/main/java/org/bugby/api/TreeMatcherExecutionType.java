package org.bugby.api;

/**
 *
 * this enum controls what happens during the subsequent maching calls, after the execution of the start part
 * @author acraciun
 */
public enum TreeMatcherExecutionType {
	keep, //is included in the next matching list
	ignore // is not included in the next matching list
}
