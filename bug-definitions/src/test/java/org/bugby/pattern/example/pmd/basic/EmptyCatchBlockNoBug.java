package org.bugby.pattern.example.pmd.basic;

import java.io.FileInputStream;
import java.io.IOException;

public class EmptyCatchBlockNoBug {

	public void doSomething() {
		try {
			FileInputStream fis = new FileInputStream("/tmp/bugger");
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}
