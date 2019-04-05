package com.goodpeople.setgo.service;

import com.goodpeople.setgo.domain.entities.Goal;
import com.goodpeople.setgo.domain.entities.Result;
import com.goodpeople.setgo.domain.models.service.GoalEditServiceModel;
import com.goodpeople.setgo.domain.models.service.GoalServiceModel;
import com.goodpeople.setgo.repository.CategoryRepository;
import com.goodpeople.setgo.repository.GoalRepository;
import com.goodpeople.setgo.repository.ResultRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GoalServiceImpl implements GoalService {

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
    public boolean deleteGoalById(String id) {
        try {
            this.goalRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public GoalServiceModel findById(String id) {
        Goal goal = this.goalRepository.findById(id).orElse(null);
        return goal == null ? null : this.modelMapper.map(goal, GoalServiceModel.class);
    }

    @Override
    public void editGoal(GoalEditServiceModel goalEditServiceModel) {
        Goal goalToUpdate = this.goalRepository.getOne(goalEditServiceModel.getId());
        this.modelMapper.map(goalEditServiceModel, goalToUpdate);
        this.goalRepository.save(goalToUpdate);
    }
}
