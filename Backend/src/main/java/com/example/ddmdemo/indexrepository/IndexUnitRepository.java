package com.example.ddmdemo.indexrepository;

import com.example.ddmdemo.indexmodel.IndexUnit;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndexUnitRepository extends ElasticsearchRepository<IndexUnit, String> {
}
