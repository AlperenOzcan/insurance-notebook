package com.alperenozcan.insurancenotebook.service;

import java.util.List;

import com.alperenozcan.insurancenotebook.entity.Customer;

public interface CustomerService {

	public List<Customer> findAll();
	
	public Customer findById(int theId);

	public void save (Customer theCustomer);
	
	public void deleteById(int theId);
}
