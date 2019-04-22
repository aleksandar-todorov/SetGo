package com.goodpeople.setgo.service;

import com.goodpeople.setgo.domain.entities.Role;
import com.goodpeople.setgo.repository.RoleRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class RoleServiceUnitTests {

    @Autowired
    private RoleRepository roleRepository;

    private ModelMapper modelMapper;

    @Before
    public void init() {
        this.modelMapper = new ModelMapper();
    }

    @Test
    public void userService_extractAllRolesWithNoValues_ReturnsEmpty() {

        RoleService roleService = new RoleServiceImpl(this.roleRepository,this.modelMapper);

        Assert.assertEquals(0,roleService.extractAllRoles().size());
    }

    @Test
    public void userService_extractAllRolesWith1Role_Returns1() {

        RoleService roleService = new RoleServiceImpl(this.roleRepository,this.modelMapper);

        Role role = new Role();
        role.setAuthority("Authority");
        this.roleRepository.saveAndFlush(role);

        Assert.assertEquals(1,roleService.extractAllRoles().size());
    }

    @Test
    public void userService_extractAllRolesWith2Roles_Returns2() {

        RoleService roleService = new RoleServiceImpl(this.roleRepository,this.modelMapper);

        Role role = new Role();
        role.setAuthority("Authority");
        this.roleRepository.saveAndFlush(role);

        Role role2 = new Role();
        role2.setAuthority("Authority2");
        this.roleRepository.saveAndFlush(role2);

        Assert.assertEquals(2,roleService.extractAllRoles().size());
    }
}
