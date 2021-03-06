package com.goodpeople.setgo.web.controllers;

import com.goodpeople.setgo.GlobalConstants;
import com.goodpeople.setgo.domain.models.binding.GoalBindingModel;
import com.goodpeople.setgo.domain.models.binding.UserLoginBindingModel;
import com.goodpeople.setgo.domain.models.binding.UserRegisterBindingModel;
import com.goodpeople.setgo.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class UserController extends BaseController{

    private static final String USERS_REGISTER = "users/register";
    private static final String USERS_LOGIN = "users/login";
    private static final String USER_BINDING_MODEL = "userBindingModel";
    private static final String MODEL = "model";

    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/" + USERS_REGISTER)
    @PreAuthorize("isAnonymous()")
    public ModelAndView register(ModelAndView modelAndView, @ModelAttribute(name = MODEL) UserRegisterBindingModel model){
        modelAndView.addObject(MODEL, model);
        return view(USERS_REGISTER, modelAndView);
    }

    @PostMapping("/" + USERS_REGISTER)
    public ModelAndView registerConfirm(ModelAndView modelAndView, @Valid @ModelAttribute(name = MODEL) UserRegisterBindingModel model, BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            modelAndView.addObject(MODEL, model);
            return view(USERS_REGISTER, modelAndView);
        }

        if(!model.getPassword().equals(model.getConfirmPassword())){
            return view(USERS_REGISTER);
        }
        this.userService.registerUser(this.modelMapper.map(model, UserRegisterBindingModel.class));
        return redirect("/" + USERS_LOGIN);
    }

    @GetMapping("/" + USERS_LOGIN)
    @PreAuthorize("isAnonymous()")
    public ModelAndView login(){
        return view(USERS_LOGIN);
    }

    @PostMapping("/" + USERS_LOGIN)
    @PreAuthorize("isAnonymous()")
    public ModelAndView loginConfirm(@ModelAttribute(USER_BINDING_MODEL) UserLoginBindingModel userLoginBindingModel) {
        if (!this.userService.loginUser(userLoginBindingModel)) {
            return view(USERS_LOGIN);
        }

        return redirect(GlobalConstants.HOME);
    }
}

