package com.goodpeople.setgo.service;

import com.goodpeople.setgo.domain.entities.Result;
import com.goodpeople.setgo.domain.models.service.ResultServiceModel;
import com.goodpeople.setgo.error.ResultNotFoundException;
import com.goodpeople.setgo.repository.ResultRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResultServiceImpl implements ResultService {

    private final ResultRepository resultRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ResultServiceImpl(ResultRepository resultRepository, ModelMapper modelMapper) {
        this.resultRepository = resultRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResultServiceModel findById(String id) {
        return this.resultRepository.findById(id).map(result -> this.modelMapper.map(result, ResultServiceModel.class))
                .orElseThrow(() -> new ResultNotFoundException("Result with the given id was not found!"));
    }

    @Override
    public void saveResult(ResultServiceModel resultServiceModel) {
        Result resultToSave = this.resultRepository.getOne(resultServiceModel.getId());
        this.modelMapper.map(resultServiceModel,resultToSave);
        this.resultRepository.save(resultToSave);
    }
}
