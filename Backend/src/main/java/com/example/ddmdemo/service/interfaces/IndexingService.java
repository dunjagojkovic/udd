package com.example.ddmdemo.service.interfaces;

import com.example.ddmdemo.dto.CreateIndexDTO;
import com.example.ddmdemo.dto.GovernmentRegistrationDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface IndexingService {

    String indexDocument(MultipartFile documentFile);

    void createIndex(CreateIndexDTO dto, Integer governmentId);

}
