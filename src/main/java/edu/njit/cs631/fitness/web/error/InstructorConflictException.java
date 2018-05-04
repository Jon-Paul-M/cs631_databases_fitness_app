package edu.njit.cs631.fitness.web.error;

public class InstructorConflictException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InstructorConflictException() {
		super();
	}

	public InstructorConflictException(String message) {
		super(message);
	}

	public InstructorConflictException(Throwable cause) {
		super(cause);
	}

	public InstructorConflictException(String message, Throwable cause) {
		super(message, cause);
	}

	public InstructorConflictException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
