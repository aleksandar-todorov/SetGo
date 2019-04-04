package com.goodpeople.setgo.service;

import com.goodpeople.setgo.domain.models.view.UserRoleViewModel;

import java.util.List;

public interface RoleService {
    List<UserRoleViewModel> extractAllRoles();
}
