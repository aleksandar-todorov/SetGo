package com.goodpeople.setgo.web.controllers;

import com.goodpeople.setgo.GlobalConstants;
import com.goodpeople.setgo.domain.entities.User;
import com.goodpeople.setgo.domain.models.binding.SuggestionBindingModel;
import com.goodpeople.setgo.domain.models.service.CategoryServiceModel;
import com.goodpeople.setgo.domain.models.service.SuggestionServiceModel;
import com.goodpeople.setgo.domain.models.view.SuggestionsListViewModel;
import com.goodpeople.setgo.service.SuggestionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/suggestions")
public class SuggestionController extends BaseController {

    private static final String SUGGESTION_ADD_SUGGESTION = "suggestions/add-suggestion";
    private static final String SUGGESTION_EDIT_SUGGESTION = "suggestions/edit-suggestion";

    private final SuggestionService suggestionService;
    private final ModelMapper modelMapper;

    @Autowired
    public SuggestionController(SuggestionService suggestionService, ModelMapper modelMapper) {
        this.suggestionService = suggestionService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(GlobalConstants.ADD)
    public ModelAndView add(ModelAndView modelAndView, @ModelAttribute(name = GlobalConstants.BINDING_MODEL) SuggestionBindingModel bindingModel) {
        modelAndView.addObject(GlobalConstants.BINDING_MODEL, bindingModel);
        return view(SUGGESTION_ADD_SUGGESTION, modelAndView);
    }

    @PostMapping(GlobalConstants.ADD)
    public ModelAndView addConfirm(@Valid @ModelAttribute(name = GlobalConstants.BINDING_MODEL) SuggestionBindingModel bindingModel,
                                   BindingResult bindingResult, ModelAndView modelAndView) {

        if (bindingResult.hasErrors()) {
            modelAndView.addObject(GlobalConstants.BINDING_MODEL, bindingModel);
            return view(SUGGESTION_ADD_SUGGESTION, modelAndView);
        }

        SuggestionServiceModel suggestionToSave = this.modelMapper.map(bindingModel, SuggestionServiceModel.class);
        suggestionToSave.setCategory(new CategoryServiceModel() {{
            setName(bindingModel.getCategory());
        }});

        this.suggestionService.addSuggestion(suggestionToSave);
        return redirect(GlobalConstants.SUGGESTION_ALL);
    }

    @GetMapping(GlobalConstants.ALL)
    public ModelAndView show(ModelAndView modelAndView, Principal principal) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<SuggestionsListViewModel> allSuggestions = this.suggestionService.findAllSuggestions()
                .stream()
                .map(suggestion -> this.modelMapper.map(suggestion, SuggestionsListViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("suggestions", allSuggestions);
        return view("suggestions/show-suggestion", modelAndView);
    }

    @GetMapping(GlobalConstants.DELETE_ID)
    public ModelAndView delete(@PathVariable("id") String id, ModelAndView modelAndView,
                               @ModelAttribute(name = GlobalConstants.BINDING_MODEL) SuggestionBindingModel bindingModel) {

        SuggestionBindingModel deleteViewModel = this.modelMapper.map(this.suggestionService.findById(id), SuggestionBindingModel.class);
        modelAndView.addObject(GlobalConstants.BINDING_MODEL, deleteViewModel);
        return view("suggestions/delete-suggestion", modelAndView);
    }

    @PostMapping(GlobalConstants.DELETE_ID)
    public ModelAndView deleteConfirm(@PathVariable("id") String id) {
        this.suggestionService.deleteSuggestionById(id);
        return redirect(GlobalConstants.SUGGESTION_ALL);
    }

    @GetMapping(GlobalConstants.EDIT_ID)
    public ModelAndView edit(@PathVariable("id") String id, ModelAndView modelAndView,
                             @ModelAttribute(name = GlobalConstants.BINDING_MODEL) SuggestionBindingModel bindingModel) {

        SuggestionBindingModel editViewModel = this.modelMapper.map(this.suggestionService.findById(id), SuggestionBindingModel.class);
        modelAndView.addObject(GlobalConstants.BINDING_MODEL, editViewModel);
        return view(SUGGESTION_EDIT_SUGGESTION, modelAndView);
    }

    @PostMapping(GlobalConstants.EDIT_ID)
    public ModelAndView editConfirm(@Valid @ModelAttribute(name = GlobalConstants.BINDING_MODEL) SuggestionBindingModel bindingModel,
                                    BindingResult bindingResult, ModelAndView modelAndView) {

        if (bindingResult.hasErrors()) {
            modelAndView.addObject(GlobalConstants.BINDING_MODEL, bindingModel);
            return view(SUGGESTION_EDIT_SUGGESTION, modelAndView);
        }

        SuggestionServiceModel suggestionToEdit = this.modelMapper.map(bindingModel, SuggestionServiceModel.class);
        suggestionToEdit.setCategory(new CategoryServiceModel() {{
            setName(bindingModel.getCategory());
        }});
        this.suggestionService.editSuggestion(suggestionToEdit);
        return redirect(GlobalConstants.SUGGESTION_ALL);
    }

}
