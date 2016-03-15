package org.bugby.matcher.javac.source;

import java.io.PrintWriter;

import com.google.common.base.Strings;

public class IndentPrinter {

	private int indentLevel;
	private String currentIndent;
	private final String indent;
	private PrintWriter out;
	private boolean newLine = false;

	public IndentPrinter() {
		this(new PrintWriter(System.out), "  ");
	}

	public IndentPrinter(PrintWriter out) {
		this(out, "  ");
	}

	public IndentPrinter(PrintWriter out, String indent) {
		this.out = out;
		this.indent = indent;
		this.currentIndent = "";
	}

	public void println(Object value) {
		println(value.toString());
	}

	public void println(String text) {
		print(text);
		out.println();
		newLine = true;
	}

	public void print(String text) {
		if (newLine) {
			out.print(currentIndent);
			newLine = false;
		}
		//TODO split here
		out.print(text);
	}

	public void printIndent() {
		for (int i = 0; i < indentLevel; i++) {
			out.print(indent);
		}
	}

	public void println() {
		out.println();
	}

	public void incrementIndent() {
		++indentLevel;
		buildIndent();
	}

	public void decrementIndent() {
		--indentLevel;
		buildIndent();
	}

	private void buildIndent() {
		currentIndent = Strings.repeat(indent, indentLevel);
	}

	public int getIndentLevel() {
		return indentLevel;
	}

	public void setIndentLevel(int indentLevel) {
		this.indentLevel = indentLevel;
	}

	public void flush() {
		out.flush();
	}
}