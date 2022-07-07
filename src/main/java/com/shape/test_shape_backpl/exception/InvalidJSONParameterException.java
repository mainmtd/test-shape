package com.shape.test_shape_backpl.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidJSONParameterException extends RuntimeException{
    public InvalidJSONParameterException(String message){
        super(message);
    }
}
