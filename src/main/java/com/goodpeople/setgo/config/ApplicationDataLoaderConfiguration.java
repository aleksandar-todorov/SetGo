package com.goodpeople.setgo.config;

import com.goodpeople.setgo.domain.entities.Category;
import com.goodpeople.setgo.domain.entities.Suggestion;
import com.goodpeople.setgo.repository.CategoryRepository;
import com.goodpeople.setgo.repository.SuggestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ApplicationDataLoaderConfiguration implements ApplicationRunner {

    private final String HEALTH = "Health";
    private final String FINANCES = "Finances";
    private final String RELATIONSHIP = "Relationship";
    private final String PERSONAL = "Personal";
    private final String SUGGESTION = " suggestion ";

    private CategoryRepository categoryRepository;
    private SuggestionRepository suggestionRepository;

    @Autowired
    public ApplicationDataLoaderConfiguration(CategoryRepository categoryRepository, SuggestionRepository suggestionRepository) {
        this.categoryRepository = categoryRepository;
        this.suggestionRepository = suggestionRepository;
    }

    public void run(ApplicationArguments args) {
        if (categoryRepository.count() == 0) {
            Category health = new Category();
            health.setName(HEALTH);
            Category finances = new Category();
            finances.setName(FINANCES);
            Category relationship = new Category();
            relationship.setName(RELATIONSHIP);
            Category personal = new Category();
            personal.setName(PERSONAL);

            categoryRepository.save(health);
            categoryRepository.save(finances);
            categoryRepository.save(relationship);
            categoryRepository.save(personal);

            for (int i = 0; i < 10; i++) {

                createSuggestionsInDB(health, i, HEALTH);

                createSuggestionsInDB(finances, i, FINANCES);

                createSuggestionsInDB(relationship, i, RELATIONSHIP);

                createSuggestionsInDB(personal, i, PERSONAL);
            }
        }
    }

    private void createSuggestionsInDB(Category category, int i, String name) {
        Suggestion Suggestion = new Suggestion();
        Suggestion.setCategory(category);
        Suggestion.setRate(i + 1);
        Suggestion.setProposal(name + SUGGESTION + (i + 1));
        suggestionRepository.save(Suggestion);
    }
}