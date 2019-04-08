package com.goodpeople.setgo.service;

import com.goodpeople.setgo.domain.entities.Category;
import com.goodpeople.setgo.domain.entities.Suggestion;
import com.goodpeople.setgo.domain.models.service.SuggestionServiceModel;
import com.goodpeople.setgo.error.SuggestionNotFoundException;
import com.goodpeople.setgo.repository.CategoryRepository;
import com.goodpeople.setgo.repository.SuggestionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SuggestionServiceImpl implements SuggestionService {

    private static final String SUGGESTION_WITH_ID_NOT_FOUND = "Suggestion with the given id was not found!";

    private final SuggestionRepository suggestionRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public SuggestionServiceImpl(SuggestionRepository suggestionRepository, CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.suggestionRepository = suggestionRepository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public SuggestionServiceModel addSuggestion(SuggestionServiceModel suggestionServiceModel) {

        try {
            Suggestion suggestion = this.modelMapper.map(suggestionServiceModel, Suggestion.class);
            suggestion.setCategory(this.categoryRepository.findByName(suggestionServiceModel.getCategory().getName()).orElse(null));
            suggestion = this.suggestionRepository.saveAndFlush(suggestion);
            return this.modelMapper.map(suggestion, SuggestionServiceModel.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<SuggestionServiceModel> findAllSuggestions() {
        return this.suggestionRepository.findAll()
                .stream()
                .map(suggestion -> this.modelMapper.map(suggestion, SuggestionServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteSuggestionById(String id) {
        Suggestion suggestion = this.suggestionRepository.findById(id)
                .orElseThrow(() -> new SuggestionNotFoundException(SUGGESTION_WITH_ID_NOT_FOUND));
        this.suggestionRepository.delete(suggestion);
    }

    @Override
    public SuggestionServiceModel findById(String id) {
        return this.suggestionRepository.findById(id).map(suggestion -> this.modelMapper.map(suggestion, SuggestionServiceModel.class))
                .orElseThrow(() -> new SuggestionNotFoundException(SUGGESTION_WITH_ID_NOT_FOUND));
    }

    @Override
    public void editSuggestion(SuggestionServiceModel suggestionServiceModel) {
        Suggestion suggestionToUpdate = this.suggestionRepository.findById(suggestionServiceModel.getId())
                .orElseThrow(() -> new SuggestionNotFoundException(SUGGESTION_WITH_ID_NOT_FOUND));

        this.modelMapper.map(suggestionServiceModel, suggestionToUpdate);
      //  suggestionToUpdate.getCategory().setId(this.categoryRepository.findByName(suggestionServiceModel.getCategory().getName()).orElse(null).getId());
        this.suggestionRepository.save(suggestionToUpdate);
    }
}