package com.example.ddmdemo.service.interfaces;

import com.example.ddmdemo.dto.HighlitedSearchResponseDTO;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface SearchService {

    List<HighlitedSearchResponseDTO> simpleSearch(List<String> keywords, Pageable pageable);

    List<HighlitedSearchResponseDTO> advancedSearch(List<String> expression, Pageable pageable);
}
