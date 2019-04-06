package com.goodpeople.setgo.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Result was not found!")
public class ResultNotFoundException extends RuntimeException {

    public ResultNotFoundException() {
    }

    public ResultNotFoundException(String message) {
        super(message);
    }
}
