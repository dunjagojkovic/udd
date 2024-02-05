package com.example.ddmdemo.controller;

import com.example.ddmdemo.dto.HighlitedSearchResponseDTO;
import com.example.ddmdemo.dto.SearchQueryDTO;
import com.example.ddmdemo.service.impl.SearchServiceImpl;
import com.example.ddmdemo.service.interfaces.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
@CrossOrigin
public class SearchController {

    private final SearchService searchService;
    private final SearchServiceImpl service;

    @PostMapping("/simple")
    public List<HighlitedSearchResponseDTO> simpleSearch(@RequestBody SearchQueryDTO simpleSearchQuery,
                                                         Pageable pageable) {
        return searchService.simpleSearch(simpleSearchQuery.keywords(), pageable);
    }

    @PostMapping("/advanced")
    public List<HighlitedSearchResponseDTO> advancedSearch(@RequestBody SearchQueryDTO advancedSearchQuery,
                                                           Pageable pageable) {
        return searchService.advancedSearch(advancedSearchQuery.keywords(), pageable);
    }

}
