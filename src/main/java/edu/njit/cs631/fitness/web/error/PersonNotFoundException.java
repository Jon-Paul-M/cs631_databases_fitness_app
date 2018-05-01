package edu.njit.cs631.fitness.web.error;

public final class PersonNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PersonNotFoundException() {
        super();
    }

    public PersonNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public PersonNotFoundException(final String message) {
        super(message);
    }

    public PersonNotFoundException(final Throwable cause) {
        super(cause);
    }

}
