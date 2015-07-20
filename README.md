# BUGBY

BUGBY (from "*Bug by* example") is a new approach to static code analysis for Java.
Static code analysis has been arround for several years now.
Tools like [Findbugs](http://findbugs.sourceforge.net) or [PMD](http://pmd.sourceforge.net/) have been around for years and they found
their important audiance within the Java developers community.

Findbugs has around 400 bug definitions and PMD around 350. So it's like saying that out of the infinite possibilities to write Java code,
out of the billions of billions of lines of Java code written around the world, only less than 800 combinations exist that can lead to bugs,
all the rest are perfectly safe!

My believe is that the number of bug definitions **must be much, much higher** than 800.
It's true that not everybody uses tools for static code analysis (I don't have a real statistics here).
But the probable reason that the number of existing bug definitions is so low is that is quite **hard to write new rules** using this tools.

The teams should invest in these rules like they do in unit tests. But the cool thing about these rule is that once they apply to a project, they actually apply to **all** the projects written with the same libraries. So it's like writing unit tests for **future code** (even more powerful than TDD!)

Have a look for example at this [Findbugs definition source](http://code.google.com/p/findbugs/source/browse/findbugs/src/java/edu/umd/cs/findbugs/detect/FindPuzzlers.java) or this [PMD definition source](https://github.com/pmd/pmd/blob/master/pmd-java/src/main/java/net/sourceforge/pmd/lang/java/rule/basic/DoubleCheckedLockingRule.java).
Of course there are shorter definitions. PMD even tried to introduce a XPATH-like language definition, that alse becomes quite complex for more difficult rules.

One more problem is that is quite confusing if you need both Findbugs or PMD (as their scope sometimes overlaps) or in which situation you should use one or this other. But in fact is quite easy to figure this out if you know how the two tools do thier job.
Findbugs uses bytecode access, so it can detect bugs when more classes are involved. But is very hard to write complex rules.
PMD uses the code's AST, but it's harder to detect bugs involving more than one class.

Bugby uses Java code itself to define the patterns, almost like copying and pasting the code that contains the bug's definition.
This way you can define very complex patterns without learning a new language, nor having to play with the hard-to-understand binary code API used by Findbugs.
What you need to learn is a dozen of annotations and a dozen of constructions that will help you to create powerful patterns that will not yield false positives.
In case the available *wildcards* are not enough, more advanced user can still create new wildcards using the standard [Java Compiler Tree API](http://docs.oracle.com/javase/7/docs/jdk/api/javac/tree/index.html).

## Some Examples

Here is an example that corresponds to [PMD: Don't call thread run](). This pattern also detects cases when the threads comes from a method call or from a collection or a field, which the PMD rule does not.

```java
@Pattern
public class DontCallThreadRun {
	public void someCode() {
		someTypedValue(Thread.class).run();
	}
}
```

Here is another one where in PMD the XPath language is no longer enough:

```java
@Pattern
public class DoubleCheckedLocking {
	SomeType field;

	public SomeType method() {
		if (field == null) { // someField may be non-null yet not fully created
			synchronized (this) {
				if (field == null) {
					field = someValue();
				}
			}
		}
		return field;
	}
}
```

Here is a rule that correspond to the Findbugs rule EQ_COMPARETO_USE_OBJECT_EQUALS:

```java
@Pattern
public class CompareToUseObjectEquals implements Comparable<Object> {
	@Override
	public int compareTo(Object obj) {
		return someIntValue();
	}

	@Override
	@Missing
	public boolean equals(Object obj) {
		return someBooleanValue();
	}
}
```


## The Plan
Here's the list of what Bugby intends to offer:

* Rich wildcard and pattern API
* The definition of more than 90% of existing Findbugs and PMD rules written using the API
* A Maven plugin to easily execute the rules on an existing project
* A strategy to easily provide rules with a Maven library

The idea is to provide all this by the **end of 2015**.

Once this is done, you should normally safely replace your Findbugs and PMD rules.
And then start adding new rules like creating future-proof unit tests.

Read below details about these ideas.


## Adding rules using Maven dependency mechanism
Some of the rules provided by PMD or Findbugs refer to JUnit for example.
If you don't use JUnit, what's the point of running those rules?

One goal is to easly include rules defined by third-party, like any other artifacts.

The idea is to provide a way to have rules specific to a Maven library that will detect at compile time misusage of this library.
When compiling a project, the Bugby Maven plugin will put together all the default rules and also the rules coming from used libraries.


## Contact
If you believe this project may fit your current or future needs drop me an email at ax.craciun@gmail.com
You're more than welcome if you can help with the development of this tool!
















