package com.goodpeople.setgo.service;

import com.goodpeople.setgo.domain.entities.Category;
import com.goodpeople.setgo.domain.entities.Suggestion;
import com.goodpeople.setgo.domain.models.service.CategoryServiceModel;
import com.goodpeople.setgo.domain.models.service.SuggestionServiceModel;
import com.goodpeople.setgo.error.SuggestionNotFoundException;
import com.goodpeople.setgo.repository.CategoryRepository;
import com.goodpeople.setgo.repository.SuggestionRepository;
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

import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class SuggestionServiceUnitTests {

    @Autowired
    private SuggestionRepository suggestionRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;

    @Before
    public void init() {
        this.modelMapper = new ModelMapper();
    }

    @Test
    public void suggestionService_addSuggestionWithCorrectValues_ReturnsCorrect() {
        SuggestionService suggestionService = new SuggestionServiceImpl(this.suggestionRepository,this.categoryRepository, this.modelMapper);
        CategoryService categoryService = new CategoryServiceImpl(this.categoryRepository, this.modelMapper);

        SuggestionServiceModel toBeSaved = new SuggestionServiceModel();
        toBeSaved.setProposal("Proposal 1");
        toBeSaved.setRate(5);
        CategoryServiceModel category = new CategoryServiceModel();
        category.setName("Category 1");
        toBeSaved.setCategory(category);
        categoryService.addCategory(category);

        SuggestionServiceModel actual = suggestionService.addSuggestion(toBeSaved);
        SuggestionServiceModel expected = this.modelMapper.map(this.suggestionRepository.findAll().get(0),
                SuggestionServiceModel.class);

        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getProposal(), actual.getProposal());
        Assert.assertEquals(expected.getRate(), actual.getRate());
        Assert.assertEquals(expected.getCategory().getName(), actual.getCategory().getName());
    }

    @Test
    public void suggestionService_addSuggestionWithIncorrectValues_ThrowsException() throws Exception {
        SuggestionService suggestionService = new SuggestionServiceImpl(this.suggestionRepository,this.categoryRepository, this.modelMapper);

        SuggestionServiceModel toBeSaved = new SuggestionServiceModel();
        toBeSaved.setProposal(null);
        toBeSaved.setRate(5);

        suggestionService.addSuggestion(toBeSaved);
    }

    @Test
    public void suggestionService_deleteSuggestionWithValidId_ReturnsCorrect() {
        SuggestionService suggestionService = new SuggestionServiceImpl(this.suggestionRepository,this.categoryRepository, this.modelMapper);

        Suggestion suggestion = new Suggestion();
        suggestion.setProposal("Proposal 1");
        suggestion.setRate(5);
        Category category = new Category();
        category.setName("Category 1");
        this.categoryRepository.saveAndFlush(category);
        suggestion.setCategory(category);

        suggestion = this.suggestionRepository.saveAndFlush(suggestion);

        suggestionService.deleteSuggestionById(suggestion.getId());

        long expectedCount = 0;
        long actualCount = this.suggestionRepository.count();

        Assert.assertEquals(expectedCount, actualCount);
    }

    @Test(expected = SuggestionNotFoundException.class)
    public void suggestionService_deleteSuggestionWithInvalidId_ThrowsException() {
        SuggestionService suggestionService = new SuggestionServiceImpl(this.suggestionRepository, this.categoryRepository, this.modelMapper);

        Suggestion suggestion = new Suggestion();
        suggestion.setProposal("Proposal 1");
        suggestion.setRate(5);
        Category category = new Category();
        category.setName("Category 1");
        this.categoryRepository.saveAndFlush(category);
        suggestion.setCategory(category);

        suggestion = this.suggestionRepository.saveAndFlush(suggestion);

        suggestionService.deleteSuggestionById("InvalidId");
    }

    @Test
    public void suggestionService_editSuggestionWithCorrectValues_ReturnsCorrect() {
        SuggestionService suggestionService = new SuggestionServiceImpl(this.suggestionRepository,this.categoryRepository ,this.modelMapper);
        CategoryService categoryService = new CategoryServiceImpl(this.categoryRepository, this.modelMapper);

        SuggestionServiceModel suggestion = new SuggestionServiceModel();
        suggestion.setProposal("Proposal 1");
        suggestion.setRate(5);
        CategoryServiceModel category = new CategoryServiceModel();
        category.setName("Category 1");
        suggestion.setCategory(category);
        categoryService.addCategory(category);

        suggestion = suggestionService.addSuggestion(suggestion);

        SuggestionServiceModel tobeEdited = new SuggestionServiceModel();
        tobeEdited.setId(suggestion.getId());
        tobeEdited.setProposal("Proposal 2");
        tobeEdited.setRate(8);
        CategoryServiceModel category2 = new CategoryServiceModel();
        category2.setName("Category 2");
        category2 = categoryService.addCategory(category2);
        tobeEdited.setCategory(category2);

        suggestionService.editSuggestion(tobeEdited);
        Suggestion actual = suggestionRepository.getOne(tobeEdited.getId());

        Assert.assertEquals(actual.getId(), tobeEdited.getId());
        Assert.assertEquals(actual.getProposal(), tobeEdited.getProposal());
        Assert.assertEquals(actual.getRate(), tobeEdited.getRate());
    }

    @Test(expected = SuggestionNotFoundException.class)
    public void suggestionService_editSuggestionWithIncorrectValues_ThrowsException() {
        SuggestionService suggestionService = new SuggestionServiceImpl(this.suggestionRepository, this.categoryRepository, this.modelMapper);
        CategoryService categoryService = new CategoryServiceImpl(this.categoryRepository, this.modelMapper);

        SuggestionServiceModel suggestion = new SuggestionServiceModel();
        suggestion.setProposal("Proposal 1");
        suggestion.setRate(5);
        CategoryServiceModel category = new CategoryServiceModel();
        category.setName("Category 1");
        suggestion.setCategory(category);
        categoryService.addCategory(category);

        suggestionService.addSuggestion(suggestion);

        SuggestionServiceModel tobeEdited = new SuggestionServiceModel();
        tobeEdited.setId("InvalidId");
        tobeEdited.setProposal("Proposal 2");
        tobeEdited.setRate(8);
        CategoryServiceModel category2 = new CategoryServiceModel();
        category2.setName("Category 2");
        category2 = categoryService.addCategory(category2);
        tobeEdited.setCategory(category2);

        suggestionService.editSuggestion(tobeEdited);
    }

    @Test
    public void suggestionService_findByIdSuggestionWithValidId_ReturnsCorrect() {
        SuggestionService suggestionService = new SuggestionServiceImpl(this.suggestionRepository,
                this.categoryRepository,this.modelMapper);
        CategoryService categoryService = new CategoryServiceImpl(this.categoryRepository, this.modelMapper);

        SuggestionServiceModel suggestion = new SuggestionServiceModel();
        suggestion.setProposal("Proposal 1");
        suggestion.setRate(5);
        CategoryServiceModel category = new CategoryServiceModel();
        category.setName("Category 1");
        suggestion.setCategory(category);
        categoryService.addCategory(category);

        suggestion = suggestionService.addSuggestion(suggestion);

        SuggestionServiceModel actual = suggestionService.findById(suggestion.getId());

        SuggestionServiceModel expected = this.modelMapper.map(suggestion, SuggestionServiceModel.class);

        Assert.assertEquals(actual.getId(), expected.getId());
        Assert.assertEquals(actual.getProposal(), expected.getProposal());
        Assert.assertEquals(actual.getRate(), expected.getRate());
        Assert.assertEquals(actual.getCategory().getName(), expected.getCategory().getName());
    }

    @Test(expected = SuggestionNotFoundException.class)
    public void suggestionService_findByIdSuggestionWithInvalidId_ThrowsException(){
        SuggestionService suggestionService = new SuggestionServiceImpl(this.suggestionRepository,
                this.categoryRepository,this.modelMapper);
        CategoryService categoryService = new CategoryServiceImpl(this.categoryRepository, this.modelMapper);

        SuggestionServiceModel suggestion = new SuggestionServiceModel();
        suggestion.setProposal("Proposal 1");
        suggestion.setRate(5);
        CategoryServiceModel category = new CategoryServiceModel();
        category.setName("Category 1");
        suggestion.setCategory(category);
        categoryService.addCategory(category);

        suggestionService.addSuggestion(suggestion);

        suggestionService.findById("InvalidId");
    }

    @Test
    public void suggestionService_findAllSuggestionsWithValidId_ReturnsCorrect() {
        SuggestionService suggestionService = new SuggestionServiceImpl(this.suggestionRepository,
                this.categoryRepository,this.modelMapper);
        CategoryService categoryService = new CategoryServiceImpl(this.categoryRepository, this.modelMapper);

        SuggestionServiceModel suggestion = new SuggestionServiceModel();
        suggestion.setProposal("Proposal 1");
        suggestion.setRate(5);
        CategoryServiceModel category = new CategoryServiceModel();
        category.setName("Category 1");
        suggestion.setCategory(category);
        categoryService.addCategory(category);

        suggestion = suggestionService.addSuggestion(suggestion);

        SuggestionServiceModel suggestion2 = new SuggestionServiceModel();
        suggestion2.setProposal("Proposal 2");
        suggestion2.setRate(8);
        CategoryServiceModel category2 = new CategoryServiceModel();
        category2.setName("Category 2");
        suggestion2.setCategory(category2);
        categoryService.addCategory(category2);

        suggestion2 = suggestionService.addSuggestion(suggestion2);

        List<SuggestionServiceModel> allSuggestions = suggestionService.findAllSuggestions();

        Assert.assertEquals(allSuggestions.get(0).getId(), suggestion.getId());
        Assert.assertEquals(allSuggestions.get(0).getProposal(), suggestion.getProposal());
        Assert.assertEquals(allSuggestions.get(0).getRate(), suggestion.getRate());
        Assert.assertEquals(allSuggestions.get(0).getCategory().getName(), suggestion.getCategory().getName());

        Assert.assertEquals(allSuggestions.get(1).getId(), suggestion2.getId());
        Assert.assertEquals(allSuggestions.get(1).getProposal(), suggestion2.getProposal());
        Assert.assertEquals(allSuggestions.get(1).getRate(), suggestion2.getRate());
        Assert.assertEquals(allSuggestions.get(1).getCategory().getName(), suggestion2.getCategory().getName());
    }
}
