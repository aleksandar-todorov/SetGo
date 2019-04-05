package com.goodpeople.setgo.domain.models.view;

import com.goodpeople.setgo.domain.models.service.CategoryServiceModel;

import java.time.LocalDate;

public class GoalsListViewModel {

    private String id;
    private String name;
    private CategoryServiceModel category;
    private LocalDate beginOn;
    private LocalDate endOn;

    public GoalsListViewModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CategoryServiceModel getCategory() {
        return category;
    }

    public void setCategory(CategoryServiceModel category) {
        this.category = category;
    }

    public LocalDate getBeginOn() {
        return beginOn;
    }

    public void setBeginOn(LocalDate beginOn) {
        this.beginOn = beginOn;
    }

    public LocalDate getEndOn() {
        return endOn;
    }

    public void setEndOn(LocalDate endOn) {
        this.endOn = endOn;
    }
}
