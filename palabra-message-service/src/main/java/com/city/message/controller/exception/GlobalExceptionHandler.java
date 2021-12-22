package com.city.message.controller.exception;

import com.city.user.dto.ApiErrorResponse;
import com.city.user.exceptions.EntityExistsException;
import com.city.user.exceptions.EntityNotFoundException;
import com.city.user.exceptions.PalabraErrorStatus;
import com.city.user.exceptions.PalabraException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(PalabraException.class)
    public ResponseEntity<ApiErrorResponse> handleAppException(WebRequest request, Exception ex) {
        PalabraException palabraException = (PalabraException) ex;
        logErrorException(palabraException);
        return buildResponse(palabraException.getErrorStatus(), palabraException.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNotFoundException(WebRequest request, Exception ex) {
        EntityNotFoundException exception = (EntityNotFoundException) ex;
        logErrorException(exception);
        return buildResponse(exception.getErrorStatus(), exception.getMessage());
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<ApiErrorResponse> handleExistsException(WebRequest request, Exception ex) {
        EntityExistsException exception = (EntityExistsException) ex;
        logErrorException(exception);
        return buildResponse(exception.getErrorStatus(), exception.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiErrorResponse> handleAccessDeniedException(WebRequest request, Exception ex) {
        AccessDeniedException accessDeniedException = (AccessDeniedException) ex;
        logErrorException(accessDeniedException);
        return buildResponse(HttpStatus.UNAUTHORIZED, accessDeniedException.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiErrorResponse> handleAuthenticationException(WebRequest request, Exception ex) {
        AuthenticationException authenticationException = (AuthenticationException) ex;
        logErrorException(authenticationException);
        return buildResponse(HttpStatus.UNAUTHORIZED, authenticationException.getMessage());
    }


    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ApiErrorResponse> handleEmptyResultDataAccessException(WebRequest request, Exception ex) {
        EmptyResultDataAccessException emptyResultDataAccessException = (EmptyResultDataAccessException) ex;
        logErrorException(emptyResultDataAccessException);
        return buildResponse(HttpStatus.NOT_FOUND, emptyResultDataAccessException.getMessage());
    }

    private ResponseEntity<ApiErrorResponse> buildResponse(HttpStatus httpStatus, String message) {
        ApiErrorResponse response = new ApiErrorResponse(httpStatus, Collections.singletonList(message));
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    private ResponseEntity<ApiErrorResponse> buildResponse(PalabraErrorStatus errorStatus, List<String> messages) {
        ApiErrorResponse response = new ApiErrorResponse(errorStatus, messages);
        return ResponseEntity.status(errorStatus.getStatus()).body(response);
    }

    private ResponseEntity<ApiErrorResponse> buildResponse(PalabraErrorStatus errorStatus, String message) {
        ApiErrorResponse response = new ApiErrorResponse(errorStatus, Collections.singletonList(message));
        return ResponseEntity.status(errorStatus.getStatus()).body(response);
    }

    private void logErrorException(Exception exception) {
        log.info(exception.getClass().getName() + " Occurred::", exception);
        log.error("Message " + exception.getMessage() + Arrays.toString(exception.getStackTrace()));
    }
}