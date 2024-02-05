package com.example.ddmdemo.service.interfaces;

import com.example.ddmdemo.indexmodel.DummyIndex;
import java.util.List;

import com.example.ddmdemo.indexmodel.IndexUnit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface SearchService {

    Page<IndexUnit> simpleSearch(List<String> keywords, Pageable pageable);

    Page<IndexUnit> advancedSearch(List<String> expression, Pageable pageable);

    Page<IndexUnit> phraseSearch(String query, Pageable pageable);

    Page<IndexUnit> geoSearch(String address, String radius, Pageable pageable);
}
