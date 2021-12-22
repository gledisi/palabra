package com.city.user.exceptions;

public class EntityNotFoundException extends PalabraException {

    public EntityNotFoundException() {
        super(PalabraErrorStatus.ENTITY_NOT_FOUND);
    }

    public EntityNotFoundException(String s) {
        super(s, PalabraErrorStatus.ENTITY_NOT_FOUND);
    }
}
