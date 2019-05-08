package com.goodpeople.setgo.domain.models.binding;

import com.goodpeople.setgo.GlobalConstants;

import javax.validation.constraints.NotNull;

public class CategoryBindingModel extends BaseBindingModel{

    private String name;

    public CategoryBindingModel() {
    }

    @NotNull(message = GlobalConstants.PICK_CATEGORY)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
