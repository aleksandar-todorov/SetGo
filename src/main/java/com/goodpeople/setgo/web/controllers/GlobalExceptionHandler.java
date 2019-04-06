package com.goodpeople.setgo.web.controllers;

import com.goodpeople.setgo.error.GoalNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler({GoalNotFoundException.class})
    public ModelAndView handleGoalNotFound(Throwable e){
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("message", e.getMessage());

        return modelAndView;
    }

    @ExceptionHandler({Throwable.class})
    public ModelAndView handleDbExceptions(Throwable e) {
        ModelAndView modelAndView = new ModelAndView("error");

        Throwable throwable = e;
        while (throwable.getCause() != null){
            throwable = throwable.getCause();
        }

        modelAndView.addObject("message", throwable.getMessage());

        return modelAndView;
    }

}
