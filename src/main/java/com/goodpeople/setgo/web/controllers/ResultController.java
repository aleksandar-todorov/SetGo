package com.goodpeople.setgo.web.controllers;

import com.goodpeople.setgo.GlobalConstants;
import com.goodpeople.setgo.domain.entities.Goal;
import com.goodpeople.setgo.domain.models.binding.ResultBindingModel;
import com.goodpeople.setgo.domain.models.service.ResultServiceModel;
import com.goodpeople.setgo.error.GoalNotFoundException;
import com.goodpeople.setgo.repository.GoalRepository;
import com.goodpeople.setgo.service.ResultService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/results")
public class ResultController extends BaseController {

    private static final String SAVE_ID = "/save/{id}";
    private static final String RESULTS_SAVE_RESULT = "results/save-result";
    private static final String GOAL= "goal";

    private final ResultService resultService;
    private final GoalRepository goalRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ResultController(ResultService resultService, GoalRepository goalRepository, ModelMapper modelMapper) {
        this.resultService = resultService;
        this.goalRepository = goalRepository;
        this.modelMapper = modelMapper;
    }

    @GetMapping(SAVE_ID)
    public ModelAndView save(@PathVariable(GlobalConstants.ID) String id, ModelAndView modelAndView,
                             @ModelAttribute(name = GlobalConstants.BINDING_MODEL) ResultBindingModel resultBindingModel) {
        ResultBindingModel saveViewModel = this.modelMapper.map(this.resultService.findById(id), ResultBindingModel.class);
        modelAndView.addObject(GlobalConstants.BINDING_MODEL, saveViewModel);

        Goal goal = goalRepository.findById(id).orElseThrow(() -> new GoalNotFoundException(GlobalConstants.GOAL_WITH_ID_NOT_FOUND));
        modelAndView.addObject(GOAL, goal);

        return view(RESULTS_SAVE_RESULT, modelAndView);
    }

    @PostMapping(SAVE_ID)
    public ModelAndView saveConfirm(@Valid @ModelAttribute(name = GlobalConstants.BINDING_MODEL) ResultBindingModel bindingModel,
                                    BindingResult bindingResult, ModelAndView modelAndView) {

        if (bindingResult.hasErrors()) {
            modelAndView.addObject(GlobalConstants.BINDING_MODEL, bindingModel);
            return view(RESULTS_SAVE_RESULT, modelAndView);
        }

        ResultServiceModel serviceModel = this.modelMapper.map(bindingModel, ResultServiceModel.class);
        this.resultService.saveResult(serviceModel);
        return redirect(GlobalConstants.GOALS_ALL);
    }

}
