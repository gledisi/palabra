package com.city.user.exceptions;

import com.city.user.dto.ApiErrorResponse;

public class PalabraException extends RuntimeException{

    private final PalabraErrorStatus errorStatus;

    public PalabraException() {
        this.errorStatus = PalabraErrorStatus.GENERIC_ERROR;
    }

    public PalabraException(String s) {
        super(s);
        this.errorStatus = PalabraErrorStatus.GENERIC_ERROR;
    }

    public PalabraException(PalabraErrorStatus errorStatus) {
        this.errorStatus = errorStatus;
    }

    public PalabraException(String s, PalabraErrorStatus errorStatus) {
        super(s);
        this.errorStatus = errorStatus;
    }

    public PalabraErrorStatus getErrorStatus() {
        return errorStatus;
    }
}
