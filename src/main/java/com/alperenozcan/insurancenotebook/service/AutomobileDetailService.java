package com.alperenozcan.insurancenotebook.service;

import java.util.List;
import java.util.Optional;

import com.alperenozcan.insurancenotebook.entity.AutomobileDetail;

public interface AutomobileDetailService {

	public List<AutomobileDetail> findAll();

	public AutomobileDetail findById(int theId);

	public Optional<List<AutomobileDetail>> findByCustomerId(int theCustomerId);
	
	public void save(AutomobileDetail theAutomobileDetail);
	
	public void deleteById(int theId);
}
