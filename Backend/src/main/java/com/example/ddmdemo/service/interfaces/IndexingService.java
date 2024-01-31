package com.example.ddmdemo.service.interfaces;

import com.example.ddmdemo.dto.IndexUnitInfoDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface IndexingService {

    String indexDocument(MultipartFile documentFile);

    String createIndex( IndexUnitInfoDTO dto);
}
