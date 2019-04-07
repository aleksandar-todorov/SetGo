package com.goodpeople.setgo.web.controllers;

import com.goodpeople.setgo.GlobalConstants;
import com.goodpeople.setgo.domain.models.binding.UserEditBindingModel;
import com.goodpeople.setgo.domain.models.view.UserViewModel;
import com.goodpeople.setgo.service.RoleService;
import com.goodpeople.setgo.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AdminController extends BaseController {

    private static final String HAS_ROLE_ADMIN = "hasRole('ROLE_ADMIN')";
    private static final String USERS = "/users";
    private static final String USERS_EDIT_USER = "users/edit-user";
    private static final String EDITBINDINGMODEL = "editBindingModel";
    private static final String ROLES = "roles";

    private UserService userService;
    private RoleService roleService;


    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(USERS)
    @PreAuthorize(HAS_ROLE_ADMIN)
    public ModelAndView listUsers(Principal principal, ModelAndView modelAndView) {
        List<UserViewModel> userViewModels = this.userService.extractAllUsers()
                .stream().filter(u -> !u.getUsername().equals(principal.getName())).collect(Collectors.toList());
        modelAndView.addObject("users", userViewModels);

        return view("users/show-users", modelAndView);
    }

    @GetMapping(USERS + GlobalConstants.EDIT_ID)
    @PreAuthorize(HAS_ROLE_ADMIN)
    public ModelAndView editUser(@PathVariable("id") String id, ModelAndView modelAndView) {
        UserEditBindingModel userBindingModel = this.userService.extractUserForEditById(id);

        if (userBindingModel.getRoleAuthorities().contains("ROOT")) {
            return redirect(USERS);
        }

        modelAndView.addObject(EDITBINDINGMODEL, userBindingModel);
        modelAndView.addObject(ROLES, this.roleService.extractAllRoles());

        return view(USERS_EDIT_USER, modelAndView);
    }

    @PostMapping(USERS + GlobalConstants.EDIT_ID)
    @PreAuthorize(HAS_ROLE_ADMIN)
    public ModelAndView editUserConfirm(@PathVariable("id") String id,
                                        @Valid @ModelAttribute(EDITBINDINGMODEL) UserEditBindingModel userEditBindingModel,
                                        BindingResult bindingResult, ModelAndView modelAndView) {
        if (bindingResult.hasErrors()) {
            modelAndView.addObject(ROLES, this.roleService.extractAllRoles());

            return view(USERS_EDIT_USER, modelAndView);
        }

        this.userService.insertEditedUser(userEditBindingModel);

        return redirect(USERS);
    }
}

