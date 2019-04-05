package com.goodpeople.setgo.domain.entities;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "goals")
public class Goal extends BaseEntity {

    private String name;
    private String reason;
    private Category category;
    private LocalDate beginOn;
    private LocalDate endOn;

    public Goal() {
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "reason", nullable = false)
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @ManyToOne(targetEntity = Category.class)
    @JoinColumn(
            name = "category_id",
            referencedColumnName = "id",
            nullable = false
    )
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Column(name = "begin_on", nullable = false)
    public LocalDate getBeginOn() {
        return beginOn;
    }

    public void setBeginOn(LocalDate beginOn) {
        this.beginOn = beginOn;
    }

    @Column(name = "end_on", nullable = false)
    public LocalDate getEndOn() {
        return endOn;
    }

    public void setEndOn(LocalDate endOn) {
        this.endOn = endOn;
    }
}


