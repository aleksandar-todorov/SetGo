package com.goodpeople.setgo.domain.models.binding;

import com.goodpeople.setgo.web.annotations.FutureDate;
import com.goodpeople.setgo.web.annotations.PresentOrPastDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class GoalBindingModel extends BaseBindingModel {

    private String name;
    private String reason;
    private String category;
    private LocalDate beginOn;
    private LocalDate endOn;

    public GoalBindingModel() {
    }

    @NotNull
    @Size(min = 3, max = 30, message = "Pick an explanatory Name(3-30 symbols)")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    @Size(min = 15, max = 200, message = "Describe your reason(15-200 symbols)")
    public String getReason() {
        return this.reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @NotNull(message = "Pick a category")
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @NotNull(message = "Pick a starting date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @PresentOrPastDate
    public LocalDate getBeginOn() {
        return beginOn;
    }

    public void setBeginOn(LocalDate beginOn) {
        this.beginOn = beginOn;
    }

    @NotNull(message = "Pick a ending date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @FutureDate
    public LocalDate getEndOn() {
        return endOn;
    }

    public void setEndOn(LocalDate endOn) {
        this.endOn = endOn;
    }
}
