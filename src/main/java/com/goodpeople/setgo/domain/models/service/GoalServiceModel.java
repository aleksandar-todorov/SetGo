package com.goodpeople.setgo.domain.models.service;

import com.goodpeople.setgo.domain.entities.Category;

import java.time.LocalDate;

public class GoalServiceModel {
    private String id;
    private String name;
    private String reason;
    private CategoryServiceModel category;
    private LocalDate beginOn;
    private LocalDate endOn;


    public GoalServiceModel() {
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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
