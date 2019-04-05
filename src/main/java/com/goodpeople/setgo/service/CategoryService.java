package com.goodpeople.setgo.service;

import com.goodpeople.setgo.domain.models.service.CategoryServiceModel;

import java.util.List;

public interface CategoryService {

    CategoryServiceModel addCategory(CategoryServiceModel categoryServiceModel);

    List<CategoryServiceModel> findAllCategories();

    CategoryServiceModel findById(String id);

    void editCategory(CategoryServiceModel categoryServiceModel);
}
