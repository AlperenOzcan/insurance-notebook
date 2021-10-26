package com.alperenozcan.insurancenotebook.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alperenozcan.insurancenotebook.entity.CustomerHealthDetail;

public interface CustomerHealthDetailRepositoryBasic extends JpaRepository<CustomerHealthDetail, Integer> {

}
