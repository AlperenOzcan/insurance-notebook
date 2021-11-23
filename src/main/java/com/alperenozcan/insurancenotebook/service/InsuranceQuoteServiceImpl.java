package com.alperenozcan.insurancenotebook.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	private CustomerRepository customerRepository;
	private CustomerHealthDetailRepository customerHealthDetailRepository;
	private AutomobileDetailRepository automobileDetailRepository;
	
	@Autowired
	public InsuranceQuoteServiceImpl(InsuranceQuoteRepository insuranceQuoteRepository,
			CustomerRepository customerRepository, CustomerHealthDetailRepository customerHealthDetailRepository,
			AutomobileDetailRepository automobileDetailRepository) {
		this.insuranceQuoteRepository = insuranceQuoteRepository;
		this.customerRepository = customerRepository;
		this.customerHealthDetailRepository = customerHealthDetailRepository;
		this.automobileDetailRepository = automobileDetailRepository;
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
		double premium = calculatePremium(theInsuranceQuote);

		theInsuranceQuote.setPremium(premium);
		
		insuranceQuoteRepository.save(theInsuranceQuote);
	}

	@Override
	public void deleteById(int theId) {
		insuranceQuoteRepository.deleteById(theId);
	}

	
	private double calculatePremium(InsuranceQuote theInsuranceQuote) {
		
		// get insurance type
		String type = theInsuranceQuote.getInsuranceType();
		int theCustomerId = theInsuranceQuote.getCustomerId().getId();
		int quoteId = theInsuranceQuote.getId();
		
		switch(type) {
			case "Health":
				return calculateHealthInsurancePremium(theCustomerId);
			case "Automobile":
				return calculateAutomobileInsurancePremium(theCustomerId, quoteId);
			case "Earthquake":
				return calculateEartquakeInsurancePremium(theCustomerId);
			default:
				throw new RuntimeException("No such insurance type. Type: " + type);
		}
	}

	
	private double calculateHealthInsurancePremium(int theCustomerId) {
		CustomerHealthDetail customerHealthDetail = customerHealthDetailRepository.findByCustomerId(theCustomerId).get();
		Customer customer = customerRepository.findById(theCustomerId).get();

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
	
	private double calculateAutomobileInsurancePremium(int theCustomerId, int quoteId) {
		AutomobileDetail automobileDetail = automobileDetailRepository.findById(quoteId).get(); // this line may need to be changed
		CustomerHealthDetail customerHealthDetail = customerHealthDetailRepository.findByCustomerId(theCustomerId).get();
		
		double premium = 0.0;
		
		automobileDetail.setHealth_score(calculateHealthScore(customerHealthDetail));
		premium += automobileDetail.getHealth_score()*250;
		premium += (automobileDetail.getAge())*100;
		premium += (automobileDetail.getKilometer())*0.01;
		if((15-(automobileDetail.getExperience())) > 0) {
			premium += 15-(automobileDetail.getExperience())*50;
		}
		
		return premium;
	}
	
	private double calculateEartquakeInsurancePremium(int theCustomerId) {
		
		return 124.0;
	}
	
	
	private int calculateHealthScore(CustomerHealthDetail customerHealthDetail) {
		int score = 0;
		
		score += (customerHealthDetail.isHadHeartAttack()) ? 5 : 0;
		score += (customerHealthDetail.isHasDiabetes()) ? 4 : 0;
		
		return score;
	}
}
