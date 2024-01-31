package com.example.ddmdemo.controller;

import com.example.ddmdemo.dto.DummyDocumentFileDTO;
import com.example.ddmdemo.dto.DummyDocumentFileResponseDTO;
import com.example.ddmdemo.dto.IndexUnitInfoDTO;
import com.example.ddmdemo.service.interfaces.IndexingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/index")
@RequiredArgsConstructor
public class IndexController {

    private final IndexingService indexingService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DummyDocumentFileResponseDTO addDocumentFile(
        @ModelAttribute DummyDocumentFileDTO documentFile) {
        System.out.println("usao");
        var serverFilename = indexingService.indexDocument(documentFile.file());
        System.out.println(serverFilename);
        return new DummyDocumentFileResponseDTO(serverFilename);

    }

    @PostMapping(value = "/create")
    @ResponseStatus(HttpStatus.CREATED)
    public DummyDocumentFileResponseDTO addDocument(@ModelAttribute  IndexUnitInfoDTO dto) {
        System.out.println("usao");
        var serverFilename = indexingService.createIndex(dto);
        System.out.println(serverFilename);
        return new DummyDocumentFileResponseDTO(serverFilename);

    }
}
