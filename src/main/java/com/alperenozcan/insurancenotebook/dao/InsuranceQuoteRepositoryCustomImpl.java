package com.alperenozcan.insurancenotebook.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.alperenozcan.insurancenotebook.entity.InsuranceQuote;

public class InsuranceQuoteRepositoryCustomImpl implements InsuranceQuoteRepositoryCustom {

	private final InsuranceQuoteRepositoryBasic insuranceQuoteRepositoryBasic;
	
	private EntityManager entityManager;
	
	public InsuranceQuoteRepositoryCustomImpl(InsuranceQuoteRepositoryBasic insuranceQuoteRepositoryBasic,
			EntityManager entityManager) {
		this.insuranceQuoteRepositoryBasic = insuranceQuoteRepositoryBasic;
		this.entityManager = entityManager;
	}



	@Override
	public Optional<List<InsuranceQuote>> findByCustomerId(int theCustomerId) {
		Query theQuery = entityManager.createQuery("from InsuranceQuote where customer_id=:tempCustomerId");
		
		theQuery.setParameter("tempCustomerId", theCustomerId);
		
		// execute query, get result
		List<InsuranceQuote> customerHealthDetails = theQuery.getResultList();
		
		// return result
		return Optional.of(customerHealthDetails);
	}

}
