package com.goodpeople.setgo.service;

import com.goodpeople.setgo.GlobalConstants;
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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GoalServiceImpl implements GoalService {

    private static final String ALL_USERS_HAVE_TOTAL_X_GOALS = "All users have a Total of %d Goals";

    private final GoalRepository goalRepository;
    private final ResultRepository resultRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public GoalServiceImpl(GoalRepository goalRepository, ResultRepository resultRepository,
                           CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.goalRepository = goalRepository;
        this.resultRepository = resultRepository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public GoalServiceModel addGoal(GoalServiceModel goalServiceModel) {

        try {
            Goal goal = this.modelMapper.map(goalServiceModel, Goal.class);
            goal.setCategory(this.categoryRepository
                    .findByName(goalServiceModel.getCategory().getName())
                    .orElse(null));

            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(user != null){
                goal.setUser_id(user.getId());
            }

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
                .orElseThrow(() -> new GoalNotFoundException(GlobalConstants.GOAL_WITH_ID_NOT_FOUND));
        this.goalRepository.delete(goal);
        this.resultRepository.deleteById(goal.getId());
    }

    @Override
    public GoalServiceModel findById(String id) {
        return this.goalRepository.findById(id)
                .map(goal -> this.modelMapper.map(goal, GoalServiceModel.class))
                .orElseThrow(() -> new GoalNotFoundException(GlobalConstants.GOAL_WITH_ID_NOT_FOUND));
    }

    @Override
    public void editGoal(GoalEditServiceModel goalEditServiceModel) {
        Goal goalToUpdate = this.goalRepository.findById(goalEditServiceModel.getId())
                .orElseThrow(() -> new GoalNotFoundException(GlobalConstants.GOAL_WITH_ID_NOT_FOUND));
        this.modelMapper.map(goalEditServiceModel, goalToUpdate);
        this.goalRepository.save(goalToUpdate);
    }

    @Scheduled(fixedRate = 300000)
    private void testSchedule() {
        System.out.println(String.format(ALL_USERS_HAVE_TOTAL_X_GOALS, goalRepository.findAll().size()));
    }
}
