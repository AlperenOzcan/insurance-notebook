package com.alperenozcan.insurancenotebook.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alperenozcan.insurancenotebook.dao.AutomobileDetailRepository;
import com.alperenozcan.insurancenotebook.dao.CustomerHealthDetailRepository;
import com.alperenozcan.insurancenotebook.dao.CustomerRepository;
import com.alperenozcan.insurancenotebook.dao.HouseRepository;
import com.alperenozcan.insurancenotebook.dao.InsuranceQuoteRepository;
import com.alperenozcan.insurancenotebook.entity.AutomobileDetail;
import com.alperenozcan.insurancenotebook.entity.Customer;
import com.alperenozcan.insurancenotebook.entity.CustomerHealthDetail;
import com.alperenozcan.insurancenotebook.entity.House;
import com.alperenozcan.insurancenotebook.entity.InsuranceQuote;

@Service
public class CustomerServiceImpl implements CustomerService {

	private CustomerRepository customerRepository;
	private CustomerHealthDetailRepository customerHealthDetailRepository;
	private InsuranceQuoteRepository insuranceQuoteRepository;
	private AutomobileDetailRepository automobileDetailRepository;
	private HouseRepository houseRepository;
	
	@Autowired
	public CustomerServiceImpl(CustomerRepository customerRepository, CustomerHealthDetailRepository customerHealthDetailRepository,
			InsuranceQuoteRepository insuranceQuoteRepository, AutomobileDetailRepository automobileDetailRepository,
			HouseRepository houseRepository) {
		this.customerRepository = customerRepository;
		this.customerHealthDetailRepository = customerHealthDetailRepository;
		this.insuranceQuoteRepository = insuranceQuoteRepository;
		this.automobileDetailRepository = automobileDetailRepository;
		this.houseRepository = houseRepository;
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
		
		// delete customer's Health Detail and related Insurance Quote
		Optional<CustomerHealthDetail> healthDetail = customerHealthDetailRepository.findByCustomerId(theId);
		if(healthDetail.isPresent()) {
			customerHealthDetailRepository.deleteById(healthDetail.get().getId());
			
			// delete related insurance quote
			Optional<List<InsuranceQuote>> healthInsuranceQuote = insuranceQuoteRepository.findByDetailId(healthDetail.get().getId());
			if (healthInsuranceQuote.isPresent()) {
				for (InsuranceQuote quote: healthInsuranceQuote.get()) {
					insuranceQuoteRepository.deleteByDetailIdAndInsuranceType(healthDetail.get().getId(), "Health");
				}
			}
			
		}
		
		// delete customer's automobile detail(s) and related Insurance Quote(s)
		Optional<List<AutomobileDetail>> automobileDetails = automobileDetailRepository.findByCustomerId(theId);
		if (automobileDetails.isPresent()) {
			List<AutomobileDetail> automobileDetailsList = automobileDetails.get();
			for (AutomobileDetail detail : automobileDetailsList) {
				automobileDetailRepository.deleteById(detail.getId());
				
				// delete related insurance quote
				Optional<List<InsuranceQuote>> automobileInsuranceQuote = insuranceQuoteRepository.findByDetailId(detail.getId());
				if (automobileInsuranceQuote.isPresent()) {
					for(InsuranceQuote quote: automobileInsuranceQuote.get()) {
						insuranceQuoteRepository.deleteByDetailIdAndInsuranceType(quote.getDetailId(), "Automobile");
					}
				}
			}	
		}
		
		// delete customer's house info and related Insurance Quotes(s)
		Optional<List<House>> house = houseRepository.findByCustomerId(theId);
		if(house.isPresent()) {
			List<House> houseList = house.get();
			for(House tempHouse: houseList) {
				houseRepository.deleteById(tempHouse.getId());
				
				// delete related insurance quote
				Optional<List<InsuranceQuote>> houseInsuranceQuote = insuranceQuoteRepository.findByDetailId(tempHouse.getId());
				if(houseInsuranceQuote.isPresent()) {
					for(InsuranceQuote quote: houseInsuranceQuote.get()) {
						insuranceQuoteRepository.deleteByDetailIdAndInsuranceType(quote.getDetailId(), "Earthquake");
					}
				}
			}
		}
		
		
		// finally, delete customer
		Optional<Customer> result = customerRepository.findById(theId);
		if (result.isEmpty()) {
			throw new RuntimeException("There is no customer with id " + theId);
		}		
		customerRepository.deleteById(theId);
		
	}

}
