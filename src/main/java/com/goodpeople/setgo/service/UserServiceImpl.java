package com.goodpeople.setgo.service;

import com.goodpeople.setgo.domain.entities.Role;
import com.goodpeople.setgo.domain.entities.User;
import com.goodpeople.setgo.domain.models.binding.UserEditBindingModel;
import com.goodpeople.setgo.domain.models.binding.UserLoginBindingModel;
import com.goodpeople.setgo.domain.models.binding.UserRegisterBindingModel;
import com.goodpeople.setgo.domain.models.view.UserViewModel;
import com.goodpeople.setgo.repository.RoleRepository;
import com.goodpeople.setgo.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, BCryptPasswordEncoder bCryptPasswordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public boolean registerUser(UserRegisterBindingModel userRegisterBindingModel) {
        if (!userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getConfirmPassword())) {
            return false;
        }

        User user = this.modelMapper.map(userRegisterBindingModel, User.class);
        user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));

        this.insertRoles();

        if (this.userRepository.count() == 0) {
            user.getAuthorities().add(this.roleRepository.findByAuthority("ROOT"));
            user.getAuthorities().add(this.roleRepository.findByAuthority("ADMIN"));
            user.getAuthorities().add(this.roleRepository.findByAuthority("MODERATOR"));
            user.getAuthorities().add(this.roleRepository.findByAuthority("USER"));
        } else {
            user.getAuthorities().add(this.roleRepository.findByAuthority("USER"));
        }

        this.userRepository.save(user);
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Username not found!");
        }

        return user;
    }

    @Override
    public boolean loginUser(UserLoginBindingModel userLoginBindingModel) {
        UserDetails userDetails = this.loadUserByUsername(userLoginBindingModel.getUsername());

        return userLoginBindingModel.getUsername().equals(userDetails.getUsername()) &&
                userLoginBindingModel.getPassword().equals(userDetails.getPassword());
    }

    @Override
    public List<UserViewModel> extractAllUsers() {
        List<User> usersFromDb = this.userRepository.findAll();
        List<UserViewModel> userViewModels = new ArrayList<>();

        for (User user : usersFromDb) {
            UserViewModel userViewModel = this.modelMapper.map(user, UserViewModel.class);
            userViewModel.setRoles(user.getAuthorities().stream().map(Role::getAuthority).collect(Collectors.joining(", ")));

            userViewModels.add(userViewModel);
        }

        return userViewModels;
    }

    @Override
    public UserEditBindingModel extractUserForEditById(String id) {
        User userFromDb = this.userRepository.findById(id).orElse(null);

        if (userFromDb == null) {
            throw new IllegalArgumentException("Non-existent user.");
        }

        UserEditBindingModel userBindingModel = this.modelMapper.map(userFromDb, UserEditBindingModel.class);

        for (Role userRole : userFromDb.getAuthorities()) {
            userBindingModel.getRoleAuthorities().add(userRole.getAuthority());
        }

        return userBindingModel;
    }

    @Override
    public boolean insertEditedUser(UserEditBindingModel userEditBindingModel) {
        User user = this.userRepository.findByUsername(userEditBindingModel.getUsername());
        user.getAuthorities().clear();

        if (userEditBindingModel.getRoleAuthorities().contains("ADMIN")) {
            user.getAuthorities().add(this.roleRepository.findByAuthority("USER"));
            user.getAuthorities().add(this.roleRepository.findByAuthority("MODERATOR"));
            user.getAuthorities().add(this.roleRepository.findByAuthority("ADMIN"));
        } else if (userEditBindingModel.getRoleAuthorities().contains("MODERATOR")) {
            user.getAuthorities().add(this.roleRepository.findByAuthority("USER"));
            user.getAuthorities().add(this.roleRepository.findByAuthority("MODERATOR"));
        } else {
            user.getAuthorities().add(this.roleRepository.findByAuthority("USER"));
        }

        this.userRepository.save(user);

        return true;
    }

    private void insertRoles() {
        if (this.roleRepository.count() == 0) {
            Role root = new Role();
            root.setAuthority("ROOT");
            Role admin = new Role();
            admin.setAuthority("ADMIN");
            Role moderator = new Role();
            moderator.setAuthority("MODERATOR");
            Role user = new Role();
            user.setAuthority("USER");

            this.roleRepository.save(root);
            this.roleRepository.save(admin);
            this.roleRepository.save(moderator);
            this.roleRepository.save(user);
        }
    }
}

