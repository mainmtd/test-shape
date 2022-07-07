package com.shape.test_shape_backpl.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ExistingInformationException extends RuntimeException {
    public ExistingInformationException(String message){
        super(message);
    }
}
