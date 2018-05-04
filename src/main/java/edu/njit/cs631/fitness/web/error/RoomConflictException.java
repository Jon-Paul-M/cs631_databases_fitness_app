package edu.njit.cs631.fitness.web.error;

public class RoomConflictException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RoomConflictException() {
		super();
	}

	public RoomConflictException(String message) {
		super(message);
	}

	public RoomConflictException(Throwable cause) {
		super(cause);
	}

	public RoomConflictException(String message, Throwable cause) {
		super(message, cause);
	}

	public RoomConflictException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	private Integer conflictingClassId;
	public Integer getConflictingClassId() {
		return conflictingClassId;
	}
	public void setConflictingClassId(Integer conflictingClassId) {
		this.conflictingClassId = conflictingClassId;
	}

}
