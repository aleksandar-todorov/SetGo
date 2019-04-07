package com.goodpeople.setgo.web.controllers;

import com.goodpeople.setgo.GlobalConstants;
import com.goodpeople.setgo.domain.models.binding.CategoryBindingModel;
import com.goodpeople.setgo.domain.models.service.CategoryServiceModel;
import com.goodpeople.setgo.domain.models.view.CategoryListViewModel;
import com.goodpeople.setgo.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/categories")
public class CategoryController extends BaseController {

    private static final String HAS_ROLE_MODERATOR = "hasRole('ROLE_MODERATOR')";
    private static final String CATEGORIES_ADD_CATEGORY = "categories/add-category";
    private static final String CATEGORIES_EDIT_CATEGORY = "categories/edit-category";
    private static final String CATEGORIES_SHOW_CATEGORY = "categories/show-category";
    private static final String CATEGORIES_ALL = "/categories/all";
    private static final String CATEGORIES = "categories";
    private static final String ADD_CATEGORY_WENT_WRONG = "Add category went wrong!";

    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    @Autowired
    public CategoryController(CategoryService categoryService, ModelMapper modelMapper) {
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(GlobalConstants.ADD)
    @PreAuthorize(HAS_ROLE_MODERATOR)
    public ModelAndView add(ModelAndView modelAndView, @ModelAttribute(name = GlobalConstants.BINDING_MODEL) CategoryBindingModel bindingModel) {
        modelAndView.addObject(GlobalConstants.BINDING_MODEL, bindingModel);
        return super.view(CATEGORIES_ADD_CATEGORY, modelAndView);
    }

    @PostMapping(GlobalConstants.ADD)
    @PreAuthorize(HAS_ROLE_MODERATOR)
    public ModelAndView addConfirm(@Valid @ModelAttribute(name = GlobalConstants.BINDING_MODEL) CategoryBindingModel bindingModel,
                                   BindingResult bindingResult, ModelAndView modelAndView) {

        if (bindingResult.hasErrors()) {
            modelAndView.addObject(GlobalConstants.BINDING_MODEL, bindingModel);
            return super.view(CATEGORIES_ADD_CATEGORY, modelAndView);
        }
        CategoryServiceModel categoryServiceModel = this.categoryService
                .addCategory(this.modelMapper.map(bindingModel, CategoryServiceModel.class));
        if (categoryServiceModel == null) {
            throw new IllegalArgumentException(ADD_CATEGORY_WENT_WRONG);
        }
        return super.redirect(CATEGORIES_ALL);
    }

    @GetMapping(GlobalConstants.ALL)
    @PreAuthorize(HAS_ROLE_MODERATOR)
    public ModelAndView show(ModelAndView modelAndView) {
        List<CategoryListViewModel> allCategories = this.categoryService.findAllCategories()
                .stream()
                .map(category -> this.modelMapper.map(category, CategoryListViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject(CATEGORIES, allCategories);
        return super.view(CATEGORIES_SHOW_CATEGORY, modelAndView);
    }

    @GetMapping(GlobalConstants.EDIT_ID)
    @PreAuthorize(HAS_ROLE_MODERATOR)
    public ModelAndView edit(@PathVariable("id") String id, ModelAndView modelAndView,
                             @ModelAttribute(name = GlobalConstants.BINDING_MODEL) CategoryBindingModel bindingModel) {

        CategoryBindingModel editViewModel = this.modelMapper.map(this.categoryService.findById(id), CategoryBindingModel.class);
        modelAndView.addObject(GlobalConstants.BINDING_MODEL, editViewModel);
        return super.view(CATEGORIES_EDIT_CATEGORY, modelAndView);
    }

    @PostMapping(GlobalConstants.EDIT_ID)
    @PreAuthorize(HAS_ROLE_MODERATOR)
    public ModelAndView editConfirm(@Valid @ModelAttribute(name = GlobalConstants.BINDING_MODEL) CategoryBindingModel bindingModel,
                                    BindingResult bindingResult, ModelAndView modelAndView) {

        if (bindingResult.hasErrors()) {
            modelAndView.addObject(GlobalConstants.BINDING_MODEL, bindingModel);
            return super.view(CATEGORIES_EDIT_CATEGORY, modelAndView);
        }

        CategoryServiceModel serviceModel = this.modelMapper.map(bindingModel, CategoryServiceModel.class);
        this.categoryService.editCategory(serviceModel);
        return super.redirect(CATEGORIES_ALL);
    }

    @GetMapping("/fetch")
    @ResponseBody
    public List<CategoryServiceModel> fetchCategories() {
        return this.categoryService.findAllCategories();
    }

}
