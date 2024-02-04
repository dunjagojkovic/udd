package com.example.ddmdemo.service.impl;

import com.example.ddmdemo.dto.GovernmentRegistrationDTO;
import com.example.ddmdemo.indexmodel.IndexUnit;
import com.example.ddmdemo.indexrepository.IndexUnitRepository;
import com.example.ddmdemo.model.GovernmentInfo;
import com.example.ddmdemo.model.enumeration.GovernmentType;
import com.example.ddmdemo.respository.GovernmentInfoRepository;
import com.example.ddmdemo.service.interfaces.GovernmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class GovernmentServiceImpl implements GovernmentService {

    private final GovernmentInfoRepository governmentInfoRepository;

    @Override
    public GovernmentInfo registerGovernment(GovernmentRegistrationDTO dto) {

        GovernmentInfo newGovernment = new GovernmentInfo();
        newGovernment.setAddress(dto.getAddress());
        newGovernment.setCity(dto.getCity());
        newGovernment.setName(dto.getGovernmentName());
        newGovernment.setPassword(dto.getPassword());  //odradi kriptovanje da ne bude plain text
        newGovernment.setType(GovernmentType.valueOf(dto.getType()));
        newGovernment.setUsername(dto.getUsername());

        return governmentInfoRepository.save(newGovernment);
    }
}
