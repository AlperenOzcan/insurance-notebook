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
			// we do not have any AutomobileDetail with given id
			throw new RuntimeException("Did not find AutomobileDetail with id: " + theId);
		}
		
		return theAutomobileDetail;
	}

	@Override
	public Optional<List<AutomobileDetail>> findByCustomerId(int theCustomerId) {
		Optional<Customer> theCustomer = customerRepository.findById(theCustomerId);
		
		Optional<List<AutomobileDetail>>  result = Optional.empty();
		if(theCustomer.isPresent()) {
			Optional<List<AutomobileDetail>> automobileDetails = automobileDetailRepository.findByCustomerId(theCustomerId);	
			result = automobileDetails;
		}
		else {
			throw new RuntimeException("Did not find customer with customerId: " + theCustomerId);
		}
		
		return result;
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
