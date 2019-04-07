package com.goodpeople.setgo.web.controllers;

import com.goodpeople.setgo.GlobalConstants;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class HomeController extends BaseController {

    @GetMapping("/")
    public ModelAndView index() {
        return super.view("index");
    }

    @GetMapping(GlobalConstants.HOME)
    @PreAuthorize("isAuthenticated()")
    public ModelAndView home(Principal principal, ModelAndView modelAndView) {
        modelAndView.addObject("username", principal.getName());

        return super.view("home", modelAndView);
    }

    @GetMapping("/about")
    public ModelAndView about() {
        return super.view("about");
    }
}

