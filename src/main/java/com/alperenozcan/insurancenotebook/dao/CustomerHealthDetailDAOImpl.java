package com.alperenozcan.insurancenotebook.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.alperenozcan.insurancenotebook.entity.CustomerHealthDetail;

@Repository
public class CustomerHealthDetailDAOImpl implements CustomerHealthDetailDAO {

	private EntityManager entityManager;
	
	@Autowired
	public CustomerHealthDetailDAOImpl(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}
	
	@Override
	public List<CustomerHealthDetail> findAll() {
		
		// query
		//Query theQuery = entityManager.createQuery("from Customer_health_detail");
		Query theQuery = entityManager.createQuery("from CustomerHealthDetail");

		
		// execute query, get result
		List<CustomerHealthDetail> customerHealthDetails = theQuery.getResultList();
				
		// return result
		return customerHealthDetails;
	}

	@Override
	public CustomerHealthDetail findById(int theId) {

		// get the customerHealthDetail
		CustomerHealthDetail theCustomerHealthDetail = entityManager.find(CustomerHealthDetail.class, theId);
				
		return theCustomerHealthDetail;
	}

	@Override
	public void save(CustomerHealthDetail theCustomerHealthDetail) {
		
		// save or update
		CustomerHealthDetail dbCustomerHealthDetail = entityManager.merge(theCustomerHealthDetail);
				
		// update id
		theCustomerHealthDetail.setId(dbCustomerHealthDetail.getId());
	}

	@Override
	public void deleteById(int theId) {

		// Query theQuery = entityManager.createQuery("delete from Customer_health_detail where id=:customerHealthDetailId");
		Query theQuery = entityManager.createQuery("delete from CustomerHealthDetail where id=:customerHealthDetailId");
		
		theQuery.setParameter("customerHealthDetailId", theId);
		
		theQuery.executeUpdate();

	}

}
