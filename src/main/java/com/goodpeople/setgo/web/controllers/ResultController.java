package com.goodpeople.setgo.web.controllers;

import com.goodpeople.setgo.GlobalConstants;
import com.goodpeople.setgo.domain.models.binding.ResultBindingModel;
import com.goodpeople.setgo.domain.models.service.ResultServiceModel;
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

    private final ResultService resultService;
    private final ModelMapper modelMapper;

    @Autowired
    public ResultController(ResultService resultService, ModelMapper modelMapper) {
        this.resultService = resultService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(SAVE_ID)
    public ModelAndView save(@PathVariable("id") String id, ModelAndView modelAndView,
                             @ModelAttribute(name = GlobalConstants.BINDING_MODEL) ResultBindingModel resultBindingModel){
        ResultBindingModel saveViewModel = this.modelMapper.map(this.resultService.findById(id), ResultBindingModel.class);
        modelAndView.addObject(GlobalConstants.BINDING_MODEL, saveViewModel);
        return super.view("results/save-result", modelAndView);
    }

    @PostMapping(SAVE_ID)
    public ModelAndView saveConfirm(@Valid @ModelAttribute(name = GlobalConstants.BINDING_MODEL) ResultBindingModel bindingModel,
                                    BindingResult bindingResult, ModelAndView modelAndView) {

        if (bindingResult.hasErrors()) {
            modelAndView.addObject(GlobalConstants.BINDING_MODEL, bindingModel);
            return super.view("results/save-result", modelAndView);
        }

        ResultServiceModel serviceModel = this.modelMapper.map(bindingModel, ResultServiceModel.class);
        this.resultService.saveResult(serviceModel);
        return super.redirect(GlobalConstants.GOALS_ALL);
    }

}
