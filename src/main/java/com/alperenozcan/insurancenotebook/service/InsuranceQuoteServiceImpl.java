package com.alperenozcan.insurancenotebook.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alperenozcan.insurancenotebook.dao.CustomerRepository;
import com.alperenozcan.insurancenotebook.dao.InsuranceQuoteRepository;
import com.alperenozcan.insurancenotebook.entity.Customer;
import com.alperenozcan.insurancenotebook.entity.InsuranceQuote;

@Service
public class InsuranceQuoteServiceImpl implements InsuranceQuoteService {

	private InsuranceQuoteRepository insuranceQuoteRepository;
	
	private CustomerRepository customerRepository;
	
	@Autowired
	public InsuranceQuoteServiceImpl(InsuranceQuoteRepository insuranceQuoteRepository,
			CustomerRepository customerRepository) {
		this.insuranceQuoteRepository = insuranceQuoteRepository;
		this.customerRepository = customerRepository;
	}

	@Override
	public List<InsuranceQuote> findAll() {
		return insuranceQuoteRepository.findAll();
	}

	@Override
	public InsuranceQuote findById(int theId) {
		Optional<InsuranceQuote> result = insuranceQuoteRepository.findById(theId);
		
		InsuranceQuote theInsuranceQuote = null;
		if (result.isPresent()) {
			theInsuranceQuote = result.get();
		}
		else {
			throw new RuntimeException("Did not find InsuranceQuote with id: " + theId);
		}
		
		return theInsuranceQuote;
	}

	@Override
	public InsuranceQuote findByCustomerId(int theCustomerId) {
		Optional<Customer> theCustomer = customerRepository.findById(theCustomerId);
		
		InsuranceQuote theInsuranceQuote = null;
		if (theCustomer.isPresent()) {
			InsuranceQuote result = insuranceQuoteRepository.findByCustomerId(theCustomerId);
			
			theInsuranceQuote = result;
		}
		else {
			throw new RuntimeException("Did not find InsuranceQuote with customerId: " + theCustomerId);
		}
		
		return theInsuranceQuote;
	}

	@Override
	public void save(InsuranceQuote theInsuranceQuote) {
		insuranceQuoteRepository.save(theInsuranceQuote);
	}

	@Override
	public void deleteById(int theId) {
		insuranceQuoteRepository.deleteById(theId);
	}

}