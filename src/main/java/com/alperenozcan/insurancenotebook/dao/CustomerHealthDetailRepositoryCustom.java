package com.alperenozcan.insurancenotebook.dao;

import com.alperenozcan.insurancenotebook.entity.CustomerHealthDetail;

public interface CustomerHealthDetailRepositoryCustom {

	public CustomerHealthDetail findByCustomerId(int theCustomerId);
	
	public void deleteByCustomerId(int theCustomerId);
}
