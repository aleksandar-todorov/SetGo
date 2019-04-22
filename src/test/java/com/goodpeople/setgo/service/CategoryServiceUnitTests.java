package com.goodpeople.setgo.service;

import com.goodpeople.setgo.domain.entities.Category;
import com.goodpeople.setgo.domain.models.service.CategoryServiceModel;
import com.goodpeople.setgo.repository.CategoryRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CategoryServiceUnitTests {

    @Autowired
    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;

    @Before
    public void init() {
        this.modelMapper = new ModelMapper();
    }

    @Test
    public void categoryService_saveCategoryWithCorrectValues_ReturnsCorrect() {
        CategoryService categoryService = new CategoryServiceImpl(this.categoryRepository, this.modelMapper);

        CategoryServiceModel toBeSaved = new CategoryServiceModel();
        toBeSaved.setName("Category 1");

        CategoryServiceModel actual = categoryService.addCategory(toBeSaved);
        CategoryServiceModel expected = this.modelMapper.map(this.categoryRepository.findAll().get(0),
                CategoryServiceModel.class);

        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getName(), actual.getName());
    }

    @Test
    public void categoryService_saveCategoryWithEmptyValues_ThrowsException() throws Exception {
        CategoryService categoryService = new CategoryServiceImpl(this.categoryRepository, this.modelMapper);

        CategoryServiceModel toBeSaved = new CategoryServiceModel();
        toBeSaved.setName("");
        categoryService.addCategory(toBeSaved);
    }

    @Test
    public void categoryService_saveCategoryWithNullValues_ThrowsException() throws Exception {
        CategoryService categoryService = new CategoryServiceImpl(this.categoryRepository, this.modelMapper);

        CategoryServiceModel toBeSaved = new CategoryServiceModel();
        toBeSaved.setName(null);
        categoryService.addCategory(toBeSaved);
    }

    @Test
    public void categoryService_editCategoryWithCorrectValues_ReturnsCorrect() {
        CategoryService categoryService = new CategoryServiceImpl(this.categoryRepository, this.modelMapper);

        Category category = new Category();
        category.setName("Category 1");

        category = this.categoryRepository.saveAndFlush(category);

        CategoryServiceModel tobeEdited = new CategoryServiceModel();
        tobeEdited.setId(category.getId());
        tobeEdited.setName("New Category 2");

        categoryService.editCategory(tobeEdited);
        Category actual = categoryRepository.getOne(tobeEdited.getId());
        Category expected = this.modelMapper.map(tobeEdited, Category.class);

        Assert.assertEquals(actual.getId(), expected.getId());
        Assert.assertEquals(actual.getName(), expected.getName());

    }

    @Test
    public void categoryService_editCategoryWithNullValues_ReturnsCorrect() throws Exception {
        CategoryService categoryService = new CategoryServiceImpl(this.categoryRepository, this.modelMapper);

        Category category = new Category();
        category.setName("Category 1");

        category = this.categoryRepository.saveAndFlush(category);

        CategoryServiceModel tobeEdited = new CategoryServiceModel();
        tobeEdited.setId(category.getId());
        tobeEdited.setName(null);

        categoryService.editCategory(tobeEdited);

    }

    @Test
    public void categoryService_findByIdCategoryWithValidId_ReturnsCorrect() {
        CategoryService categoryService = new CategoryServiceImpl(this.categoryRepository, this.modelMapper);

        Category category = new Category();
        category.setName("Category 1");
        this.categoryRepository.saveAndFlush(category);

        CategoryServiceModel actual = categoryService.findById(category.getId());

        CategoryServiceModel expected = this.modelMapper.map(category, CategoryServiceModel.class);

        Assert.assertEquals(actual.getId(), expected.getId());
        Assert.assertEquals(actual.getName(), expected.getName());
    }

    @Test
    public void categoryService_findByIdCategoryWithInvalidId_ThrowsException() throws Exception {
        CategoryService categoryService = new CategoryServiceImpl(this.categoryRepository, this.modelMapper);

        Category category = new Category();
        category.setName("Category 1");
        this.categoryRepository.saveAndFlush(category);

        categoryService.findById("InvalidId");
    }
}

