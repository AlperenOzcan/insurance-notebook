package com.alperenozcan.insurancenotebook.service;

import java.util.List;
import java.util.Optional;

import com.alperenozcan.insurancenotebook.entity.House;

public interface HouseService {
	public House findById(int theId);
	
	public Optional<List<House>> findByCustomerId(int theCustomerId);
	
	public void save(House theHouse);
	
	public void deleteById(int theId);
}