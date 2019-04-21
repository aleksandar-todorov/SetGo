package com.goodpeople.setgo.web.controllers;

import com.goodpeople.setgo.GlobalConstants;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class HomeController extends BaseController {

    private static final String INDEX = "index";
    private static final String USERNAME = "username";
    private static final String HOME = "home";
    private static final String ABOUT = "about";
    private static final String ABOUT_WITH_SLASH = "/about";

    @GetMapping("/")
    public ModelAndView index() {
        return super.view(INDEX);
    }

    @GetMapping(GlobalConstants.HOME)
    @PreAuthorize("isAuthenticated()")
    public ModelAndView home(Principal principal, ModelAndView modelAndView) {
        modelAndView.addObject(USERNAME, principal.getName());

        return view(HOME, modelAndView);
    }

    @GetMapping(ABOUT_WITH_SLASH)
    public ModelAndView about() {
        return super.view(ABOUT);
    }
}

