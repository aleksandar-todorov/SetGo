package com.goodpeople.setgo.web.controllers;

import com.goodpeople.setgo.GlobalConstants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AccessController extends BaseController {

    private static final String UNAUTHORIZED = "/unauthorized";

    @GetMapping(UNAUTHORIZED)
    public ModelAndView unauthorized() {
        return super.view(GlobalConstants.ERROR + UNAUTHORIZED);
    }
}
