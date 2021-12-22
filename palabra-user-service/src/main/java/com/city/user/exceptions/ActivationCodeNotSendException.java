package com.city.user.exceptions;

public class ActivationCodeNotSendException extends PalabraException {

    public ActivationCodeNotSendException() {
        super(PalabraErrorStatus.GENERIC_ERROR);
    }

    public ActivationCodeNotSendException(String s) {
        super(s, PalabraErrorStatus.GENERIC_ERROR);
    }
}
