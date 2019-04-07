package com.goodpeople.setgo.service;

import com.goodpeople.setgo.domain.entities.Goal;
import com.goodpeople.setgo.domain.entities.Result;
import com.goodpeople.setgo.domain.entities.User;
import com.goodpeople.setgo.domain.models.service.GoalEditServiceModel;
import com.goodpeople.setgo.domain.models.service.GoalServiceModel;
import com.goodpeople.setgo.error.GoalNotFoundException;
import com.goodpeople.setgo.repository.CategoryRepository;
import com.goodpeople.setgo.repository.GoalRepository;
import com.goodpeople.setgo.repository.ResultRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GoalServiceImpl implements GoalService {

    private static final String GOAL_WITH_ID_NOT_FOUND = "Goal with the given id was not found!";

    private final GoalRepository goalRepository;
    private final ResultRepository resultRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public GoalServiceImpl(GoalRepository goalRepository, ResultRepository resultRepository, CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.goalRepository = goalRepository;
        this.resultRepository = resultRepository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public GoalServiceModel addGoal(GoalServiceModel goalServiceModel) {

        try {
            Goal goal = this.modelMapper.map(goalServiceModel, Goal.class);
            goal.setCategory(this.categoryRepository.findByName(goalServiceModel.getCategory().getName()).orElse(null));
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            goal.setUser_id(user.getId());
            goal = this.goalRepository.saveAndFlush(goal);
            Result result = new Result();
            result.setId(goal.getId());
            this.resultRepository.saveAndFlush(result);
            return this.modelMapper.map(goal, GoalServiceModel.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<GoalServiceModel> findAllGoals() {
        return this.goalRepository.findAll()
                .stream()
                .map(goal -> this.modelMapper.map(goal, GoalServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteGoalById(String id) {
        Goal goal = this.goalRepository.findById(id)
                .orElseThrow(() -> new GoalNotFoundException(GOAL_WITH_ID_NOT_FOUND));
        this.goalRepository.delete(goal);
        this.resultRepository.deleteById(goal.getId());
    }

    @Override
    public GoalServiceModel findById(String id) {
        return this.goalRepository.findById(id).map(goal -> this.modelMapper.map(goal, GoalServiceModel.class))
                .orElseThrow(() -> new GoalNotFoundException(GOAL_WITH_ID_NOT_FOUND));
    }

    @Override
    public void editGoal(GoalEditServiceModel goalEditServiceModel) {
        Goal goalToUpdate = this.goalRepository.findById(goalEditServiceModel.getId())
                .orElseThrow(() -> new GoalNotFoundException(GOAL_WITH_ID_NOT_FOUND));
        this.modelMapper.map(goalEditServiceModel, goalToUpdate);
        this.goalRepository.save(goalToUpdate);
    }
}
