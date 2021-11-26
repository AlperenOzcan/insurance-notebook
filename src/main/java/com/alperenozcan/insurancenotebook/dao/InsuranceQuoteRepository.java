package com.alperenozcan.insurancenotebook.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.alperenozcan.insurancenotebook.entity.InsuranceQuote;

public interface InsuranceQuoteRepository extends JpaRepository<InsuranceQuote, Integer> {

	@Query("from InsuranceQuote where detailId=:theDetailId")
	Optional<List<InsuranceQuote>> findByDetailId(int theDetailId);
	//Optional<List<InsuranceQuote>> findAllByDetailId(int theDetailId);
	
	@Query("from InsuranceQuote where detailId=:theDetailId and insuranceType=:theType")
	Optional<List<InsuranceQuote>> findByDetailIdAndInsuranceType(int theDetailId, String theType);
	// Optional<InsuranceQuote> findByDetailIdAndInsuranceType(int detailId, String insuranceType);
	// Optional<InsuranceQuote> findAllByDetailIdAndInsuranceType(int detailId, String insuranceType);
		
	@Modifying
	@Query("delete from InsuranceQuote where detailId=:theDetailId and insuranceType=:theType")
	void deleteByDetailIdAndInsuranceType(int theDetailId, String theType);
}
