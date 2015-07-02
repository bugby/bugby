package org.bugby.api;

import java.io.File;

/**
 * This is the exception thrown by the Generator.
 * 
 * @author <a href='mailto:ax.craciun@gmail.com'>Alexandru Craciun</a>
 * 
 */
public class BugbyException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private final SourcePosition sourcePosition;

	public BugbyException(SourcePosition sourcePosition, String message, Throwable cause) {
		super(message, cause);
		this.sourcePosition = sourcePosition;
	}

	public BugbyException(String message) {
		super(message);
		this.sourcePosition = new SourcePosition(new File("<unknown>"), 0, 0);
	}

	public BugbyException(String message, Throwable cause) {
		super(message, cause);
		this.sourcePosition = new SourcePosition(new File("<unknown>"), 0, 0);
	}

	public BugbyException(SourcePosition sourcePosition, String message) {
		super(message);
		this.sourcePosition = sourcePosition;
	}

	public BugbyException(SourcePosition sourcePosition, Throwable cause) {
		super(cause);
		this.sourcePosition = sourcePosition;
	}

	public SourcePosition getSourcePosition() {
		return sourcePosition;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append('(').append(sourcePosition.getFile().getName());
		if (sourcePosition != null) {
			sb.append(':').append(sourcePosition.getLine());
		}
		sb.append(')');

		return sb.toString();
	}
}
