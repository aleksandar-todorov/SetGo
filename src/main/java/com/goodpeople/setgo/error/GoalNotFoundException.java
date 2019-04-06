package com.goodpeople.setgo.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Goal was not found!")
public class GoalNotFoundException extends RuntimeException {

    public GoalNotFoundException() {
    }

    public GoalNotFoundException(String message) {
        super(message);
    }
}
