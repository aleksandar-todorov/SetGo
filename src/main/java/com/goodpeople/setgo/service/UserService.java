package com.goodpeople.setgo.service;

import com.goodpeople.setgo.domain.models.binding.UserEditBindingModel;
import com.goodpeople.setgo.domain.models.binding.UserLoginBindingModel;
import com.goodpeople.setgo.domain.models.binding.UserRegisterBindingModel;
import com.goodpeople.setgo.domain.models.view.UserViewModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    boolean registerUser(UserRegisterBindingModel userRegisterBindingModel);

    boolean loginUser(UserLoginBindingModel userLoginBindingModel);

    List<UserViewModel> extractAllUsers();

    UserEditBindingModel extractUserForEditById(String id);

    boolean insertEditedUser(UserEditBindingModel userEditBindingModel);
}
