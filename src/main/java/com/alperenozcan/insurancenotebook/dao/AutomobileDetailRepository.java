package com.alperenozcan.insurancenotebook.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alperenozcan.insurancenotebook.entity.AutomobileDetail;
import com.alperenozcan.insurancenotebook.entity.CustomerHealthDetail;

public interface AutomobileDetailRepository extends JpaRepository<AutomobileDetail, Integer>{

	Optional<AutomobileDetail> findByCustomerId(int theCustomerId); // ?
}
