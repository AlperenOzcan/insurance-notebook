package com.alperenozcan.insurancenotebook.service;

import java.util.List;

import com.alperenozcan.insurancenotebook.entity.CustomerHealthDetail;

public interface CustomerHealthDetailService {
	
	public List<CustomerHealthDetail> findAll();
	
	public CustomerHealthDetail findById(int theId);
	
	public CustomerHealthDetail findByCustomerId(int theCustomerId);

	public void save (CustomerHealthDetail theCustomerHealthDetail);
	
	public void deleteById(int theId);
}
