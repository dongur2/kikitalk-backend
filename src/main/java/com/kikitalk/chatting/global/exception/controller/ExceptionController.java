package com.kikitalk.chatting.global.exception.controller;

import com.kikitalk.chatting.global.response.ExceptionResponse;
import com.kikitalk.chatting.user.exception.DuplicateRelationshipRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;


@Slf4j @ControllerAdvice
public class ExceptionController {

    // 400 BAD_REQUEST
    @ExceptionHandler({ MethodArgumentNotValidException.class })
    public ExceptionResponse handleValidationBadRequestException(final MethodArgumentNotValidException ex) {
        String errorFieldName = ex.getBindingResult().getFieldErrors().get(0).getField();
        String errorMessage = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();

        log.warn("[ERROR] 유효성 검사에 실패했습니다. 필드: {}, 메세지: {}", errorFieldName, errorMessage);

        return ExceptionResponse.of(HttpStatus.BAD_REQUEST, LocalDateTime.now(), errorMessage);
    }

    @ExceptionHandler({ RuntimeException.class })
    public ExceptionResponse handleBadRequestException(final RuntimeException ex) {
        log.warn("[ERROR] ", ex);
        return ExceptionResponse.of(HttpStatus.BAD_REQUEST, LocalDateTime.now(), ex.getMessage());
    }

    // 401 UNAUTHORIZED
    @ExceptionHandler({ AuthenticationException.class })
    public ExceptionResponse handleAuthenticationException(final AuthenticationException ex) {
        log.warn("[ERROR] ", ex);
        return ExceptionResponse.of(HttpStatus.UNAUTHORIZED, LocalDateTime.now(), ex.getMessage());
    }

    // 403 FORBIDDEN
    @ExceptionHandler({ AccessDeniedException.class })
    public ExceptionResponse handleAccessDeniedException(final AccessDeniedException ex) {
        log.warn("[ERROR] ", ex);
        return ExceptionResponse.of(HttpStatus.FORBIDDEN, LocalDateTime.now(), ex.getMessage());
    }

    // 404 NOT_FOUND
    @ExceptionHandler({ NullPointerException.class })
    public ExceptionResponse handleNullPointerException(final NullPointerException ex) {
        log.warn("[ERROR] ", ex);
        return ExceptionResponse.of(HttpStatus.NOT_FOUND, LocalDateTime.now(), ex.getMessage());
    }

    // 409 CONFLICT
    @ExceptionHandler({ DuplicateRelationshipRequestException.class })
    public ExceptionResponse handleNullPointerException(final DuplicateRelationshipRequestException ex) {
        log.warn("[ERROR] ", ex);
        return ExceptionResponse.of(HttpStatus.CONFLICT, LocalDateTime.now(), ex.getMessage());
    }

    // 500 SERVER_ERROR
    @ExceptionHandler({ Exception.class })
    public ExceptionResponse handleAll(final Exception ex) {
        log.warn("{} - [ERROR] {}", ex.getClass().getName(), ex.getMessage());
        return ExceptionResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, LocalDateTime.now(), ex.getMessage());
    }
}
