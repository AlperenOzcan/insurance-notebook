package com.alperenozcan.insurancenotebook.dao;

import java.util.Optional;

import com.alperenozcan.insurancenotebook.entity.CustomerHealthDetail;

public interface CustomerHealthDetailRepositoryCustom {

	public Optional<CustomerHealthDetail> findByCustomerId(int theCustomerId);
	
}
