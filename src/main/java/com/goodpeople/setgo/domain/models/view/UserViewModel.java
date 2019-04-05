package com.goodpeople.setgo.domain.models.view;

public class UserViewModel extends BaseViewModel {

    private String username;
    private String email;
    private String roles;

    public UserViewModel() {
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

    public String getRoles() {
        return this.roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
}

