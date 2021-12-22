package com.city.user.exceptions;

import org.springframework.http.HttpStatus;

public enum PalabraErrorStatus {

    GENERIC_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error!"),
    ACCESS_DENIED(HttpStatus.UNAUTHORIZED, "Access denied error!"),
    ILLEGAL_ARGUMENT(HttpStatus.BAD_REQUEST, "Illegal argument error!"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "Unauthorized operation error!"),
    NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "Method not allowed error"),
    ENTITY_ALREADY_EXIST(HttpStatus.CONFLICT, "Entity already exists error!"),
    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "Entity not found error!"),
    MATCHING_NOT_FOUND(HttpStatus.NOT_FOUND, "Matching not found error!"),
    DELETE_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "Delete not allowed error!"),
    UPDATE_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "Update not allowed error!");

    private final HttpStatus status;
    private final String error;

    PalabraErrorStatus(HttpStatus httpstatus, String error) {
        this.status = httpstatus;
        this.error = error;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }
}