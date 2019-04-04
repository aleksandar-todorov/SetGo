package com.goodpeople.setgo.web.controllers;

import com.goodpeople.setgo.domain.models.binding.UserLoginBindingModel;
import com.goodpeople.setgo.domain.models.binding.UserRegisterBindingModel;
import com.goodpeople.setgo.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController extends BaseController{

    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/users/register")
    @PreAuthorize("isAnonymous()")
    public ModelAndView register(){
        return super.view("users/register");
    }

    @PostMapping("/users/register")
    public ModelAndView registerConfirm(@ModelAttribute(name = "model") UserRegisterBindingModel model){
        if(!model.getPassword().equals(model.getConfirmPassword())){
            return super.view("users/register");
        }
        this.userService.registerUser(this.modelMapper.map(model, UserRegisterBindingModel.class));
        return super.redirect("/users/login");
    }

    @GetMapping("/users/login")
    @PreAuthorize("isAnonymous()")
    public ModelAndView login(){
        return super.view("users/login");
    }

    @PostMapping("/users/login")
    @PreAuthorize("isAnonymous()")
    public ModelAndView loginConfirm(@ModelAttribute("userBindingModel") UserLoginBindingModel userLoginBindingModel) {
        if (!this.userService.loginUser(userLoginBindingModel)) {
            return super.view("users/login");
        }

        return super.redirect("/home");
    }

}

