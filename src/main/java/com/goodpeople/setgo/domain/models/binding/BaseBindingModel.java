package com.goodpeople.setgo.domain.models.binding;

public abstract class BaseBindingModel {

    private String id;

    public BaseBindingModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
