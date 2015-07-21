package org.bugby.bugs.findbugs.badpractice;

import java.util.Collection;

import org.bugby.api.annotation.Pattern;

/**
 *
 * DMI: Don't use removeAll to clear a collection (DMI_USING_REMOVEALL_TO_CLEAR_COLLECTION)
 *
 * If you want to remove all elements from a collection c, use c.clear, not c.removeAll(c). Calling c.removeAll(c) to clear a collection is less
 * clear, susceptible to errors from typos, less efficient and for some collections, might throw a ConcurrentModificationException.
 * @author acraciun
 */
@Pattern
public class CollectionRemoveAll {
	public void someCode(Collection<?> collection) {
		collection.removeAll(collection);
	}
}
