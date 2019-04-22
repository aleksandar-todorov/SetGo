package com.goodpeople.setgo.service;

import com.goodpeople.setgo.domain.entities.Result;
import com.goodpeople.setgo.domain.models.service.ResultServiceModel;
import com.goodpeople.setgo.error.ResultNotFoundException;
import com.goodpeople.setgo.repository.ResultRepository;
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
public class ResultServiceUnitTests {

    @Autowired
    private ResultRepository resultRepository;
    private ModelMapper modelMapper;

    @Before
    public void init() {
        this.modelMapper = new ModelMapper();
    }

    @Test
    public void resultService_saveResultWithCorrectValues_ReturnsCorrect() {
        ResultService resultService = new ResultServiceImpl(this.resultRepository, this.modelMapper);

        ResultServiceModel toBeSaved = new ResultServiceModel();
        toBeSaved.setConclusion("Conclusion 1");
        toBeSaved.setNotes("Notes 1");
        toBeSaved.setRate(5);
        toBeSaved.setSuggestion("Suggestion 1");
        toBeSaved.setId("id1");
        Result result = new Result();
        result.setId("id1");
        this.resultRepository.saveAndFlush(result);

        resultService.saveResult(toBeSaved);
        Result actual = this.resultRepository.getOne(toBeSaved.getId());
        Result expected = this.resultRepository.findAll().get(0);

        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.getConclusion(), actual.getConclusion());
        Assert.assertEquals(expected.getNotes(), actual.getNotes());
        Assert.assertEquals(expected.getRate(), actual.getRate());
        Assert.assertEquals(expected.getSuggestion(), actual.getSuggestion());
    }

    @Test
    public void resultService_saveResultWithIncorrectValues_ThrowsException() throws Exception {
        ResultService resultService = new ResultServiceImpl(this.resultRepository, this.modelMapper);

        ResultServiceModel toBeSaved = new ResultServiceModel();
        toBeSaved.setConclusion(null);
        toBeSaved.setNotes("Notes 1");
        toBeSaved.setRate(5);
        toBeSaved.setSuggestion("Suggestion 1");
        toBeSaved.setId("id1");
        Result result = new Result();
        result.setId("id1");
        this.resultRepository.saveAndFlush(result);

        resultService.saveResult(toBeSaved);
    }

    @Test
    public void resultService_findByIdResultWithValidId_ReturnsCorrect() {
        ResultService resultService = new ResultServiceImpl(this.resultRepository, this.modelMapper);

        Result result = new Result();
        result.setConclusion("Conclusion 1");
        result.setNotes("Notes 1");
        result.setRate(5);
        result.setSuggestion("Suggestion 1");
        result.setId("id1");
        this.resultRepository.saveAndFlush(result);

        ResultServiceModel actual = resultService.findById(result.getId());

        ResultServiceModel expected = this.modelMapper.map(result, ResultServiceModel.class);

        Assert.assertEquals(actual.getId(), expected.getId());
        Assert.assertEquals(actual.getConclusion(), expected.getConclusion());
        Assert.assertEquals(actual.getNotes(), expected.getNotes());
        Assert.assertEquals(actual.getRate(), expected.getRate());
        Assert.assertEquals(actual.getSuggestion(), expected.getSuggestion());
    }

    @Test(expected = ResultNotFoundException.class)
    public void resultService_findByIdResultWithInvalidId_ThrowsException() {
        ResultService resultService = new ResultServiceImpl(this.resultRepository, this.modelMapper);

        Result result = new Result();
        result.setConclusion("Conclusion 1");
        result.setNotes("Notes 1");
        result.setRate(5);
        result.setSuggestion("Suggestion 1");
        result.setId("id1");
        this.resultRepository.saveAndFlush(result);

        ResultServiceModel actual = resultService.findById("InvalidId");
    }
}