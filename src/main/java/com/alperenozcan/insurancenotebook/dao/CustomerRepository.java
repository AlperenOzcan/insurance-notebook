package com.alperenozcan.insurancenotebook.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alperenozcan.insurancenotebook.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	// no need to write more code. Able to use CRUD methods
}