package com.alperenozcan.insurancenotebook.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alperenozcan.insurancenotebook.dao.CustomerHealthDetailDAO;
import com.alperenozcan.insurancenotebook.entity.CustomerHealthDetail;

@Service
public class CustomerHealthDetailServiceImpl implements CustomerHealthDetailService {

	private CustomerHealthDetailDAO customerHealthDetailDAO;
	
	@Autowired
	public CustomerHealthDetailServiceImpl(CustomerHealthDetailDAO theCustomerHealthDetailDAO) {
		customerHealthDetailDAO = theCustomerHealthDetailDAO;
	}
	
	
	@Override
	@Transactional
	public List<CustomerHealthDetail> findAll() {
		return customerHealthDetailDAO.findAll();
	}

	@Override
	@Transactional
	public CustomerHealthDetail findById(int theId) {
		return customerHealthDetailDAO.findById(theId);
	}

	@Override
	@Transactional
	public void save(CustomerHealthDetail theCustomerHealthDetail) {
		customerHealthDetailDAO.save(theCustomerHealthDetail);
	}

	@Override
	@Transactional
	public void deleteById(int theId) {
		customerHealthDetailDAO.deleteById(theId);
	}

}
