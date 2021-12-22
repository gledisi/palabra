package com.city.user.exceptions;

public class InvalidLoginException extends PalabraException {

    private static final long serialVersionUID = 1L;

    public InvalidLoginException() {
        super(PalabraErrorStatus.ACCESS_DENIED);
    }

    public InvalidLoginException(String s) {
        super(s, PalabraErrorStatus.ACCESS_DENIED);
    }
}
