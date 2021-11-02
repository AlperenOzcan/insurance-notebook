package com.alperenozcan.insurancenotebook.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alperenozcan.insurancenotebook.entity.CustomerHealthDetail;
import com.alperenozcan.insurancenotebook.service.CustomerHealthDetailService;

@Controller
@RequestMapping("/customer-health-details")
public class CustomerHealthDetailController {

private CustomerHealthDetailService customerHealthDetailService;
	
	@Autowired
	public CustomerHealthDetailController(CustomerHealthDetailService theCustomerHealthDetailService) {
		customerHealthDetailService = theCustomerHealthDetailService;
	}
	
	@GetMapping("/list")
	public String listCustomersHealthDetails(Model theModel) {
		
		// get health details from database
		List<CustomerHealthDetail> theCustomersHealthDetails = customerHealthDetailService.findAll(); 
	
		// add theCustomersHealthDetails into theModel with name "customersHealthDetails"
		theModel.addAttribute("customersHealthDetails", theCustomersHealthDetails);
		
		return "health-details/list-customersHealthDetails";
	}
	
	/*
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		
		CustomerHealthDetail theCustomerHealthDetail = new CustomerHealthDetail();
		
		theModel.addAttribute("customersHealthDetail", theCustomerHealthDetail);
		
		return "health-details/customersHealthDetails-form";
	}
	*/
	
	/*
	@PostMapping("/save")
	public String saveCustomerHealthDetail(@ModelAttribute("customerHealthDetail") CustomerHealthDetail theCustomerHealthDetail) {
		
		customerHealthDetailService.save(theCustomerHealthDetail);
		
		// to prevent duplicate submission we use redirect
		return "redirect:/customer-health-details/list";
	}
	*/
	
	/*
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("customerHealthDetailId") int theId, Model theModel) {
		
		CustomerHealthDetail theCustomerHealthDetail = customerHealthDetailService.findById(theId);
		
		theModel.addAttribute("customerHealthDetail", theCustomerHealthDetail);
		
		return "/customer-health-details/customersHealthDetails-form";
	}
	*/
	
	@GetMapping("/delete")
	public String deleteCustomerHealthDetail(@RequestParam("customerHealthDetailId") int theId) {
		
		customerHealthDetailService.deleteById(theId);
		
		return "redirect:/customer-health-details/list";
	}
	
	
}
