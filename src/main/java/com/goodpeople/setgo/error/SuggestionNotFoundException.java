package com.goodpeople.setgo.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Suggestion was not found!")
public class SuggestionNotFoundException extends RuntimeException {

    public SuggestionNotFoundException() {
    }

    public SuggestionNotFoundException(String message) {
        super(message);
    }
}
