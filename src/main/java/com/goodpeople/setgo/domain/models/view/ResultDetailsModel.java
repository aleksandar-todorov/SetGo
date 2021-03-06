package com.goodpeople.setgo.domain.models.view;

public class ResultDetailsModel extends BaseViewModel {

    private String notes;
    private String conclusion;
    private int rate;
    private String suggestion;

    public ResultDetailsModel() {
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }
}
