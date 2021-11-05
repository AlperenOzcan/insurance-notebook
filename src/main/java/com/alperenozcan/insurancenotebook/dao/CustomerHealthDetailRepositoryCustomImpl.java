package com.alperenozcan.insurancenotebook.dao;

import java.util.List;
import java.util.Optional;

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
	public Optional<CustomerHealthDetail> findByCustomerId(int theCustomerId) {
		
		Query theQuery = entityManager.createQuery("from CustomerHealthDetail where customer_id=:tempCustomerId");
		
		theQuery.setParameter("tempCustomerId", theCustomerId);
		
		// execute query, get result
		List<CustomerHealthDetail> customerHealthDetails = theQuery.getResultList();

		// check is there any result & return it
		if (customerHealthDetails.isEmpty()) {
			return Optional.empty();
		}
		else {
			return Optional.of(customerHealthDetails.get(0));
		}
	}

}
