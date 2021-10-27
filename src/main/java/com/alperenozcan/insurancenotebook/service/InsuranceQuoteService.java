package com.alperenozcan.insurancenotebook.service;

import java.util.List;

import com.alperenozcan.insurancenotebook.entity.InsuranceQuote;

public interface InsuranceQuoteService {

	public List<InsuranceQuote> findAll();
	
	public InsuranceQuote findById(int theId);
	
	public InsuranceQuote findByCustomerId(int theCustomerId);

	public void save (InsuranceQuote theInsuranceQuote);
	
	public void deleteById(int theId);
}
