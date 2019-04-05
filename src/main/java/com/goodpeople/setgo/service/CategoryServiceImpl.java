package com.goodpeople.setgo.service;

import com.goodpeople.setgo.domain.entities.Category;
import com.goodpeople.setgo.domain.models.service.CategoryServiceModel;
import com.goodpeople.setgo.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryServiceModel addCategory(CategoryServiceModel categoryServiceModel) {
        Category category = this.modelMapper.map(categoryServiceModel, Category.class);
        try {
            category = this.categoryRepository.saveAndFlush(category);
            return this.modelMapper.map(category, CategoryServiceModel.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<CategoryServiceModel> findAllCategories() {
        return this.categoryRepository.findAll()
                .stream()
                .map(category -> this.modelMapper.map(category,CategoryServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryServiceModel findById(String id) {
        Category category = this.categoryRepository.findById(id).orElse(null);
        return category == null ? null : this.modelMapper.map(category, CategoryServiceModel.class);
    }

    @Override
    public void editCategory(CategoryServiceModel categoryServiceModel) {
        Category categoryToUpdate = this.categoryRepository.getOne(categoryServiceModel.getId());
        this.modelMapper.map(categoryServiceModel, categoryToUpdate);
        this.categoryRepository.save(categoryToUpdate);
    }
}

