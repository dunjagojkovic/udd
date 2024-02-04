package com.example.ddmdemo.service.interfaces;

import com.example.ddmdemo.dto.GovernmentRegistrationDTO;
import com.example.ddmdemo.model.GovernmentInfo;
import org.springframework.stereotype.Service;

@Service
public interface GovernmentService {

    GovernmentInfo registerGovernment(GovernmentRegistrationDTO dto);

}
