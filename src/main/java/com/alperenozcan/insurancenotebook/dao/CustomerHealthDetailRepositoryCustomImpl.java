package com.alperenozcan.insurancenotebook.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.alperenozcan.insurancenotebook.entity.CustomerHealthDetail;

public class CustomerHealthDetailRepositoryCustomImpl implements CustomerHealthDetailRepositoryCustom {

	private final CustomerHealthDetailRepositoryBasic customerHealthDetailRepositoryBasic;
	
	private EntityManager entityManager;
	
	public CustomerHealthDetailRepositoryCustomImpl(CustomerHealthDetailRepositoryBasic customerHealthDetailRepositoryBasic,
			EntityManager entityManager) {
		this.customerHealthDetailRepositoryBasic = customerHealthDetailRepositoryBasic;
		this.entityManager = entityManager;
	}
	
	@Override
	public CustomerHealthDetail findByCustomerId(int theCustomerId) {
		
		Query theQuery = entityManager.createQuery("from CustomerHealthDetail where customerId=:tempCustomerId");
		
		theQuery.setParameter("tempCustomerId", theCustomerId);
		
		// execute query, get result
		List<CustomerHealthDetail> customerHealthDetails = theQuery.getResultList();
		
		// return result
		return customerHealthDetails.get(0);
	}

}
