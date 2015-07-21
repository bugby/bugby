package org.bugby.bugs.findbugs.badpractice;

import static org.bugby.wildcard.Wildcards.someTypedValue;

import java.util.Collection;
import java.util.Map;

import org.bugby.api.annotation.Pattern;

/**
 *
 * PZ: Don't reuse entry objects in iterators (PZ_DONT_REUSE_ENTRY_OBJECTS_IN_ITERATORS) The entrySet() method is allowed to return a view of the
 * underlying Map in which an Iterator and Map.Entry. This clever idea was used in several Map implementations, but introduces the possibility of
 * nasty coding mistakes. If a map m returns such an iterator for an entrySet, then c.addAll(m.entrySet()) will go badly wrong. All of the Map
 * implementations in OpenJDK 1.7 have been rewritten to avoid this, you should to.
 * 
 * @author acraciun
 */
@Pattern
public class DontReuseEntryObjectsInIterators {
	@SuppressWarnings("unchecked")
	public void someCode() {
		someTypedValue(Collection.class).addAll(someTypedValue(Map.class).entrySet());
	}
}
