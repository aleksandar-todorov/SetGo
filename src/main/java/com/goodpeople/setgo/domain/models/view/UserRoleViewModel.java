package com.goodpeople.setgo.domain.models.view;

public class UserRoleViewModel extends BaseViewModel {

    private String authority;

    public UserRoleViewModel() {
    }

    public String getAuthority() {
        return this.authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}


