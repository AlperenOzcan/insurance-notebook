package com.alperenozcan.insurancenotebook.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alperenozcan.insurancenotebook.entity.CustomerHealthDetail;

public interface CustomerHealthDetailRepository extends JpaRepository<CustomerHealthDetail, Integer> {

	Optional<CustomerHealthDetail> findByCustomerId(int theCustomerId);
}
