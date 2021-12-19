package com.palabra.user.exceptions;

import org.springframework.security.core.AuthenticationException;

public class InvalidTokenException extends AuthenticationException {
    public InvalidTokenException(String s) {
        super(s);
    }

    public InvalidTokenException(String message, Throwable cause) {
        super(message, cause);
    }
}
