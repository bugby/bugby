package org.bugby.matcher.tree;

import java.util.ArrayList;
import java.util.List;

import org.bugby.matcher.tree.MatchingType;

class DefaultWildcard<T> implements Wildcard<T> {
	/**
	 * means next node must match
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static final Wildcard<?> BEGIN = new DefaultWildcard(MatchingType.begin);
	/**
	 * means the last match should've been on the last position
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static final Wildcard<?> END = new DefaultWildcard(MatchingType.end);

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static final Wildcard<?> EMPTY = new DefaultWildcard(MatchingType.empty);

	private final T value;

	private final MatchingType matchingType;

	public DefaultWildcard(T value) {
		this.value = value;
		this.matchingType = MatchingType.normal;
	}

	public DefaultWildcard(MatchingType matchingType) {
		this.value = null;
		this.matchingType = matchingType;
	}

	public T getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "W:" + value;
	}

	@Override
	public boolean match(T t) {
		return t.equals(value);
	}

	public MatchingType getMatchingType() {
		return matchingType;
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
		list.add((Wildcard<T>) BEGIN);
		list.addAll(build(values));
		return list;
	}

	@SuppressWarnings("unchecked")
	public static <T> List<Wildcard<T>> buildEnd(T... values) {
		List<Wildcard<T>> list = new ArrayList<Wildcard<T>>(values.length + 1);
		list.addAll(build(values));
		list.add((Wildcard<T>) END);
		return list;
	}

	@SuppressWarnings("unchecked")
	public static <T> List<Wildcard<T>> buildBeginEnd(T... values) {
		List<Wildcard<T>> list = new ArrayList<Wildcard<T>>(values.length + 2);
		list.add((Wildcard<T>) BEGIN);
		list.addAll(build(values));
		list.add((Wildcard<T>) END);
		return list;
	}

	@SuppressWarnings("unchecked")
	public static <T> List<Wildcard<T>> buildEmpty() {
		List<Wildcard<T>> list = new ArrayList<Wildcard<T>>();
		list.add((Wildcard<T>) EMPTY);
		return list;
	}
}
