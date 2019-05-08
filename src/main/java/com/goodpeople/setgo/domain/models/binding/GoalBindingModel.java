package com.goodpeople.setgo.domain.models.binding;

import com.goodpeople.setgo.GlobalConstants;
import com.goodpeople.setgo.web.annotations.FutureDate;
import com.goodpeople.setgo.web.annotations.PresentOrPastDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class GoalBindingModel extends BaseBindingModel {

    private final String PICK_STARTING_DATE = "Pick a starting date";
    private final String DATE_PATTERN = "yyyy-MM-dd";


    private String name;
    private String reason;
    private String category;
    private LocalDate beginOn;
    private LocalDate endOn;

    public GoalBindingModel() {
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

    @NotNull(message = GlobalConstants.PICK_CATEGORY)
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @NotNull(message = PICK_STARTING_DATE)
    @DateTimeFormat(pattern = DATE_PATTERN)
    @PresentOrPastDate
    public LocalDate getBeginOn() {
        return beginOn;
    }

    public void setBeginOn(LocalDate beginOn) {
        this.beginOn = beginOn;
    }

    @NotNull(message = GlobalConstants.PICK_ENDING_DATE)
    @DateTimeFormat(pattern = DATE_PATTERN)
    @FutureDate
    public LocalDate getEndOn() {
        return endOn;
    }

    public void setEndOn(LocalDate endOn) {
        this.endOn = endOn;
    }
}
