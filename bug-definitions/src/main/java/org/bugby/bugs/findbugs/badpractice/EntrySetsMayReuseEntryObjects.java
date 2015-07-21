package org.bugby.bugs.findbugs.badpractice;

import static org.bugby.wildcard.Wildcards.someTypedValue;

import java.util.Collection;
import java.util.EnumMap;
import java.util.IdentityHashMap;

import org.bugby.api.annotation.OrSet;

/**
 * DMI: Adding elements of an entry set may fail due to reuse of Entry objects (DMI_ENTRY_SETS_MAY_REUSE_ENTRY_OBJECTS)
 *
 * The entrySet() method is allowed to return a view of the underlying Map in which a single Entry object is reused and returned during the
 * iteration. As of Java 1.6, both IdentityHashMap and EnumMap did so. When iterating through such a Map, the Entry value is only valid until you
 * advance to the next iteration. If, for example, you try to pass such an entrySet to an addAll method, things will go badly wrong.
 *
 *
 * @author acraciun
 */
public class EntrySetsMayReuseEntryObjects {

	@SuppressWarnings({"unchecked"})
	@OrSet
	public void someCode1() {
		someTypedValue(Collection.class).addAll(someTypedValue(EnumMap.class).entrySet());
	}

	@SuppressWarnings({"unchecked"})
	@OrSet
	public void someCode2() {
		someTypedValue(Collection.class).addAll(someTypedValue(IdentityHashMap.class).entrySet());
	}
}
