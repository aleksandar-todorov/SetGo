package com.goodpeople.setgo.domain.models.binding;

import javax.validation.constraints.NotNull;

public class CategoryBindingModel {

    private String id;
    private String name;

    public CategoryBindingModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @NotNull(message = "Pick a category")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
