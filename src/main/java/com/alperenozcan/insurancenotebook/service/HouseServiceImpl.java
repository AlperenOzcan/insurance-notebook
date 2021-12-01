package com.alperenozcan.insurancenotebook.service;

import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.alperenozcan.insurancenotebook.dao.CustomerRepository;
import com.alperenozcan.insurancenotebook.dao.HouseRepository;
import com.alperenozcan.insurancenotebook.dao.InsuranceQuoteRepository;
import com.alperenozcan.insurancenotebook.entity.Customer;
import com.alperenozcan.insurancenotebook.entity.House;

public class HouseServiceImpl implements HouseService {

	private HouseRepository houseRepository;
	private CustomerRepository customerRepository;
	private InsuranceQuoteRepository insuranceQuoteRepository;
	
	public HouseServiceImpl(HouseRepository houseRepository, CustomerRepository customerRepository,
			InsuranceQuoteRepository insuranceQuoteRepository) {
		this.houseRepository = houseRepository;
		this.customerRepository = customerRepository;
		this.insuranceQuoteRepository = insuranceQuoteRepository;
	}
	
	@Override
	public House findById(int theId) {
		Optional<House> result = houseRepository.findById(theId);
		
		House theHouse = null;		
		if (result.isPresent()) {
			theHouse = result.get();
		}
		else {
			throw new RuntimeException("Did not find house with id: " + theId);
		}
		
		return theHouse;
	}

	@Override
	public Optional<List<House>> findByCustomerId(int theCustomerId) {
		Optional<Customer> theCustomer = customerRepository.findById(theCustomerId);
		
		Optional<List<House>> result = Optional.empty();
		if(theCustomer.isPresent()) {
			Optional<List<House>> houses = houseRepository.findByCustomerId(theCustomerId);	
			result = houses;
		}
		else {
			throw new RuntimeException("Did not find customer with customerId: " + theCustomerId);
		}
		
		return result;
	}

	@Override
	public void save(House theHouse) {
		houseRepository.save(theHouse);
	}

	@Override
	@Transactional
	public void deleteById(int theId) {
		houseRepository.deleteById(theId);
		insuranceQuoteRepository.deleteByDetailIdAndInsuranceType(theId, "Earthquake");
	}

}
