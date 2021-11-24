package com.alperenozcan.insurancenotebook.service;

import java.util.List;

import com.alperenozcan.insurancenotebook.entity.InsuranceQuote;

public interface InsuranceQuoteService {

	public List<InsuranceQuote> findAll();
	
	public InsuranceQuote findById(int theId);
	
	public List<InsuranceQuote> findByDetailId(int theDetailId);

	public List<InsuranceQuote> findByDetailIdAndInsuranceType(int theDetailId, String theType);
	
	public void save (InsuranceQuote theInsuranceQuote);
	
	public void deleteById(int theId);
	
	public void deleteByDetailId(int theDetailId);
}
