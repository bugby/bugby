package org.bugby.annotation;

public enum MatchingType {
	STRICT, // as written in the example
	ANY // (the name is node good) - for example when implementing an interface, add this to tell the bugby you may also
		// inherit from a class implementing that interface
}
