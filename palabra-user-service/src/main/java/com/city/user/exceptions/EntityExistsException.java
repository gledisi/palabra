package com.city.user.exceptions;

public class EntityExistsException extends PalabraException {

    public EntityExistsException() {
        super(PalabraErrorStatus.ENTITY_ALREADY_EXIST);
    }

    public EntityExistsException(String s) {
        super(s, PalabraErrorStatus.ENTITY_ALREADY_EXIST);
    }
}
