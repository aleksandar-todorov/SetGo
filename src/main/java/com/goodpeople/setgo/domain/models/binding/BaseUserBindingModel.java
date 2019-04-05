package com.goodpeople.setgo.domain.models.binding;

public abstract class BaseUserBindingModel {
    private String username;
    private String password;

    public BaseUserBindingModel() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
