package org.bugby.api.annotation;

/**
 * it is used to describe how a matching between a list of patterns should be applied to the target source nodes.
 * @author acraciun
 */
public enum PatternListMatchingType {
	exact, // the pattern of 3 elements must match exactly 3 source nodes
	partial, //the pattern of 3 elements must match at least 3 other elements
	ignore;//no match is needed
}
