package com.example.ddmdemo.respository;

import com.example.ddmdemo.model.IndexUnitInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndexUnitInfoRepository extends JpaRepository<IndexUnitInfo, Integer> {
}
