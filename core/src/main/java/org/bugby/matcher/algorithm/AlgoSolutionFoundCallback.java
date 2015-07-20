package org.bugby.matcher.algorithm;

import java.util.List;

public interface AlgoSolutionFoundCallback<R> {
	public void solutionFound(List<R> solution);
}
