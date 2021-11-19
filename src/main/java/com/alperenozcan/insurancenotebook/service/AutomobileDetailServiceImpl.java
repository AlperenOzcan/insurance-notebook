package com.alperenozcan.insurancenotebook.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alperenozcan.insurancenotebook.dao.AutomobileDetailRepository;
import com.alperenozcan.insurancenotebook.dao.CustomerRepository;
import com.alperenozcan.insurancenotebook.entity.AutomobileDetail;
import com.alperenozcan.insurancenotebook.entity.Customer;

@Service
public class AutomobileDetailServiceImpl implements AutomobileDetailService {

	private AutomobileDetailRepository automobileDetailRepository;
	private CustomerRepository customerRepository;
	
	
	@Autowired
	public AutomobileDetailServiceImpl(AutomobileDetailRepository automobileDetailRepository,
			CustomerRepository customerRepository) {
		this.automobileDetailRepository = automobileDetailRepository;
		this.customerRepository = customerRepository;
	}

	@Override
	public List<AutomobileDetail> findAll() {
		return automobileDetailRepository.findAll();
	}

	@Override
	public AutomobileDetail findById(int theId) {
		Optional<AutomobileDetail> result = automobileDetailRepository.findById(theId);
		
		AutomobileDetail theAutomobileDetail = null;
		if (result.isPresent()) {
			theAutomobileDetail = result.get();
		}
		else {
			// we do not have any customerHealthDetail with given id
			throw new RuntimeException("Did not find AutomobileDetail with id: " + theId);
		}
		
		return theAutomobileDetail;
	}

	@Override
	public Optional<AutomobileDetail> findByCustomerId(int theCustomerId) {
		Optional<Customer> theCustomer = customerRepository.findById(theCustomerId);
		
		Optional<AutomobileDetail> theAutomobileDetail = Optional.empty();
		if(theAutomobileDetail.isPresent()) {
			Optional<AutomobileDetail> result = automobileDetailRepository.findByCustomerId(theCustomerId);	
			theAutomobileDetail = result;
		}
		else {
			throw new RuntimeException("Did not find AutomobileDetail with customerId: " + theCustomerId);
		}
		
		return theAutomobileDetail;
	}

	@Override
	public void save(AutomobileDetail theAutomobileDetail) {
		automobileDetailRepository.save(theAutomobileDetail);
	}

	@Override
	public void deleteById(int theId) {
		automobileDetailRepository.deleteById(theId);
	}

}
