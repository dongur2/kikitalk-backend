package com.doni.talk.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateRelationshipRequestException extends RuntimeException {
    public DuplicateRelationshipRequestException(String message) {
        super(message);
    }
}
