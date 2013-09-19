package org.bugby.matcher.acr;

public interface NodeMatch<T, W> {
	public boolean match(W wildcard, T node);
}
