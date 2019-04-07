package com.goodpeople.setgo.web.controllers;

import com.goodpeople.setgo.GlobalConstants;
import com.goodpeople.setgo.domain.entities.User;
import com.goodpeople.setgo.domain.models.binding.GoalBindingModel;
import com.goodpeople.setgo.domain.models.binding.GoalEditBindingModel;
import com.goodpeople.setgo.domain.models.service.CategoryServiceModel;
import com.goodpeople.setgo.domain.models.service.GoalEditServiceModel;
import com.goodpeople.setgo.domain.models.service.GoalServiceModel;
import com.goodpeople.setgo.domain.models.view.GoalsListViewModel;
import com.goodpeople.setgo.service.GoalService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
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

    @GetMapping(GlobalConstants.ADD)
    public ModelAndView add(ModelAndView modelAndView, @ModelAttribute(name = GlobalConstants.BINDING_MODEL) GoalBindingModel bindingModel) {
        modelAndView.addObject(GlobalConstants.BINDING_MODEL, bindingModel);
        return super.view("goals/add-goal", modelAndView);
    }

    @PostMapping(GlobalConstants.ADD)
    public ModelAndView addConfirm(@Valid @ModelAttribute(name = GlobalConstants.BINDING_MODEL) GoalBindingModel bindingModel,
                                   BindingResult bindingResult, ModelAndView modelAndView) {

        if (bindingResult.hasErrors()) {
            modelAndView.addObject(GlobalConstants.BINDING_MODEL, bindingModel);
            return super.view("goals/add-goal", modelAndView);
        }

        GoalServiceModel goalToSave = this.modelMapper.map(bindingModel, GoalServiceModel.class);
        goalToSave.setCategory(new CategoryServiceModel() {{
            setName(bindingModel.getCategory());
        }});

        this.goalService.addGoal(goalToSave);
        return super.redirect(GlobalConstants.GOALS_ALL);
    }

    @GetMapping(GlobalConstants.ALL)
    public ModelAndView show(ModelAndView modelAndView, Principal principal) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<GoalsListViewModel> allGoals = this.goalService.findAllGoals()
                .stream()
                .filter(goal -> goal.getUser_id().equals(user.getId()))
                .map(goal -> this.modelMapper.map(goal, GoalsListViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("goals", allGoals);
        return super.view("goals/show-goal", modelAndView);
    }

    @GetMapping(GlobalConstants.DELETE_ID)
    public ModelAndView delete(@PathVariable("id") String id, ModelAndView modelAndView,
                               @ModelAttribute(name = GlobalConstants.BINDING_MODEL) GoalBindingModel bindingModel) {

        GoalBindingModel deleteViewModel = this.modelMapper.map(this.goalService.findById(id), GoalBindingModel.class);
        modelAndView.addObject(GlobalConstants.BINDING_MODEL, deleteViewModel);
        return super.view("goals/delete-goal", modelAndView);
    }

    @PostMapping(GlobalConstants.DELETE_ID)
    public ModelAndView deleteConfirm(@PathVariable("id") String id) {
        this.goalService.deleteGoalById(id);
        return super.redirect(GlobalConstants.GOALS_ALL);
    }

    @GetMapping(GlobalConstants.EDIT_ID)
    public ModelAndView edit(@PathVariable("id") String id, ModelAndView modelAndView,
                             @ModelAttribute(name = GlobalConstants.BINDING_MODEL) GoalBindingModel bindingModel) {

        GoalEditBindingModel editViewModel = this.modelMapper.map(this.goalService.findById(id), GoalEditBindingModel.class);
        modelAndView.addObject(GlobalConstants.BINDING_MODEL, editViewModel);
        return super.view("goals/edit-goal", modelAndView);
    }

    @PostMapping(GlobalConstants.EDIT_ID)
    public ModelAndView editConfirm(@Valid @ModelAttribute(name = GlobalConstants.BINDING_MODEL) GoalEditBindingModel bindingModel,
                                    BindingResult bindingResult, ModelAndView modelAndView) {

        if (bindingResult.hasErrors()) {
            modelAndView.addObject(GlobalConstants.BINDING_MODEL, bindingModel);
            return super.view("goals/edit-goal", modelAndView);
        }

        GoalEditServiceModel serviceModel = this.modelMapper.map(bindingModel, GoalEditServiceModel.class);
        this.goalService.editGoal(serviceModel);
        return super.redirect(GlobalConstants.GOALS_ALL);
    }


}
