package com.alperenozcan.insurancenotebook.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.alperenozcan.insurancenotebook.entity.AutomobileDetail;

public interface AutomobileDetailRepository extends JpaRepository<AutomobileDetail, Integer>{

	@Query("from AutomobileDetail where customer_id=:theCustomerId")
	Optional<List<AutomobileDetail>> findByCustomerId(int theCustomerId);
}
