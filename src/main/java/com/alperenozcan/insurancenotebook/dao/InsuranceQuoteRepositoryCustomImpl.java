package com.alperenozcan.insurancenotebook.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.alperenozcan.insurancenotebook.entity.CustomerHealthDetail;
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
	public InsuranceQuote findByCustomerId(int theCustomerId) {
		Query theQuery = entityManager.createQuery("from InsuranceQuote where customer_id=:tempCustomerId");
		
		theQuery.setParameter("tempCustomerId", theCustomerId);
		
		// execute query, get result
		List<InsuranceQuote> customerHealthDetails = theQuery.getResultList();
		
		// return result
		return customerHealthDetails.get(0);
	}

}
