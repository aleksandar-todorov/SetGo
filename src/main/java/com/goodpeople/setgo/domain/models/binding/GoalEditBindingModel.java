package com.goodpeople.setgo.domain.models.binding;

import com.goodpeople.setgo.GlobalConstants;
import com.goodpeople.setgo.web.annotations.FutureDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class GoalEditBindingModel extends BaseBindingModel {

    private String name;
    private String reason;
    private LocalDate beginOn;
    private LocalDate endOn;

    public GoalEditBindingModel() {
    }

    @NotNull
    @Size(min = 3, max = 30, message = GlobalConstants.PICK_EXPLANATORY_NAME)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    @Size(min = 15, max = 200, message = GlobalConstants.DESCRIBE_REASON)
    public String getReason() {
        return this.reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate getBeginOn() {
        return beginOn;
    }

    public void setBeginOn(LocalDate beginOn) {
        this.beginOn = beginOn;
    }

    @NotNull(message = GlobalConstants.PICK_ENDING_DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @FutureDate
    public LocalDate getEndOn() {
        return endOn;
    }

    public void setEndOn(LocalDate endOn) {
        this.endOn = endOn;
    }
}

