package com.goodpeople.setgo.domain.models.binding;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class UserEditBindingModel {

    private String id;
    private String username;
    private String email;
    private List<String> roleAuthorities;

    public UserEditBindingModel() {
        this.roleAuthorities = new ArrayList<>();
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NotNull
    @Size(min = 1)
    public List<String> getRoleAuthorities() {
        return this.roleAuthorities;
    }

    public void setRoleAuthorities(List<String> roleAuthorities) {
        this.roleAuthorities = roleAuthorities;
    }
}


