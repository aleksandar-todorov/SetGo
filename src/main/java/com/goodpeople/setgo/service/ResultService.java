package com.goodpeople.setgo.service;

import com.goodpeople.setgo.domain.models.service.ResultServiceModel;

public interface ResultService {

    ResultServiceModel findById(String id);

    void saveResult(ResultServiceModel resultServiceModel);
}
