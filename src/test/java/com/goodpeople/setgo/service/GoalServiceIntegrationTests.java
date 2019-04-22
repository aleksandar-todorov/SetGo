package com.goodpeople.setgo.service;

import com.goodpeople.setgo.domain.entities.Category;
import com.goodpeople.setgo.domain.entities.Goal;
import com.goodpeople.setgo.domain.models.service.GoalServiceModel;
import com.goodpeople.setgo.repository.GoalRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class GoalServiceIntegrationTests {

    @Autowired
    GoalService service;

    @MockBean
    GoalRepository goalRepository;

    private List<Goal> goals;

    @Before
    public void setup() {
        goals = new ArrayList<>();
        when(goalRepository.findAll())
                .thenReturn(goals);
    }

    @Test
    public void findAllGoals_whenGoals_returnGoals() {

        String goalName = "goal 1";
        String categoryName = "category name";
        LocalDate beginDate = LocalDate.of(14, 5, 10);
        LocalDate endDate = LocalDate.of(24, 8, 20);
        String goalReason = "goal reason";

        Goal goal = new Goal();
        goal.setName(goalName);
        goal.setCategory(new Category() {
            {
                setName(categoryName);
            }
        });

        goal.setBeginOn(beginDate);
        goal.setEndOn(endDate);
        goal.setReason(goalReason);

        goals.add(goal);

        var result = service.findAllGoals();
        GoalServiceModel goalResult = result.get(0);

        assertEquals(1, result.size());
        assertEquals(goalName, goalResult.getName());
        assertEquals(categoryName, goalResult.getCategory().getName());
        assertEquals(beginDate, goalResult.getBeginOn());
        assertEquals(endDate, goalResult.getEndOn());
        assertEquals(goalReason, goalResult.getReason());
    }

    @Test
    public void findAllGoals_whenNoGoals_returnEmptyGoals() {
        goals.clear();
        var result = service.findAllGoals();
        assertTrue(result.isEmpty());
    }


}
