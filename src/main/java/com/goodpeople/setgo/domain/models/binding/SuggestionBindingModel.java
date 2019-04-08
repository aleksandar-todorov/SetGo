package com.goodpeople.setgo.domain.models.binding;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SuggestionBindingModel extends BaseBindingModel {

    private String category;
    private int rate;
    private String proposal;

    public SuggestionBindingModel() {
    }

    @NotNull(message = "Pick a category")
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @NotNull
    @Min(value = 1, message = "Choose a rate(1-10)" )
    @Max(value = 10, message = "Choose a rate(1-10)" )
    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    @NotNull
    @Size(min = 10, max = 200, message = "Create proposal(10-200 symbols)")
    public String getProposal() {
        return proposal;
    }

    public void setProposal(String proposal) {
        this.proposal = proposal;
    }
}
