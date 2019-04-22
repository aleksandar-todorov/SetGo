package com.goodpeople.setgo.service;

import com.goodpeople.setgo.domain.entities.User;
import com.goodpeople.setgo.domain.models.binding.UserLoginBindingModel;
import com.goodpeople.setgo.domain.models.binding.UserRegisterBindingModel;
import com.goodpeople.setgo.repository.RoleRepository;
import com.goodpeople.setgo.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserServiceUnitTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private ModelMapper modelMapper;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Before
    public void init() {
        this.modelMapper = new ModelMapper();
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    @Test
    public void userService_registerUserWithCorrectValues_ReturnsCorrect() {

        UserService userService = new UserServiceImpl(this.userRepository,
                this.modelMapper, this.bCryptPasswordEncoder, this.roleRepository);

        UserRegisterBindingModel model = new UserRegisterBindingModel();
        model.setUsername("User 1");
        model.setEmail("user@gmail.com");
        model.setPassword("userpass");
        model.setConfirmPassword("userpass");

        userService.registerUser(model);

        User expected = this.userRepository.findAll().get(0);

        Assert.assertEquals(model.getUsername(), expected.getUsername());
        Assert.assertEquals(model.getEmail(), expected.getEmail());
        Assert.assertEquals(4, expected.getAuthorities().size());

        UserRegisterBindingModel model2 = new UserRegisterBindingModel();
        model2.setUsername("User 2");
        model2.setEmail("user2@gmail.com");
        model2.setPassword("user2pass");
        model2.setConfirmPassword("user2pass");

        userService.registerUser(model2);

        User expected2 = this.userRepository.findAll().get(1);

        Assert.assertEquals(model2.getUsername(), expected2.getUsername());
        Assert.assertEquals(model2.getEmail(), expected2.getEmail());
        Assert.assertEquals(1, expected2.getAuthorities().size());
    }

    @Test
    public void userService_registerUserWithIncorrectValues_ReturnFalse() {
        UserService userService = new UserServiceImpl(this.userRepository,
                this.modelMapper, this.bCryptPasswordEncoder, this.roleRepository);

        UserRegisterBindingModel model = new UserRegisterBindingModel();
        model.setUsername("User 1");
        model.setEmail("user@gmail.com");
        model.setPassword("userpass");
        model.setConfirmPassword("differentpass");

        Assert.assertFalse(userService.registerUser(model));
    }

    @Test
    public void userService_loadUserByUsernameWithCorrectValues_ReturnsCorrect() {

        UserService userService = new UserServiceImpl(this.userRepository,
                this.modelMapper, this.bCryptPasswordEncoder, this.roleRepository);

        UserRegisterBindingModel model = new UserRegisterBindingModel();
        model.setUsername("User 1");
        model.setEmail("user@gmail.com");
        model.setPassword("userpass");
        model.setConfirmPassword("userpass");

        userService.registerUser(model);
        UserDetails user = userService.loadUserByUsername(model.getUsername());

        Assert.assertEquals(user.getUsername(), model.getUsername());
    }

    @Test(expected = UsernameNotFoundException.class)
    public void userService_loadUserByUsernameWithIncorrectValues_ThrowsException() {

        UserService userService = new UserServiceImpl(this.userRepository,
                this.modelMapper, this.bCryptPasswordEncoder, this.roleRepository);


        UserRegisterBindingModel model = new UserRegisterBindingModel();
        model.setUsername("User 1");
        model.setEmail("user@gmail.com");
        model.setPassword("userpass");
        model.setConfirmPassword("userpass");

        userService.registerUser(model);
        UserDetails user = userService.loadUserByUsername("NonExistingUsername");

    }

    @Test
    public void userService_loginUserWithCorrectValues_ReturnsTrue() {

        UserService userService = new UserServiceImpl(this.userRepository,
                this.modelMapper, this.bCryptPasswordEncoder, this.roleRepository);

        UserRegisterBindingModel model = new UserRegisterBindingModel();
        model.setUsername("User 1");
        model.setEmail("user@gmail.com");
        model.setPassword("userpass");
        model.setConfirmPassword("userpass");
        userService.registerUser(model);

        UserLoginBindingModel userLoginBindingModel = new UserLoginBindingModel();
        userLoginBindingModel.setUsername("User 1");
        userLoginBindingModel.setPassword(this.userRepository.findAll().get(0).getPassword());

        Assert.assertTrue(userService.loginUser(userLoginBindingModel));
    }

    @Test
    public void userService_loginUserWithIncorrectPassword_ReturnsFalse() {

        UserService userService = new UserServiceImpl(this.userRepository,
                this.modelMapper, this.bCryptPasswordEncoder, this.roleRepository);

        UserRegisterBindingModel model = new UserRegisterBindingModel();
        model.setUsername("User 1");
        model.setEmail("user@gmail.com");
        model.setPassword("userpass");
        model.setConfirmPassword("userpass");
        userService.registerUser(model);

        UserLoginBindingModel userLoginBindingModel = new UserLoginBindingModel();
        userLoginBindingModel.setUsername("User 1");
        userLoginBindingModel.setPassword("RandomPassword");

        Assert.assertFalse(userService.loginUser(userLoginBindingModel));
    }

    @Test(expected = UsernameNotFoundException.class)
    public void userService_loginUserWithIncorrectUsername_ThrowsException() {

        UserService userService = new UserServiceImpl(this.userRepository,
                this.modelMapper, this.bCryptPasswordEncoder, this.roleRepository);

        UserRegisterBindingModel model = new UserRegisterBindingModel();
        model.setUsername("User 1");
        model.setEmail("user@gmail.com");
        model.setPassword("userpass");
        model.setConfirmPassword("userpass");
        userService.registerUser(model);

        UserLoginBindingModel userLoginBindingModel = new UserLoginBindingModel();
        userLoginBindingModel.setUsername("RandomUsername");
        userLoginBindingModel.setPassword(this.userRepository.findAll().get(0).getPassword());

        Assert.assertFalse(userService.loginUser(userLoginBindingModel));
    }

    @Test
    public void userService_extractAllUsersWithCorrectValues_ReturnsCorrect() {

        UserService userService = new UserServiceImpl(this.userRepository,
                this.modelMapper, this.bCryptPasswordEncoder, this.roleRepository);

        UserRegisterBindingModel model = new UserRegisterBindingModel();
        model.setUsername("User 1");
        model.setEmail("user@gmail.com");
        model.setPassword("userpass");
        model.setConfirmPassword("userpass");

        userService.registerUser(model);

        UserRegisterBindingModel model2 = new UserRegisterBindingModel();
        model2.setUsername("User 2");
        model2.setEmail("user2@gmail.com");
        model2.setPassword("user2pass");
        model2.setConfirmPassword("user2pass");

        userService.registerUser(model2);

        Assert.assertEquals(2, userService.extractAllUsers().size());
    }

    @Test
    public void userService_extractAllUsersWithNoValues_ReturnsEmpty() {

        UserService userService = new UserServiceImpl(this.userRepository,
                this.modelMapper, this.bCryptPasswordEncoder, this.roleRepository);


        Assert.assertEquals(0, userService.extractAllUsers().size());
    }
}
