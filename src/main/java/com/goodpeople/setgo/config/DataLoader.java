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
public class DataLoader implements ApplicationRunner {

    private CategoryRepository categoryRepository;
    private SuggestionRepository suggestionRepository;

    @Autowired
    public DataLoader(CategoryRepository categoryRepository, SuggestionRepository suggestionRepository) {
        this.categoryRepository = categoryRepository;
        this.suggestionRepository = suggestionRepository;
    }

    public void run(ApplicationArguments args) {
        if(categoryRepository.count() == 0) {
            Category health = new Category();
            health.setName("Health");
            Category finances = new Category();
            finances.setName("Finances");
            Category relationship = new Category();
            relationship.setName("Relationship");
            Category personal = new Category();
            personal.setName("Personal");


            categoryRepository.save(health);
            categoryRepository.save(finances);
            categoryRepository.save(relationship);
            categoryRepository.save(personal);

            for(int i=0; i<10  ; i++){
                Suggestion healthSuggestion = new Suggestion();
                healthSuggestion.setCategory(health);
                healthSuggestion.setRate(i+1);
                healthSuggestion.setProposal("Health proposal " + (i+1));
                suggestionRepository.save(healthSuggestion);

                Suggestion financesSuggestion = new Suggestion();
                financesSuggestion.setCategory(finances);
                financesSuggestion.setRate(i+1);
                financesSuggestion.setProposal("Finances proposal " + (i+1));
                suggestionRepository.save(financesSuggestion);


            }

        }




    }
}