package com.alperenozcan.insurancenotebook.dao;

import com.alperenozcan.insurancenotebook.entity.InsuranceQuote;

public interface InsuranceQuoteRepositoryCustom {

	public InsuranceQuote findByCustomerId(int theCustomerId);
}
