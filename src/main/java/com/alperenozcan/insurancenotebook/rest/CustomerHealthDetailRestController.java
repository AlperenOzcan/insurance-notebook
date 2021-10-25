package com.alperenozcan.insurancenotebook.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alperenozcan.insurancenotebook.entity.CustomerHealthDetail;
import com.alperenozcan.insurancenotebook.service.CustomerHealthDetailService;

@RestController
@RequestMapping("/api")
public class CustomerHealthDetailRestController {
	
	private CustomerHealthDetailService customerHealthDetailService;

	@Autowired
	public CustomerHealthDetailRestController (CustomerHealthDetailService theCustomerHealthDetailService) {
		customerHealthDetailService = theCustomerHealthDetailService;
	}

	@GetMapping("/customers-health-details")
	public List<CustomerHealthDetail> findAll() {
		return customerHealthDetailService.findAll();
	}

	@GetMapping("/customers-health-details/{customerHealthDetailId}")
	public CustomerHealthDetail getCustomerHealthDetail(@PathVariable int customerHealthDetailId) {
		
		CustomerHealthDetail theCustomerHealthDetail = customerHealthDetailService.findById(customerHealthDetailId);
		
		if (theCustomerHealthDetail == null) {
			throw new RuntimeException("There is no customer with id " + customerHealthDetailId);
		}
		
		return theCustomerHealthDetail;	
	}
	
	@PostMapping("/customers-health-details")
	public CustomerHealthDetail addCustomerHealthDetail(@RequestBody CustomerHealthDetail theCustomerHealthDetail) {
		
		theCustomerHealthDetail.setId(0);
		customerHealthDetailService.save(theCustomerHealthDetail);
		
		return theCustomerHealthDetail;
	}
	
	@PutMapping("/customers-health-details")
	public CustomerHealthDetail updateCustomerHealthDetail(@RequestBody CustomerHealthDetail theCustomerHealthDetail) {
		customerHealthDetailService.save(theCustomerHealthDetail);
		return theCustomerHealthDetail;
	}
	
	@DeleteMapping("/customers-health-details/{customerHealthDetailId}")
	public String deleteCustomerHealthDetail(@PathVariable int customerHealthDetailId) {
		
		CustomerHealthDetail tempCustomerHealthDetail = customerHealthDetailService.findById(customerHealthDetailId);
		
		if (tempCustomerHealthDetail == null) {
			throw new RuntimeException("There is no customer with id " + customerHealthDetailId);
		}
		
		customerHealthDetailService.deleteById(customerHealthDetailId);
		
		return "Customer Health Detail with id " + customerHealthDetailId + " is deleted";
	}
	
}
