package org.bugby.pattern.example.findbugs;

import static org.bugby.pattern.example.MatchingHelper.assertFindbugs;
import static org.bugby.pattern.example.MatchingHelper.assertFindbugsNotMatch;

import org.junit.Test;

public class TestFindbugs {
	@Test
	public void testAmbiguousInvocationOfOuterOrInner1() {
		assertFindbugs("AmbiguousInvocationOfOuterOrInner2.java", "AmbiguousInvocationOfOuterOrInner2Check2.java", 18);
	}

	@Test
	public void testAmbiguousInvocationOfOuterOrInner2() {
		assertFindbugs("AmbiguousInvocationOfOuterOrInner.java", "AmbiguousInvocationOfOuterOrInnerCheck1.java", 18);
	}

	@Test
	public void testCheckImmutable1() {
		assertFindbugs("CheckImmutable.java", "CheckImmutableCheck1.java", 18);
	}

	@Test
	public void testCheckImmutable2() {
		assertFindbugs("CheckImmutable.java", "CheckImmutableCheck2.java", 18);
	}

	@Test
	public void testCheckImmutable3() {
		assertFindbugs("CheckImmutable.java", "CheckImmutableCheck3.java", 18);
	}

	@Test
	public void testCloneableWithoutClone() {
		assertFindbugs("CloneableWithoutClone.java", "CloneableWithoutCloneCheck1.java", 7);
	}

	@Test
	public void testCollectionRemoveAll1() {
		assertFindbugs("CollectionRemoveAll.java", "CollectionRemoveAllCheck1.java", 12);
	}

	@Test
	public void testCollectionRemoveAll2() {
		assertFindbugsNotMatch("CollectionRemoveAll.java", "CollectionRemoveAllCheck2.java");
	}

	@Test
	public void testCollectionRemoveAll3() {
		assertFindbugs("CollectionRemoveAll.java", "CollectionRemoveAllCheck3.java", 8);
	}

	@Test
	public void testCollectionRemoveAll4() {
		assertFindbugsNotMatch("CollectionRemoveAll.java", "CollectionRemoveAllCheck4.java");
	}
}
