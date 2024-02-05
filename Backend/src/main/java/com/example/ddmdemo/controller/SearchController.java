package com.example.ddmdemo.controller;

import com.example.ddmdemo.dto.SearchPhraseQueryDTO;
import com.example.ddmdemo.dto.SearchQueryDTO;
import com.example.ddmdemo.indexmodel.DummyIndex;
import com.example.ddmdemo.indexmodel.IndexUnit;
import com.example.ddmdemo.service.interfaces.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
@CrossOrigin
public class SearchController {

    @Autowired
    private SearchService searchService;

    @PostMapping("/simple")
    public Page<IndexUnit> simpleSearch(@RequestBody SearchQueryDTO simpleSearchQuery,
                                        Pageable pageable) {
        return searchService.simpleSearch(simpleSearchQuery.keywords(), pageable);
    }

    @PostMapping("/advanced")
    public Page<IndexUnit> advancedSearch(@RequestBody SearchQueryDTO advancedSearchQuery,
                                           Pageable pageable) {
        return searchService.advancedSearch(advancedSearchQuery.keywords(), pageable);
    }

    @PostMapping("/phrase")
    public Page<IndexUnit> phraseSearch(@RequestBody SearchPhraseQueryDTO phraseQuery,
                                           Pageable pageable) {
        return searchService.phraseSearch(phraseQuery.query(), pageable);
    }
}
