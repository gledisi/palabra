package com.palabra.user.exceptions;

public class EntityNotFoundException extends RuntimeException{

    public EntityNotFoundException() {
    }

    public EntityNotFoundException(String s) {
        super(s);
    }
}
