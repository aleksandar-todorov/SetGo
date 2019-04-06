package com.goodpeople.setgo.service;

import com.goodpeople.setgo.domain.models.service.GoalEditServiceModel;
import com.goodpeople.setgo.domain.models.service.GoalServiceModel;

import java.util.List;

public interface GoalService {

    GoalServiceModel addGoal(GoalServiceModel goalServiceModel);

    List<GoalServiceModel> findAllGoals();

    void deleteGoalById(String id);

    GoalServiceModel findById(String id);

    void editGoal(GoalEditServiceModel goalEditServiceModel);
}
