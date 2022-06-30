package com.alperenozcan.insurancenotebook.dao;

import com.alperenozcan.insurancenotebook.entity.AutomobileDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AutomobileDetailRepository extends JpaRepository<AutomobileDetail, Integer>{

	Optional<List<AutomobileDetail>> findByCustomerId(int theCustomerId);
}
