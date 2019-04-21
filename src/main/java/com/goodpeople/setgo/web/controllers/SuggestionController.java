package com.goodpeople.setgo.web.controllers;

import com.goodpeople.setgo.GlobalConstants;
import com.goodpeople.setgo.domain.models.binding.SuggestionBindingModel;
import com.goodpeople.setgo.domain.models.service.CategoryServiceModel;
import com.goodpeople.setgo.domain.models.service.SuggestionServiceModel;
import com.goodpeople.setgo.domain.models.view.SuggestionsListViewModel;
import com.goodpeople.setgo.service.SuggestionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/suggestions")
public class SuggestionController extends BaseController {

    private static final String SUGGESTIONS_ADD_SUGGESTION = "suggestions/add-suggestion";
    private static final String SUGGESTIONS_EDIT_SUGGESTION = "suggestions/edit-suggestion";
    private static final String SUGGESTIONS_SHOW_SUGGESTION = "suggestions/show-suggestion";
    private static final String SUGGESTIONS_DELETE_SUGGESTION = "suggestions/delete-suggestion";
    private static final String SUGGESTIONS = "suggestions";

    private final SuggestionService suggestionService;
    private final ModelMapper modelMapper;

    @Autowired
    public SuggestionController(SuggestionService suggestionService, ModelMapper modelMapper) {
        this.suggestionService = suggestionService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(GlobalConstants.ADD)
    @PreAuthorize(GlobalConstants.HAS_ROLE_MODERATOR)
    public ModelAndView add(ModelAndView modelAndView, @ModelAttribute(name = GlobalConstants.BINDING_MODEL) SuggestionBindingModel bindingModel) {
        modelAndView.addObject(GlobalConstants.BINDING_MODEL, bindingModel);
        return view(SUGGESTIONS_ADD_SUGGESTION, modelAndView);
    }

    @PostMapping(GlobalConstants.ADD)
    @PreAuthorize(GlobalConstants.HAS_ROLE_MODERATOR)
    public ModelAndView addConfirm(@Valid @ModelAttribute(name = GlobalConstants.BINDING_MODEL) SuggestionBindingModel bindingModel,
                                   BindingResult bindingResult, ModelAndView modelAndView) {

        if (bindingResult.hasErrors()) {
            modelAndView.addObject(GlobalConstants.BINDING_MODEL, bindingModel);
            return view(SUGGESTIONS_ADD_SUGGESTION, modelAndView);
        }

        SuggestionServiceModel suggestionToSave = this.modelMapper.map(bindingModel, SuggestionServiceModel.class);
        suggestionToSave.setCategory(new CategoryServiceModel() {{
            setName(bindingModel.getCategory());
        }});

        this.suggestionService.addSuggestion(suggestionToSave);
        return redirect(GlobalConstants.SUGGESTION_ALL);
    }

    @GetMapping(GlobalConstants.ALL)
    @PreAuthorize(GlobalConstants.HAS_ROLE_MODERATOR)
    public ModelAndView show(ModelAndView modelAndView) {

        Comparator<SuggestionsListViewModel> comparatorName = Comparator.comparing(a -> a.getCategory().getName());
        Comparator<SuggestionsListViewModel> comparatorRate = Comparator.comparing(SuggestionsListViewModel::getRate);
        List<SuggestionsListViewModel> allSuggestions = this.suggestionService.findAllSuggestions()
                .stream()
                .map(suggestion -> this.modelMapper.map(suggestion, SuggestionsListViewModel.class))
                .sorted(comparatorName.thenComparing(comparatorRate))
                .collect(Collectors.toList());

        modelAndView.addObject(SUGGESTIONS, allSuggestions);
        return view(SUGGESTIONS_SHOW_SUGGESTION, modelAndView);
    }

    @GetMapping(GlobalConstants.DELETE_ID)
    @PreAuthorize(GlobalConstants.HAS_ROLE_MODERATOR)
    public ModelAndView delete(@PathVariable(GlobalConstants.ID) String id, ModelAndView modelAndView) {
//TODO method
        SuggestionServiceModel suggestionServiceModel = this.suggestionService.findById(id);
        SuggestionBindingModel deleteViewModel = this.modelMapper.map(suggestionServiceModel, SuggestionBindingModel.class);
        deleteViewModel.setCategory(suggestionServiceModel.getCategory().getName());
        modelAndView.addObject(GlobalConstants.BINDING_MODEL, deleteViewModel);
        return view(SUGGESTIONS_DELETE_SUGGESTION, modelAndView);
    }

    @PostMapping(GlobalConstants.DELETE_ID)
    @PreAuthorize(GlobalConstants.HAS_ROLE_MODERATOR)
    public ModelAndView deleteConfirm(@PathVariable(GlobalConstants.ID) String id) {
        this.suggestionService.deleteSuggestionById(id);
        return redirect(GlobalConstants.SUGGESTION_ALL);
    }

    @GetMapping(GlobalConstants.EDIT_ID)
    @PreAuthorize(GlobalConstants.HAS_ROLE_MODERATOR)
    public ModelAndView edit(@PathVariable(GlobalConstants.ID) String id, ModelAndView modelAndView) {

        SuggestionServiceModel suggestionServiceModel = this.suggestionService.findById(id);
        SuggestionBindingModel editViewModel = this.modelMapper.map(suggestionServiceModel, SuggestionBindingModel.class);
        editViewModel.setCategory(suggestionServiceModel.getCategory().getName());
        modelAndView.addObject(GlobalConstants.BINDING_MODEL, editViewModel);
        return view(SUGGESTIONS_EDIT_SUGGESTION, modelAndView);
    }

    @PostMapping(GlobalConstants.EDIT_ID)
    @PreAuthorize(GlobalConstants.HAS_ROLE_MODERATOR)
    public ModelAndView editConfirm(@Valid @ModelAttribute(name = GlobalConstants.BINDING_MODEL) SuggestionBindingModel bindingModel,
                                    BindingResult bindingResult, ModelAndView modelAndView) {

        if (bindingResult.hasErrors()) {
            modelAndView.addObject(GlobalConstants.BINDING_MODEL, bindingModel);
            return view(SUGGESTIONS_EDIT_SUGGESTION, modelAndView);
        }

        SuggestionServiceModel suggestionToEdit = this.modelMapper.map(bindingModel, SuggestionServiceModel.class);
        this.suggestionService.editSuggestion(suggestionToEdit);
        return redirect(GlobalConstants.SUGGESTION_ALL);
    }


    @GetMapping(GlobalConstants.FETCH)
    @ResponseBody
    public List<SuggestionServiceModel> fetchSuggestions() {
        return this.suggestionService.findAllSuggestions();
    }


}
