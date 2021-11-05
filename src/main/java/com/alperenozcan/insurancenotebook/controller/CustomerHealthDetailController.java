package com.alperenozcan.insurancenotebook.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alperenozcan.insurancenotebook.entity.Customer;
import com.alperenozcan.insurancenotebook.entity.CustomerHealthDetail;
import com.alperenozcan.insurancenotebook.service.CustomerHealthDetailService;
import com.alperenozcan.insurancenotebook.service.CustomerService;

@Controller
@RequestMapping("/customer-health-details")
public class CustomerHealthDetailController {

	private CustomerHealthDetailService customerHealthDetailService;
	private CustomerService customerService;
	
	@Autowired
	public CustomerHealthDetailController(CustomerHealthDetailService theCustomerHealthDetailService, CustomerService theCustomerService) {
		customerHealthDetailService = theCustomerHealthDetailService;
		customerService = theCustomerService;
	}
	
	@GetMapping("/list")
	public String listCustomersHealthDetails(Model theModel) {
		
		// get health details from database
		List<CustomerHealthDetail> theCustomersHealthDetails = customerHealthDetailService.findAll(); 
	
		// add theCustomersHealthDetails into theModel with name "customersHealthDetails"
		theModel.addAttribute("customersHealthDetails", theCustomersHealthDetails);
		
		return "health-details/list-customersHealthDetails";
	}

	
	@PostMapping("/save")
	public String saveCustomerHealthDetail(@ModelAttribute("customerHealthDetail") CustomerHealthDetail theCustomerHealthDetail) {
		
		customerHealthDetailService.save(theCustomerHealthDetail);
		
		// to prevent duplicate submission we use redirect
		return "redirect:/customers/list";
	}
	
	
	
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("customerId") int theId, Model theModel) {
		
		Optional<CustomerHealthDetail> theCustomerHealthDetail = customerHealthDetailService.findByCustomerId(theId);
		
		if (theCustomerHealthDetail.isPresent()) {
			
			theModel.addAttribute("customerHealthDetail", theCustomerHealthDetail.get());
		}
		else {
			
			Customer tempCustomer = customerService.findById(theId);
			theModel.addAttribute("customerHealthDetail", new CustomerHealthDetail(0, 0, false, false, false, tempCustomer));
		}

		return "health-details/customersHealthDetails-form";		
	}
	
	
	@GetMapping("/delete")
	public String deleteCustomerHealthDetail(@RequestParam("customerHealthDetailId") int theId) {
		
		customerHealthDetailService.deleteById(theId);
		
		return "redirect:/customer-health-details/list";
	}
	
	
}
