package com.goodpeople.setgo.config;

import com.goodpeople.setgo.domain.entities.Category;
import com.goodpeople.setgo.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    private CategoryRepository categoryRepository;

    @Autowired
    public DataLoader(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
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
            Category other = new Category();
            other.setName("Other");

            categoryRepository.save(health);
            categoryRepository.save(finances);
            categoryRepository.save(relationship);
            categoryRepository.save(personal);
            categoryRepository.save(other);
        }
    }
}