package com.goodpeople.setgo.web.controllers;

import com.goodpeople.setgo.GlobalConstants;
import com.goodpeople.setgo.error.GoalNotFoundException;
import com.goodpeople.setgo.error.ResultNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({GoalNotFoundException.class})
    public ModelAndView handleGoalNotFound(Throwable e) {
        ModelAndView modelAndView = new ModelAndView(GlobalConstants.ERROR);
        modelAndView.addObject(GlobalConstants.MESSAGE, e.getMessage());

        return modelAndView;
    }

    @ExceptionHandler({ResultNotFoundException.class})
    public ModelAndView handleResultNotFound(Throwable e) {
        ModelAndView modelAndView = new ModelAndView(GlobalConstants.ERROR);
        modelAndView.addObject(GlobalConstants.MESSAGE, e.getMessage());

        return modelAndView;
    }


    @ExceptionHandler({Throwable.class})
    public ModelAndView handleDbExceptions(Throwable e) {
        ModelAndView modelAndView = new ModelAndView(GlobalConstants.ERROR);

        Throwable throwable = e;
        while (throwable.getCause() != null) {
            throwable = throwable.getCause();
        }

        modelAndView.addObject(GlobalConstants.MESSAGE, throwable.getMessage());

        return modelAndView;
    }

}
