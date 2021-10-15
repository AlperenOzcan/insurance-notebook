package com.alperenozcan.insurancenotebook.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.alperenozcan.insurancenotebook.entity.Customer;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

	private EntityManager entityManager;
	
	@Autowired
	public CustomerDAOImpl (EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}
	
	@Override
	public List<Customer> findAll() {
		
		// query
		Query theQuery = entityManager.createQuery("from Customer");
		
		
		// execute query, get result
		List<Customer> customers = theQuery.getResultList();
		
		
		// return result
		return customers;
	}
	
	@Override
	public Customer findById(int theId) {
		
		// get the customer
		Customer theCustomer = entityManager.find(Customer.class, theId);
		
		return theCustomer;
	}
	
	@Override
	public void save(Customer theCustomer) {
		
		// save or update
		Customer dbCustomer = entityManager.merge(theCustomer);
		
		// update id
		theCustomer.setId(dbCustomer.getId());
	}
	
	@Override
	public void deleteById(int theId) {
		
		Query theQuery = entityManager.createQuery("delete from Customer where id=:employeeId");
	
		theQuery.setParameter("employeeId", theId);
		
		theQuery.executeUpdate();
	}

}
