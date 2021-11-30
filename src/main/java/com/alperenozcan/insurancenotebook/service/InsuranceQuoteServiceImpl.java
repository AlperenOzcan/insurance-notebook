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
import com.alperenozcan.insurancenotebook.entity.InsuranceQuote;

@Service
public class InsuranceQuoteServiceImpl implements InsuranceQuoteService {

	private InsuranceQuoteRepository insuranceQuoteRepository;
	private CustomerHealthDetailRepository customerHealthDetailRepository;
	private CustomerRepository customerRepository;
	private AutomobileDetailRepository automobileDetailRepository;
	
	@Autowired
	public InsuranceQuoteServiceImpl(InsuranceQuoteRepository insuranceQuoteRepository,
			CustomerHealthDetailRepository customerHealthDetailRepository, CustomerRepository customerRepository,
			AutomobileDetailRepository automobileDetailRepository) {
		this.insuranceQuoteRepository = insuranceQuoteRepository;
		this.customerHealthDetailRepository = customerHealthDetailRepository;
		this.customerRepository = customerRepository;
		this.automobileDetailRepository = automobileDetailRepository;
	}

	@Override
	public List<InsuranceQuote> findAll() {
		return insuranceQuoteRepository.findAll();
	}

	@Override
	public InsuranceQuote findById(int theId) {
		
		if (insuranceQuoteRepository.findById(theId).isPresent()) {
			return insuranceQuoteRepository.findById(theId).get();
		}
		throw new RuntimeException("No such insurance quote with id: " + theId);
	}

	@Override
	public List<InsuranceQuote> findByDetailId(int theDetailId) {
		Optional<List<InsuranceQuote>> list = insuranceQuoteRepository.findByDetailId(theDetailId);
		
		if (list.isPresent()) {
			return list.get();
		}
		throw new RuntimeException("No insurance quote with detailId: " + theDetailId);
	}

	@Override
	public List<InsuranceQuote> findByDetailIdAndInsuranceType(int theDetailId, String theType) {
		Optional<List<InsuranceQuote>> list = insuranceQuoteRepository.findByDetailIdAndInsuranceType(theDetailId, theType);
		if (list.isPresent()) {
			return list.get();
		}
		throw new RuntimeException("No insurance quote with detailId: " + theDetailId);
	}

	@Override
	public void save(InsuranceQuote theInsuranceQuote) {
		// double premium = calculatePremium(theInsuranceQuote);

		double premium = calculatePremium(theInsuranceQuote);
		theInsuranceQuote.setPremium(premium);
		insuranceQuoteRepository.save(theInsuranceQuote);
	}

	@Override
	@Transactional
	public void deleteById(int theId) {
		insuranceQuoteRepository.deleteById(theId);
	}

	@Override
	@Transactional
	public void deleteByDetailIdAndInsuranceType(int theDetailId, String theType) {
		insuranceQuoteRepository.deleteByDetailIdAndInsuranceType(theDetailId, theType);
	}

	private double calculatePremium(InsuranceQuote theInsuranceQuote) {
		// get insurance type
		String type = theInsuranceQuote.getInsuranceType();
		int detailId = theInsuranceQuote.getDetailId();
		
		switch(type) {
			case "Health":
				return calculateHealthInsurancePremium(detailId);
			case "Automobile":
				return calculateAutomobileInsurancePremium(detailId);
			case "Earthquake":
				return calculateEartquakeInsurancePremium(detailId);
			default:
				throw new RuntimeException("No such insurance type.\n -->Type: " + type + "\n -->Detail ID: " + detailId);
		}
	}
	
	private double calculateHealthInsurancePremium(int theDetailId) {
		CustomerHealthDetail customerHealthDetail = customerHealthDetailRepository.findById(theDetailId).get();
		Customer customer = customerRepository.findById(customerHealthDetail.getCustomer().getId()).get();

		// Gender=0 means male, =1 female
		double premium = (customer.isGender()) ? 1000 : 1200;
		
		// body-mass index
		premium += (customerHealthDetail.getWeight() / Math.pow(customerHealthDetail.getHeight()/100.0, 2.0)) * 50;
		
		// other health details
		premium += (customerHealthDetail.isHadCancer()) ? 2000 : 0;
		premium += (customerHealthDetail.isHadHeartAttack()) ? 1500 : 0;
		premium += (customerHealthDetail.isHasDiabetes()) ? 1000 : 0;
		
		return premium;
	}


	private double calculateAutomobileInsurancePremium(int theDetailId) {
		AutomobileDetail automobileDetail = automobileDetailRepository.findById(theDetailId).get(); // this line may need to be changed
		
		double premium = 0.0;
		premium += automobileDetail.getHealth_score()*250;
		premium += (automobileDetail.getAge())*100;
		premium += (automobileDetail.getKilometer())*0.01;
		if((15-(automobileDetail.getExperience())) > 0) {
			premium += 15-(automobileDetail.getExperience())*50;
		}
		premium += (9 - automobileDetail.getHealth_score())*200; // max health score can be 9
		
		return premium;
	}
	
	private double calculateEartquakeInsurancePremium(int theCustomerId) {
		return 124.0;
	}
	

}
