package com.goodpeople.setgo.service;

import com.goodpeople.setgo.domain.entities.Role;
import com.goodpeople.setgo.domain.models.view.UserRoleViewModel;
import com.goodpeople.setgo.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private static final String ROOT = "ROOT";

    private RoleRepository roleRepository;
    private ModelMapper modelMapper;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<UserRoleViewModel> extractAllRoles() {
        List<Role> rolesFromDb = this.roleRepository.findAll();
        List<UserRoleViewModel> roleViewModels = new ArrayList<>();

        for (Role userRole : rolesFromDb) {
            if (userRole.getAuthority().equals(ROOT)) {
                continue;
            }
            UserRoleViewModel userRoleViewModel = this.modelMapper.map(userRole, UserRoleViewModel.class);

            roleViewModels.add(userRoleViewModel);
        }

        return roleViewModels;
    }
}

