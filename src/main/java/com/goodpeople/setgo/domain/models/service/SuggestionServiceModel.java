package com.goodpeople.setgo.domain.models.service;

public class SuggestionServiceModel extends BaseServiceModel {

    private CategoryServiceModel category;
    private int rate;
    private String proposal;

    public SuggestionServiceModel() {
    }

    public CategoryServiceModel getCategory() {
        return category;
    }

    public void setCategory(CategoryServiceModel category) {
        this.category = category;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getProposal() {
        return proposal;
    }

    public void setProposal(String proposal) {
        this.proposal = proposal;
    }
}
