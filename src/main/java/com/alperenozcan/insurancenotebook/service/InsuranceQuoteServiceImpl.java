package com.alperenozcan.insurancenotebook.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alperenozcan.insurancenotebook.dao.CustomerHealthDetailRepository;
import com.alperenozcan.insurancenotebook.dao.CustomerRepository;
import com.alperenozcan.insurancenotebook.dao.InsuranceQuoteRepository;
import com.alperenozcan.insurancenotebook.entity.Customer;
import com.alperenozcan.insurancenotebook.entity.CustomerHealthDetail;
import com.alperenozcan.insurancenotebook.entity.InsuranceQuote;

@Service
public class InsuranceQuoteServiceImpl implements InsuranceQuoteService {

	private InsuranceQuoteRepository insuranceQuoteRepository;
	private CustomerRepository customerRepository;
	private CustomerHealthDetailRepository customerHealthDetailRepository;
	
	@Autowired
	public InsuranceQuoteServiceImpl(InsuranceQuoteRepository insuranceQuoteRepository,
			CustomerRepository customerRepository, CustomerHealthDetailRepository customerHealthDetailRepository) {
		this.insuranceQuoteRepository = insuranceQuoteRepository;
		this.customerRepository = customerRepository;
		this.customerHealthDetailRepository = customerHealthDetailRepository;
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
	public Optional<List<InsuranceQuote>> findByCustomerId(int theCustomerId) {
		Optional<Customer> theCustomer = customerRepository.findById(theCustomerId);
		
		Optional<List<InsuranceQuote>> result = Optional.empty();
		if (theCustomer.isPresent()) {
			Optional<List<InsuranceQuote>> insuranceQuotes = insuranceQuoteRepository.findByCustomerId(theCustomerId);
			result = insuranceQuotes;
		}
		else {
			// throw new RuntimeException("Did not find InsuranceQuote with customerId: " + theCustomerId);
			System.out.println("Did not find insuranceQuote with customerId: " + theCustomerId);
		}
		
		return result;
	}

	@Override
	public void save(InsuranceQuote theInsuranceQuote) {
		double premium = calculatePremium(theInsuranceQuote.getCustomerId().getId());

		theInsuranceQuote.setPremium(premium);
		
		insuranceQuoteRepository.save(theInsuranceQuote);
	}

	@Override
	public void deleteById(int theId) {
		insuranceQuoteRepository.deleteById(theId);
	}

	
	private double calculatePremium(int theCustomerId) {
		CustomerHealthDetail customerHealthDetail = customerHealthDetailRepository.findByCustomerId(theCustomerId).get();
		Customer customer = customerRepository.findById(theCustomerId).get();
		
		if (customerHealthDetail == null) {
			return 1000000000.00;		// 1 Billion
		}
		
		// Gender=0 means male, =1 female
		double premium = (customer.isGender()) ? 0 : 200;
		
		// body-mass index
		premium += (customerHealthDetail.getWeight() / Math.pow(customerHealthDetail.getHeight()/100.0, 2.0)) * 50;
		
		// other health details
		premium += (customerHealthDetail.isHadCancer()) ? 2000 : 0;
		premium += (customerHealthDetail.isHadHeartAttack()) ? 1500 : 0;
		premium += (customerHealthDetail.isHasDiabetes()) ? 1000 : 0;
		
		return premium;
	}

	
	
}
