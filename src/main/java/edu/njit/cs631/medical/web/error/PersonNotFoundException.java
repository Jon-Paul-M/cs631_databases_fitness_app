package edu.njit.cs631.medical.web.error;

public final class PersonNotFoundException extends RuntimeException {

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
