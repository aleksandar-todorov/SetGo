package com.goodpeople.setgo.repository;

import com.goodpeople.setgo.domain.entities.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

@Validated
@Repository
public interface GoalRepository extends JpaRepository<Goal, String> {
}
