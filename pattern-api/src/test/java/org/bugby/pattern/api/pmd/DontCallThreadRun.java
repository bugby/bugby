package org.bugby.pattern.api.pmd;

import static org.bugby.pattern.api.matcher.content.Matchers.specificClass;
import static org.bugby.pattern.api.matcher.content.Matchers.string;

import java.util.Collections;
import java.util.List;

import javax.lang.model.type.TypeMirror;

import junit.framework.Assert;

import org.bugby.pattern.api.BugDefinition;
import org.bugby.pattern.api.CodePattern;
import org.bugby.pattern.api.matcher.content.Matchers;
import org.bugby.pattern.api.matcher.structure.ChildDefinition;
import org.bugby.pattern.api.model.Annotation;
import org.bugby.pattern.api.model.Expr;
import org.bugby.pattern.api.model.MethodInvExpr;
import org.junit.Test;

public class DontCallThreadRun {

	@Test
	public void testCallTrhreadRun() {

		//TODO why is it that type inference is unable to infer there generic types?
		BugDefinition bugDefinition = new BugDefinition(//
				new CodePattern(//
						//Thread.run()
						new MethodInvExpr(specificClass(Thread.class),//
								Matchers.<List<Annotation>> any(),//
								Matchers.<List<TypeMirror>> any(),//
								Matchers.<List<List<Annotation>>> any(),//
								string("run"),//
								ChildDefinition.<Expr> anyChild(),//
								Collections.<ChildDefinition<Expr>> emptyList())),//
				Collections.<CodePattern> emptyList(),//
				Collections.<CodePattern> emptyList()//
		);

		//just testing if building the pattern is possible, and how
		Assert.assertNotNull(bugDefinition);
	}
}
