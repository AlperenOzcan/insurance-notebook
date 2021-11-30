package com.alperenozcan.insurancenotebook.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alperenozcan.insurancenotebook.dao.AutomobileDetailRepository;
import com.alperenozcan.insurancenotebook.dao.CustomerHealthDetailRepository;
import com.alperenozcan.insurancenotebook.dao.CustomerRepository;
import com.alperenozcan.insurancenotebook.dao.InsuranceQuoteRepository;
import com.alperenozcan.insurancenotebook.entity.AutomobileDetail;
import com.alperenozcan.insurancenotebook.entity.Customer;
import com.alperenozcan.insurancenotebook.entity.CustomerHealthDetail;

@Service
public class AutomobileDetailServiceImpl implements AutomobileDetailService {

	private AutomobileDetailRepository automobileDetailRepository;
	private CustomerRepository customerRepository;
	private InsuranceQuoteRepository insuranceQuoteRepository;
	private CustomerHealthDetailRepository customerHealthDetailRepository;
	
	@Autowired
	public AutomobileDetailServiceImpl(AutomobileDetailRepository automobileDetailRepository,
			CustomerRepository customerRepository, InsuranceQuoteRepository insuranceQuoteRepository,
			CustomerHealthDetailRepository customerHealthDetailRepository) {
		this.automobileDetailRepository = automobileDetailRepository;
		this.customerRepository = customerRepository;
		this.insuranceQuoteRepository = insuranceQuoteRepository;
		this.customerHealthDetailRepository = customerHealthDetailRepository;
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
		int healthScore = calculateHealthDetailScore(theAutomobileDetail.getCustomer().getId());
		theAutomobileDetail.setHealth_score(healthScore);
		automobileDetailRepository.save(theAutomobileDetail);
	}

	@Override
	@Transactional
	public void deleteById(int theId) {
		automobileDetailRepository.deleteById(theId);
		insuranceQuoteRepository.deleteByDetailIdAndInsuranceType(theId, "Automobile");
	}
	
	private int calculateHealthDetailScore(int customerId) {
		int score = 9; // max 9
		Optional<CustomerHealthDetail> returned = customerHealthDetailRepository.findByCustomerId(customerId);
		if (!returned.isPresent()) {
			throw new RuntimeException("No such customer health detail with customer id: " + customerId);
		}
		CustomerHealthDetail healthDetail = returned.get();
		score -= (healthDetail.isHadHeartAttack()) ? 5 : 0;
		score -= (healthDetail.isHasDiabetes()) ? 4 : 0;
		
		return score;
	}

}
