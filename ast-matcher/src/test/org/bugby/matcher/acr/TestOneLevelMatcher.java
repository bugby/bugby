package org.bugby.matcher.acr;

import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import org.bugby.matcher.acr.OneLevelMatcher.DefaultWildcard;
import org.bugby.matcher.acr.OneLevelMatcher.Wildcard;
import org.junit.Test;

@SuppressWarnings("unchecked")
public class TestOneLevelMatcher {
	@Test
	public void testSimpleMatch() {
		OneLevelMatcher matcher = new OneLevelMatcher();
		List<String> nodes = Arrays.asList("a", "b", "c");
		List<? extends Wildcard<String>> wildcards = Arrays.asList(new DefaultWildcard<String>("a"));
		Assert.assertEquals(true, matcher.match(nodes, wildcards));
	}

	@Test
	public void testTwoMatch() {
		OneLevelMatcher matcher = new OneLevelMatcher();
		List<String> nodes = Arrays.asList("a", "b", "c");
		List<? extends Wildcard<String>> wildcards = Arrays.asList(new DefaultWildcard<String>("a"),
				new DefaultWildcard<String>("c"));
		Assert.assertEquals(true, matcher.match(nodes, wildcards));
	}

	@Test
	public void testSimpleNotMatch() {
		OneLevelMatcher matcher = new OneLevelMatcher();
		List<String> nodes = Arrays.asList("a", "b", "c");
		List<? extends Wildcard<String>> wildcards = Arrays.asList(new DefaultWildcard<String>("x"));
		Assert.assertEquals(false, matcher.match(nodes, wildcards));
	}

	@Test
	public void testBegin() {
		OneLevelMatcher matcher = new OneLevelMatcher();
		List<String> nodes = Arrays.asList("a", "b", "c");
		List<? extends Wildcard<String>> wildcards = Arrays.asList((Wildcard<String>) OneLevelMatcher.BEGIN,
				new DefaultWildcard<String>("a"));
		Assert.assertEquals(true, matcher.match(nodes, wildcards));
	}

	@Test
	public void testBeginWrong() {
		OneLevelMatcher matcher = new OneLevelMatcher();
		List<String> nodes = Arrays.asList("a", "b", "c");
		List<? extends Wildcard<String>> wildcards = Arrays.asList((Wildcard<String>) OneLevelMatcher.BEGIN,
				new DefaultWildcard<String>("b"));
		Assert.assertEquals(false, matcher.match(nodes, wildcards));
	}

	@Test
	public void testEnd() {
		OneLevelMatcher matcher = new OneLevelMatcher();
		List<String> nodes = Arrays.asList("a", "b", "c");
		List<? extends Wildcard<String>> wildcards = Arrays.asList(new DefaultWildcard<String>("c"),
				(Wildcard<String>) OneLevelMatcher.END);
		Assert.assertEquals(true, matcher.match(nodes, wildcards));
	}

	@Test
	public void testEndWrong() {
		OneLevelMatcher matcher = new OneLevelMatcher();
		List<String> nodes = Arrays.asList("a", "b", "c");
		List<? extends Wildcard<String>> wildcards = Arrays.asList(new DefaultWildcard<String>("b"),
				(Wildcard<String>) OneLevelMatcher.END);
		Assert.assertEquals(false, matcher.match(nodes, wildcards));
	}

	@Test
	public void testExactMatch() {
		OneLevelMatcher matcher = new OneLevelMatcher();
		List<String> nodes = Arrays.asList("a", "b", "c");
		List<? extends Wildcard<String>> wildcards = Arrays.asList((Wildcard<String>) OneLevelMatcher.BEGIN,
				new DefaultWildcard<String>("a"), new DefaultWildcard<String>("b"), new DefaultWildcard<String>("c"),
				(Wildcard<String>) OneLevelMatcher.END);
		Assert.assertEquals(true, matcher.match(nodes, wildcards));
	}

}
