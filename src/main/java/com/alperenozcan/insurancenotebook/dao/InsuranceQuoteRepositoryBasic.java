package com.alperenozcan.insurancenotebook.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alperenozcan.insurancenotebook.entity.InsuranceQuote;

public interface InsuranceQuoteRepositoryBasic extends JpaRepository<InsuranceQuote, Integer> {

}
