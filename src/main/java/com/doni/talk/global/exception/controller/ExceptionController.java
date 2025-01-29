package com.doni.talk.global.exception.controller;

import com.doni.talk.user.exception.DuplicateRelationshipRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@Slf4j @ControllerAdvice
public class ExceptionController {

    // 400 BAD_REQUEST
    @ExceptionHandler({ MethodArgumentNotValidException.class })
    public ResponseEntity<Object> handleValidationBadRequestException(final MethodArgumentNotValidException ex) {
        log.warn("[ERROR] ", ex);
        return ResponseEntity.badRequest().body(ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage());
    }
    @ExceptionHandler({ RuntimeException.class })
    public ResponseEntity<Object> handleBadRequestException(final RuntimeException ex) {
        log.warn("[ERROR] ", ex);
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    // 401 UNAUTHORIZED
    @ExceptionHandler({ AuthenticationException.class })
    public ResponseEntity handleAuthenticationException(final AuthenticationException ex) {
        log.warn("[ERROR] ", ex);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    // 403 FORBIDDEN
    @ExceptionHandler({ AccessDeniedException.class })
    public ResponseEntity handleAccessDeniedException(final AccessDeniedException ex) {
        log.warn("[ERROR] ", ex);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }

    // 404 NOT_FOUND
    @ExceptionHandler({ NullPointerException.class })
    public ResponseEntity handleNullPointerException(final NullPointerException ex) {
        log.warn("[ERROR] ", ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    // 409 CONFLICT
    @ExceptionHandler({ DuplicateRelationshipRequestException.class })
    public ResponseEntity handleNullPointerException(final DuplicateRelationshipRequestException ex) {
        log.warn("[ERROR] ", ex);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    // 500 SERVER_ERROR
    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleAll(final Exception ex) {
        log.info(ex.getClass().getName());
        log.warn("[ERROR] ", ex);
        return ResponseEntity.internalServerError().body(ex.getMessage());
    }

}
