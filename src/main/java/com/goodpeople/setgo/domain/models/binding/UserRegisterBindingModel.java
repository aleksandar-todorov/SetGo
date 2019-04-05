package com.goodpeople.setgo.domain.models.binding;

public class UserRegisterBindingModel extends BaseUserBindingModel {

    private String confirmPassword;
    private String email;

    public UserRegisterBindingModel() {
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

