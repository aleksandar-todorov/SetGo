package com.goodpeople.setgo.domain.models.binding;

import javax.validation.constraints.NotNull;

public class CategoryBindingModel extends BaseBindingModel{

    private String name;

    public CategoryBindingModel() {
    }

    @NotNull(message = "Pick a category")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
