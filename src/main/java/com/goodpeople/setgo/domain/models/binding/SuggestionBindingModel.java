package com.goodpeople.setgo.domain.models.binding;

import com.goodpeople.setgo.GlobalConstants;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SuggestionBindingModel extends BaseBindingModel {

    private final String CHOOSE_RATE = "Choose a rate(1-10)";
    private final String CREATE_PROPOSAL = "Create proposal(10-200 symbols)";

    private String category;
    private int rate;
    private String proposal;

    public SuggestionBindingModel() {
    }

    @NotNull(message = GlobalConstants.PICK_CATEGORY)
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @NotNull
    @Min(value = 1, message = CHOOSE_RATE )
    @Max(value = 10, message = CHOOSE_RATE )
    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    @NotNull
    @Size(min = 10, max = 200, message = CREATE_PROPOSAL)
    public String getProposal() {
        return proposal;
    }

    public void setProposal(String proposal) {
        this.proposal = proposal;
    }
}
