package com.alperenozcan.insurancenotebook.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alperenozcan.insurancenotebook.dao.CustomerHealthDetailRepository;
import com.alperenozcan.insurancenotebook.dao.CustomerRepository;
import com.alperenozcan.insurancenotebook.dao.InsuranceQuoteRepository;
import com.alperenozcan.insurancenotebook.entity.Customer;
import com.alperenozcan.insurancenotebook.entity.CustomerHealthDetail;
import com.alperenozcan.insurancenotebook.entity.InsuranceQuote;

@Service
public class CustomerServiceImpl implements CustomerService {

	private CustomerRepository customerRepository;
	
	private CustomerHealthDetailRepository customerHealthDetailRepository;
	private InsuranceQuoteRepository insuranceQuoteRepository;
	
	@Autowired
	public CustomerServiceImpl(CustomerRepository customerRepository, 
			CustomerHealthDetailRepository customerHealthDetailRepository, InsuranceQuoteRepository insuranceQuoteRepository) {
		this.customerRepository = customerRepository;
		this.customerHealthDetailRepository = customerHealthDetailRepository;
		this.insuranceQuoteRepository = insuranceQuoteRepository;
	}

	@Override
	@Transactional
	public List<Customer> findAll() {
		return customerRepository.findAll();
	}

	@Override
	@Transactional
	public Customer findById(int theId) {
		Optional<Customer> result = customerRepository.findById(theId);
		
		Customer theCustomer = null;
		if (result.isPresent()) {
			theCustomer = result.get();
		}
		else {
			// we do not have any customer with given id
			throw new RuntimeException("Did not find customer with id: " + theId);
		}
		
		return theCustomer;
	}

	@Override
	@Transactional
	public void save(Customer theCustomer) {
		customerRepository.save(theCustomer);
	}

	// delete customer and other customer related records
	@Override
	@Transactional
	public void deleteById(int theId) {
		
		Optional<CustomerHealthDetail> healthDetail = customerHealthDetailRepository.findByCustomerId(theId);
		if(healthDetail.isPresent()) {
			customerHealthDetailRepository.deleteById(healthDetail.get().getId());
		}
		
		Optional<List<InsuranceQuote>> insuranceQuotes = insuranceQuoteRepository.findByCustomerId(theId);
		if (insuranceQuotes.isPresent()) {
			List<InsuranceQuote> list = insuranceQuotes.get();
			for (InsuranceQuote quote : list){
				insuranceQuoteRepository.deleteById(quote.getId());
			}
		}
		
		Optional<Customer> result = customerRepository.findById(theId);
		
		if (result.isEmpty()) {
			throw new RuntimeException("There is no customer with id " + theId);
		}
		
		customerRepository.deleteById(theId);
		
	}

}
