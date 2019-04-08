package com.goodpeople.setgo.service;

import com.goodpeople.setgo.domain.models.service.SuggestionServiceModel;
import java.util.List;

public interface SuggestionService {

    SuggestionServiceModel addSuggestion(SuggestionServiceModel suggestionServiceModel);

    List<SuggestionServiceModel> findAllSuggestions();

    SuggestionServiceModel findById(String id);

    void deleteSuggestionById(String id);

    void editSuggestion(SuggestionServiceModel suggestionServiceModel);
}
