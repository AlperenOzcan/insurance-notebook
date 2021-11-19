package com.alperenozcan.insurancenotebook.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.alperenozcan.insurancenotebook.entity.InsuranceQuote;

public interface InsuranceQuoteRepository extends JpaRepository<InsuranceQuote, Integer> {

	@Query("from InsuranceQuote where customer_id=:theCustomerId")
	Optional<List<InsuranceQuote>> findByCustomerId(int theCustomerId);
}
