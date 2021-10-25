package com.alperenozcan.insurancenotebook.dao;

import java.util.List;

import com.alperenozcan.insurancenotebook.entity.CustomerHealthDetail;

public interface CustomerHealthDetailDAO {

	public List<CustomerHealthDetail> findAll();
	
	public CustomerHealthDetail findById(int theId);

	public CustomerHealthDetail findByCustomerId(int theCustomerId);
	
	public void save(CustomerHealthDetail theCustomerHealthDetail);
	
	public void deleteById(int theId);

	public void deleteByCustomerId(int theId);
}
