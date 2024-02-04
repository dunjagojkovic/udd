package com.example.ddmdemo.respository;

import com.example.ddmdemo.model.GovernmentInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GovernmentInfoRepository extends JpaRepository<GovernmentInfo, Integer> {
}
