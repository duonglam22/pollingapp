package com.vnpt.polling.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException{

    public BadRequestException(String message) {
        super(message);
        System.out.println("have an exception bad request: " + message);

    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
        System.out.println("have an exception bad request: " + message + "; cause: " + cause);

    }
}
