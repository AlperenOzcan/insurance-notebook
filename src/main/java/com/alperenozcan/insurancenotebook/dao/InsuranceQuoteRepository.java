package com.alperenozcan.insurancenotebook.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alperenozcan.insurancenotebook.entity.InsuranceQuote;

public interface InsuranceQuoteRepository extends JpaRepository<InsuranceQuote, Integer> {

	Optional<List<InsuranceQuote>> findByDetailId(int theDetailId);

	Optional<List<InsuranceQuote>> findByDetailIdAndInsuranceType(int theDetailId, String theType);

	void deleteByDetailIdAndInsuranceType(int theDetailId, String insuranceType);
}
