//package com.goodpeople.setgo.service;
//
//import com.goodpeople.setgo.domain.models.service.CategoryServiceModel;
//import com.goodpeople.setgo.domain.models.service.GoalServiceModel;
//import com.goodpeople.setgo.repository.CategoryRepository;
//import com.goodpeople.setgo.repository.GoalRepository;
//import com.goodpeople.setgo.repository.ResultRepository;
//import org.junit.Before;
//import org.junit.runner.RunWith;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.time.LocalDate;
//
//@RunWith(SpringRunner.class)
//@DataJpaTest
//@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
//public class GoalServiceUnitTests {
//
//    @Autowired
//    private GoalRepository goalRepository;
//    private ResultRepository resultRepository;
//    private CategoryRepository categoryRepository;
//    private ModelMapper modelMapper;
//
//    @Before
//    public void init() {
//        this.modelMapper = new ModelMapper();
//    }
//
//    @Test
//    public void goalService_saveGoalWithCorrectValues_ReturnsCorrect() {
//        GoalService goalService = new GoalServiceImpl(this.goalRepository,
//                this.resultRepository, this.categoryRepository, this.modelMapper);
//
//        CategoryService categoryService = new CategoryServiceImpl(this.categoryRepository, this.modelMapper);
//
//        GoalServiceModel toBeSaved = new GoalServiceModel();
//        CategoryServiceModel category = new CategoryServiceModel();
//        category.setName("Category 1");
//        categoryService.addCategory(category);
//        toBeSaved.setCategory(category);
//        toBeSaved.setBeginOn(LocalDate.of(2015, 10, 10));
//        toBeSaved.setEndOn(LocalDate.of(2020, 10 , 10));
//        toBeSaved.setName("Goal 1");
//        toBeSaved.setReason("Reason 1");
//        toBeSaved.setUser_id("123123123123");
//
//        GoalServiceModel actual = goalService.addGoal(toBeSaved);
//        GoalServiceModel expected = this.modelMapper.map(this.goalRepository.findAll().get(0),
//                GoalServiceModel.class);
//
//        Assert.assertEquals(expected.getId(),actual.getId());
//        Assert.assertEquals(expected.getName(),actual.getName());
//        Assert.assertEquals(expected.getCategory(),actual.getCategory());
//        Assert.assertEquals(expected.getBeginOn(),actual.getBeginOn());
//        Assert.assertEquals(expected.getEndOn(),actual.getEndOn());
//        Assert.assertEquals(expected.getReason(),actual.getReason());
//        Assert.assertEquals(expected.getUser_id(),actual.getUser_id());
//
//    }
//}
