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
		
		Query theQuery = entityManager.createQuery("from CustomerHealthDetail where customer_id=:tempCustomerId");
		
		theQuery.setParameter("tempCustomerId", theCustomerId);
		
		// execute query, get result
		List<CustomerHealthDetail> customerHealthDetails = theQuery.getResultList();
		
		// return result
		return customerHealthDetails.get(0);
	}

	@Override
	public void deleteByCustomerId(int theCustomerId) {
		
		Query theQuery = entityManager.createQuery("delete from CustomerHealthDetail where customerId=:customerHealthDetailId");
		
		
		theQuery.setParameter("theCustomerId", theCustomerId);
				
		theQuery.executeUpdate();
	}

}
