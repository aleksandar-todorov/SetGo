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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("/goals")
public class GoalController extends BaseController {

    private static final String GOALS = "goals";
    private static final String GOALS_ADD_GOAL = "goals/add-goal";
    private static final String GOALS_EDIT_GOAL = "goals/edit-goal";
    private static final String GOALS_SHOW_GOAL = "goals/show-goal";
    private static final String GOALS_DELETE_GOAL = "goals/delete-goal";

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
        return view(GOALS_ADD_GOAL, modelAndView);
    }

    @PostMapping(GlobalConstants.ADD)
    public ModelAndView addConfirm(@Valid @ModelAttribute(name = GlobalConstants.BINDING_MODEL) GoalBindingModel bindingModel,
                                   BindingResult bindingResult, ModelAndView modelAndView) {

        if (bindingResult.hasErrors()) {
            modelAndView.addObject(GlobalConstants.BINDING_MODEL, bindingModel);
            return view(GOALS_ADD_GOAL, modelAndView);
        }

        GoalServiceModel goalToSave = this.modelMapper.map(bindingModel, GoalServiceModel.class);
        goalToSave.setCategory(new CategoryServiceModel() {{
            setName(bindingModel.getCategory());
        }});

        this.goalService.addGoal(goalToSave);
        return redirect(GlobalConstants.GOALS_ALL);
    }

    @GetMapping(GlobalConstants.ALL)
    public ModelAndView show(ModelAndView modelAndView) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<GoalsListViewModel> allGoals = this.goalService.findAllGoals()
                .stream()
                .filter(goal -> goal.getUser_id().equals(user.getId()))
                .map(goal -> this.modelMapper.map(goal, GoalsListViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject(GOALS, allGoals);
        return view(GOALS_SHOW_GOAL, modelAndView);
    }

    @GetMapping(GlobalConstants.DELETE_ID)
    public ModelAndView delete(@PathVariable(GlobalConstants.ID) String id, ModelAndView modelAndView,
                               @ModelAttribute(name = GlobalConstants.BINDING_MODEL) GoalBindingModel bindingModel) {

        GoalBindingModel deleteViewModel = this.modelMapper.map(this.goalService.findById(id), GoalBindingModel.class);
        modelAndView.addObject(GlobalConstants.BINDING_MODEL, deleteViewModel);
        return view(GOALS_DELETE_GOAL, modelAndView);
    }

    @PostMapping(GlobalConstants.DELETE_ID)
    public ModelAndView deleteConfirm(@PathVariable(GlobalConstants.ID) String id) {
        this.goalService.deleteGoalById(id);
        return redirect(GlobalConstants.GOALS_ALL);
    }

    @GetMapping(GlobalConstants.EDIT_ID)
    public ModelAndView edit(@PathVariable(GlobalConstants.ID) String id, ModelAndView modelAndView,
                             @ModelAttribute(name = GlobalConstants.BINDING_MODEL) GoalBindingModel bindingModel) {

        GoalEditBindingModel editViewModel = this.modelMapper.map(this.goalService.findById(id), GoalEditBindingModel.class);
        modelAndView.addObject(GlobalConstants.BINDING_MODEL, editViewModel);
        return view(GOALS_EDIT_GOAL, modelAndView);
    }

    @PostMapping(GlobalConstants.EDIT_ID)
    public ModelAndView editConfirm(@Valid @ModelAttribute(name = GlobalConstants.BINDING_MODEL) GoalEditBindingModel bindingModel,
                                    BindingResult bindingResult, ModelAndView modelAndView) {

        if (bindingResult.hasErrors()) {
            modelAndView.addObject(GlobalConstants.BINDING_MODEL, bindingModel);
            return view(GOALS_EDIT_GOAL, modelAndView);
        }

        GoalEditServiceModel serviceModel = this.modelMapper.map(bindingModel, GoalEditServiceModel.class);
        this.goalService.editGoal(serviceModel);
        return redirect(GlobalConstants.GOALS_ALL);
    }

//    @GetMapping(GlobalConstants.FETCH)
//    @ResponseBody
//    @Scheduled(fixedRate = 5000)
//    private List<String> testSchedule() {
//
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        List<GoalServiceModel> allGoals = goalService.findAllGoals()
//                    .stream()
//                    .filter(goal -> goal.getUser_id().equals(user.getId()))
//                    .collect(Collectors.toList());
//            List<String> usersGoals = new ArrayList<>();
//            allGoals.forEach(goal -> usersGoals.add(goal.getCategory().getName()));
//        return usersGoals;
//    }

}


