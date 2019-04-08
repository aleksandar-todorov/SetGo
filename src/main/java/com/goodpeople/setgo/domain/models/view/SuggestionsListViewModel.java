package com.goodpeople.setgo.domain.models.view;

import com.goodpeople.setgo.domain.models.service.CategoryServiceModel;

public class SuggestionsListViewModel extends BaseViewModel {

    private CategoryServiceModel category;
    private int rate;
    private String proposal;

    public SuggestionsListViewModel() {
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
