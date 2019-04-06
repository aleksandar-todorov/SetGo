package com.goodpeople.setgo.web.controllers;

import com.goodpeople.setgo.domain.models.binding.GoalBindingModel;
import com.goodpeople.setgo.domain.models.binding.GoalEditBindingModel;
import com.goodpeople.setgo.domain.models.service.CategoryServiceModel;
import com.goodpeople.setgo.domain.models.service.GoalEditServiceModel;
import com.goodpeople.setgo.domain.models.service.GoalServiceModel;
import com.goodpeople.setgo.domain.models.view.GoalsListViewModel;
import com.goodpeople.setgo.service.GoalService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/goals")
public class GoalController extends BaseController {

    private final GoalService goalService;
    private final ModelMapper modelMapper;

    @Autowired
    public GoalController(GoalService goalService, ModelMapper modelMapper) {
        this.goalService = goalService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    public ModelAndView add(ModelAndView modelAndView, @ModelAttribute(name = "bindingModel") GoalBindingModel bindingModel) {
        modelAndView.addObject("bindingModel", bindingModel);
        return super.view("goals/add-goal", modelAndView);
    }

    @PostMapping("/add")
    public ModelAndView addConfirm(@Valid @ModelAttribute(name = "bindingModel") GoalBindingModel bindingModel,
                                   BindingResult bindingResult, ModelAndView modelAndView) {

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("bindingModel", bindingModel);
            return super.view("goals/add-goal", modelAndView);
        }

        GoalServiceModel goalToSave = this.modelMapper.map(bindingModel, GoalServiceModel.class);
        goalToSave.setCategory(new CategoryServiceModel(){{
            setName(bindingModel.getCategory());
        }});

        this.goalService.addGoal(goalToSave);

        if (goalToSave == null) {
            throw new IllegalArgumentException("Add goal went wrong!");
        }
        return super.redirect("/goals/all");
    }

    @GetMapping("/all")
    public ModelAndView show(ModelAndView modelAndView) {
        modelAndView.addObject("goals", this.goalService.findAllGoals()
                .stream()
                .map(goal -> this.modelMapper.map(goal, GoalsListViewModel.class))
                .collect(Collectors.toList()));
        return super.view("goals/show-goal", modelAndView);
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id") String id, ModelAndView modelAndView,
                               @ModelAttribute(name = "bindingModel") GoalBindingModel bindingModel) {

        GoalBindingModel deleteViewModel = this.modelMapper.map(this.goalService.findById(id), GoalBindingModel.class);
        modelAndView.addObject("bindingModel", deleteViewModel);
        return super.view("goals/delete-goal", modelAndView);
    }

    @PostMapping("/delete/{id}")
    public ModelAndView deleteConfirm(@PathVariable("id") String id) {
        this.goalService.deleteGoalById(id);
        return super.redirect("/goals/all");
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") String id, ModelAndView modelAndView,
                             @ModelAttribute(name = "bindingModel") GoalBindingModel bindingModel) {

        GoalEditBindingModel editViewModel = this.modelMapper.map(this.goalService.findById(id), GoalEditBindingModel.class);
        modelAndView.addObject("bindingModel", editViewModel);
        return super.view("goals/edit-goal", modelAndView);
    }

    @PostMapping("/edit/{id}")
    public ModelAndView editConfirm(@Valid @ModelAttribute(name = "bindingModel") GoalEditBindingModel bindingModel,
                                    BindingResult bindingResult, ModelAndView modelAndView) {

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("bindingModel", bindingModel);
            return super.view("goals/edit-goal", modelAndView);
        }

        GoalEditServiceModel serviceModel = this.modelMapper.map(bindingModel, GoalEditServiceModel.class);
        this.goalService.editGoal(serviceModel);
        return super.redirect("/goals/all");
    }



}
