package com.goodpeople.setgo.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AccessController extends BaseController {

    private static final String UNAUTHORIZED = "/unauthorized";
    private static final String ERROR_UNAUTHORIZED = "error" + UNAUTHORIZED;

    @GetMapping(UNAUTHORIZED)
    public ModelAndView unauthorized() {
        return view(ERROR_UNAUTHORIZED);
    }
}
