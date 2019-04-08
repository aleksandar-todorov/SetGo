package com.goodpeople.setgo.domain.entities;

import javax.persistence.*;

@Entity
@Table(name = "suggestions")
public class Suggestion extends BaseEntity {

    private Category category;
    private int rate;
    private String proposal;

    public Suggestion() {
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

    @Column(name = "rate", nullable = false)
    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    @Column(name = "proposal", nullable = false)
    public String getProposal() {
        return proposal;
    }

    public void setProposal(String proposal) {
        this.proposal = proposal;
    }
}
