package com.goodpeople.setgo.web.controllers;

import com.goodpeople.setgo.domain.models.binding.CategoryBindingModel;
import com.goodpeople.setgo.domain.models.service.CategoryServiceModel;
import com.goodpeople.setgo.domain.models.view.CategoryListViewModel;
import com.goodpeople.setgo.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    @Autowired
    public CategoryController(CategoryService categoryService, ModelMapper modelMapper) {
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    public ModelAndView add(ModelAndView modelAndView, @ModelAttribute(name = "bindingModel") CategoryBindingModel bindingModel) {
        modelAndView.addObject("bindingModel", bindingModel);
        return super.view("categories/add-category", modelAndView);
    }

    @PostMapping("/add")
    public ModelAndView addConfirm(@Valid @ModelAttribute(name = "bindingModel") CategoryBindingModel bindingModel,
                                   BindingResult bindingResult, ModelAndView modelAndView) {

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("bindingModel", bindingModel);
            return super.view("categories/add-category", modelAndView);
        }
        CategoryServiceModel categoryServiceModel = this.categoryService
                .addCategory(this.modelMapper.map(bindingModel, CategoryServiceModel.class));
        if (categoryServiceModel == null) {
            throw new IllegalArgumentException("Add category went wrong!");
        }
        return super.redirect("/categories/all");
    }

    @GetMapping("/all")
    public ModelAndView show(ModelAndView modelAndView) {
        List<CategoryListViewModel> allCategories = this.categoryService.findAllCategories()
                .stream()
                .map(category -> this.modelMapper.map(category, CategoryListViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("categories", allCategories);
        return super.view("categories/show-category", modelAndView);
    }

    @GetMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable("id") String id, ModelAndView modelAndView,
                             @ModelAttribute(name = "bindingModel") CategoryBindingModel bindingModel) {

        CategoryBindingModel editViewModel = this.modelMapper.map(this.categoryService.findById(id), CategoryBindingModel.class);
        modelAndView.addObject("bindingModel", editViewModel);
        return super.view("categories/edit-category", modelAndView);
    }

    @PostMapping("/edit/{id}")
    public ModelAndView editConfirm(@Valid @ModelAttribute(name = "bindingModel") CategoryBindingModel bindingModel,
                                    BindingResult bindingResult, ModelAndView modelAndView) {

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("bindingModel", bindingModel);
            return super.view("categories/edit-category", modelAndView);
        }

        CategoryServiceModel serviceModel = this.modelMapper.map(bindingModel, CategoryServiceModel.class);
        this.categoryService.editCategory(serviceModel);
        return super.redirect("/categories/all");
    }

    @GetMapping("/fetch")
    @ResponseBody
    public List<CategoryServiceModel> fetchCategories() {
        return this.categoryService.findAllCategories();
    }

}
