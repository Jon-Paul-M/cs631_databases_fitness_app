package edu.njit.cs631.fitness.web.error;

public class ClassConflictException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ClassConflictException() {
        super();
    }

    public ClassConflictException(String message) {
        super(message);
    }

    public ClassConflictException(Throwable cause) {
        super(cause);
    }

    public ClassConflictException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClassConflictException(String message, Throwable cause, boolean enableSuppression,
                                       boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
