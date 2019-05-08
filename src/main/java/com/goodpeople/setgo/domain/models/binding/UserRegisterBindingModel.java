package com.goodpeople.setgo.domain.models.binding;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserRegisterBindingModel {

    private final String PICK_USERNAME = "Pick an Username(3-30 symbols)";
    private final String CHOOSE_PASSWORD = "Choose a Password(3-30 symbols)";
    private final String PICK_EMAIL = "Pick an Email(3-30 symbols)";

    private String username;
    private String password;
    private String confirmPassword;
    private String email;

    public UserRegisterBindingModel() {
    }

    @NotNull
    @Size(min = 3, max = 30, message = PICK_USERNAME)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotNull
    @Size(min = 3, max = 30, message = CHOOSE_PASSWORD)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @NotNull
    @Size(min = 3, max = 30, message = PICK_EMAIL)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

