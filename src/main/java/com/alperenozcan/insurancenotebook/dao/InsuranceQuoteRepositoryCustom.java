package com.alperenozcan.insurancenotebook.dao;

import java.util.List;
import java.util.Optional;

import com.alperenozcan.insurancenotebook.entity.InsuranceQuote;

public interface InsuranceQuoteRepositoryCustom {

	public Optional<List<InsuranceQuote>> findByCustomerId(int theCustomerId);
}
