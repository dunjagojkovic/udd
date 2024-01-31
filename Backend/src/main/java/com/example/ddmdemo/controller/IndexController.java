package com.example.ddmdemo.controller;

import com.example.ddmdemo.dto.CreateIndexDTO;
import com.example.ddmdemo.dto.DummyDocumentFileDTO;
import com.example.ddmdemo.dto.DummyDocumentFileResponseDTO;
import com.example.ddmdemo.service.interfaces.IndexingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;


@RestController
@RequestMapping("/api/index")
@RequiredArgsConstructor
public class IndexController {

    private final IndexingService indexingService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DummyDocumentFileResponseDTO addDocumentFile(
        @ModelAttribute DummyDocumentFileDTO documentFile) {
        var serverFilename = indexingService.indexDocument(documentFile.file());
        return new DummyDocumentFileResponseDTO(Collections.singletonList(serverFilename.toString()));
    }

    @PostMapping(value = "/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void createIndex(@ModelAttribute CreateIndexDTO dto) {
        indexingService.createIndex(dto);
    }

}
