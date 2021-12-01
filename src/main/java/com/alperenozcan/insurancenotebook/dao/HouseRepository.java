package com.alperenozcan.insurancenotebook.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.alperenozcan.insurancenotebook.entity.House;

public interface HouseRepository extends JpaRepository<House, Integer> {
	
	@Query("from House where customer_id=:theCustomerId")
	Optional<List<House>> findByCustomerId(int theCustomerId);
	
	@Query("from House where customer_id=:theCustomerId and id=:theHouseId")
	Optional<House> findByCustomerAndHouseId(int theCustomerId, int theHouseId);
}