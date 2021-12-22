package com.city.user.exceptions;

import org.springframework.security.core.AuthenticationException;

public class InvalidTokenException extends AuthenticationException {
    private final PalabraErrorStatus errorStatus;

    public InvalidTokenException(String s) {
        super(s);
        this.errorStatus = PalabraErrorStatus.GENERIC_ERROR;
    }

    public InvalidTokenException(String msg, Throwable cause) {
        super(msg, cause);
        this.errorStatus = PalabraErrorStatus.GENERIC_ERROR;
    }

    public PalabraErrorStatus getErrorStatus() {
        return errorStatus;
    }
}
