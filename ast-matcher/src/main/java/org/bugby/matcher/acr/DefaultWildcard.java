package org.bugby.matcher.acr;

import java.util.ArrayList;
import java.util.List;

public class DefaultWildcard<T> implements Wildcard<T> {
	private final T value;

	public DefaultWildcard(T value) {
		this.value = value;
	}

	public T getValue() {
		return value;
	}

	@Override
	public boolean match(T t) {
		return t.equals(value);
	}

	public static <T> List<Wildcard<T>> build(T... values) {
		List<Wildcard<T>> list = new ArrayList<Wildcard<T>>(values.length);
		for (T v : values) {
			list.add(new DefaultWildcard<T>(v));
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public static <T> List<Wildcard<T>> buildBegin(T... values) {
		List<Wildcard<T>> list = new ArrayList<Wildcard<T>>(values.length + 1);
		list.add((Wildcard<T>) OneLevelMatcher.BEGIN);
		list.addAll(build(values));
		return list;
	}

	@SuppressWarnings("unchecked")
	public static <T> List<Wildcard<T>> buildEnd(T... values) {
		List<Wildcard<T>> list = new ArrayList<Wildcard<T>>(values.length + 1);
		list.addAll(build(values));
		list.add((Wildcard<T>) OneLevelMatcher.END);
		return list;
	}

	@SuppressWarnings("unchecked")
	public static <T> List<Wildcard<T>> buildBeginEnd(T... values) {
		List<Wildcard<T>> list = new ArrayList<Wildcard<T>>(values.length + 2);
		list.add((Wildcard<T>) OneLevelMatcher.BEGIN);
		list.addAll(build(values));
		list.add((Wildcard<T>) OneLevelMatcher.END);
		return list;
	}

	@SuppressWarnings("unchecked")
	public static <T> List<Wildcard<T>> buildEmpty() {
		List<Wildcard<T>> list = new ArrayList<Wildcard<T>>();
		list.add((Wildcard<T>) OneLevelMatcher.EMPTY);
		return list;
	}
}
