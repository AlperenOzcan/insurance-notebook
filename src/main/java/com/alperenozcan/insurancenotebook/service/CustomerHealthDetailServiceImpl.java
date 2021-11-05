package com.alperenozcan.insurancenotebook.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alperenozcan.insurancenotebook.dao.CustomerHealthDetailRepository;
import com.alperenozcan.insurancenotebook.dao.CustomerRepository;
import com.alperenozcan.insurancenotebook.entity.Customer;
import com.alperenozcan.insurancenotebook.entity.CustomerHealthDetail;

@Service
public class CustomerHealthDetailServiceImpl implements CustomerHealthDetailService {

	private CustomerHealthDetailRepository customerHealthDetailRepository;
	
	// we need customerRepository for findByCustomerId()
	private CustomerRepository customerRepository;
	
	
	@Autowired
	public CustomerHealthDetailServiceImpl(CustomerHealthDetailRepository customerHealthDetailRepository,
			CustomerRepository customerRepository) {
		this.customerHealthDetailRepository = customerHealthDetailRepository;
		this.customerRepository = customerRepository;
	}

	@Override
	public List<CustomerHealthDetail> findAll() {
		return customerHealthDetailRepository.findAll();
	}

	@Override
	public CustomerHealthDetail findById(int theId) {
		Optional<CustomerHealthDetail> result = customerHealthDetailRepository.findById(theId);
		
		CustomerHealthDetail theCustomerHealthDetail = null;
		if (result.isPresent()) {
			theCustomerHealthDetail = result.get();
		}
		else {
			// we do not have any customerHealthDetail with given id
			throw new RuntimeException("Did not find customerHealthDetail with id: " + theId);
		}
		
		return theCustomerHealthDetail;
	}

	@Override
	public Optional<CustomerHealthDetail> findByCustomerId(int theCustomerId) {
		Optional<Customer> theCustomer = customerRepository.findById(theCustomerId);
		
		Optional<CustomerHealthDetail> theCustomerHealthDetail = Optional.empty();
		if (theCustomer.isPresent()) {
			Optional<CustomerHealthDetail> result = customerHealthDetailRepository.findByCustomerId(theCustomerId);
			
			theCustomerHealthDetail = result;
		}
		else {
			// we do not have any customerHealthDetail with given id
			// throw new RuntimeException("Did not find customerHealthDetail with customerId: " + theCustomerId);
			System.out.println("Did not find customerHealthDetail with customerId: " + theCustomerId);
		}
		
		return theCustomerHealthDetail;
	}

	@Override
	public void save(CustomerHealthDetail theCustomerHealthDetail) {
		customerHealthDetailRepository.save(theCustomerHealthDetail);
	}

	@Override
	public void deleteById(int theId) {
		customerHealthDetailRepository.deleteById(theId);
	}

}
