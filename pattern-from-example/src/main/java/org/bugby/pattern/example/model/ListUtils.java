package org.bugby.pattern.example.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListUtils {
	public static <T> List<T> singletonListOrEmpty(T elem) {
		if (elem == null) {
			return Collections.emptyList();
		}
		return Collections.singletonList(elem);
	}

	public static <T> List<T> notNull(List<T> list) {
		if (list == null) {
			return Collections.emptyList();
		}
		return list;
	}

	public static <T> List<T> asList(T... elems) {
		List<T> list = new ArrayList<T>();
		for (T elem : elems) {
			if (elem != null) {
				list.add(elem);
			}
		}
		return list;
	}

}
