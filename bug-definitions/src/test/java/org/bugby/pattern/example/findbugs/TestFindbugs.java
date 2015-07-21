package org.bugby.pattern.example.findbugs;

import static org.bugby.pattern.example.MatchingHelper.assertBug;
import static org.bugby.pattern.example.MatchingHelper.assertNoBug;

import org.bugby.bugs.findbugs.AmbiguousInvocationOfOuterOrInner;
import org.bugby.bugs.findbugs.badpractice.CheckImmutable;
import org.bugby.bugs.findbugs.badpractice.CloneableWithoutClone;
import org.bugby.bugs.findbugs.badpractice.CollectionRemoveAll;
import org.junit.Test;

public class TestFindbugs {

	@Test
	public void testAmbiguousInvocationOfOuterOrInner2() {
		assertBug(AmbiguousInvocationOfOuterOrInner.class, AmbiguousInvocationOfOuterOrInnerCheck1.class, 18);
	}

	@Test
	public void testCheckImmutable1() {
		assertNoBug(CheckImmutable.class, CheckImmutableCheck1.class);
	}

	@Test
	public void testCheckImmutable2() {
		assertBug(CheckImmutable.class, CheckImmutableCheck2.class, 7);
	}

	@Test
	public void testCheckImmutable3() {
		assertNoBug(CheckImmutable.class, CheckImmutableCheck3.class);
	}

	@Test
	public void testCloneableWithoutCloneNoBug() {
		assertNoBug(CloneableWithoutClone.class, CloneableWithoutCloneCheck1.class);
	}

	@Test
	public void testCloneableWithoutCloneBug() {
		assertBug(CloneableWithoutClone.class, CloneableWithoutCloneCheck2.class, 3);
	}

	@Test
	public void testCloneableWithoutCloneNoBugAsNoCloneable() {
		assertNoBug(CloneableWithoutClone.class, CloneableWithoutCloneCheck3.class);
	}

	@Test
	public void testCollectionRemoveAll1() {
		assertBug(CollectionRemoveAll.class, CollectionRemoveAllCheck1.class, 12);
	}

	@Test
	public void testCollectionRemoveAll2() {
		assertNoBug(CollectionRemoveAll.class, CollectionRemoveAllCheck2.class);
	}

	@Test
	public void testCollectionRemoveAll3() {
		assertBug(CollectionRemoveAll.class, CollectionRemoveAllCheck3.class, 8);
	}

	@Test
	public void testCollectionRemoveAll4() {
		assertNoBug(CollectionRemoveAll.class, CollectionRemoveAllCheck4.class);
	}
}
