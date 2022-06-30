package com.alperenozcan.insurancenotebook.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alperenozcan.insurancenotebook.entity.House;

public interface HouseRepository extends JpaRepository<House, Integer> {
	
	Optional<List<House>> findByCustomerId(int theCustomerId);
}