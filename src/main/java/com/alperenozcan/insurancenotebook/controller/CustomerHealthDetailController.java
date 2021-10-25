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
@RequestMapping("/customershealthdetails")
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
		
		return "customershealthdetails/list-customersHealthDetails";
	}
	
	/*
	// list only one customer's health details
	@GetMapping("/theCustomerhealthdetails")
	public String theCustomerHealthDetails(@RequestParam int customerHealthDetailId, Model theModel) {
		
		// get health details from database
		CustomerHealthDetail theCustomersHealthDetails = customerHealthDetailService.findById(customerHealthDetailId); 
	
		// add the customer's health details into theModel with name "customersHealthDetails"
		theModel.addAttribute("customersHealthDetails", theCustomersHealthDetails);
		
		return "customershealthdetails/list-customersHealthDetails";
	}
	*/
	
	// list only one customer's health details
	@GetMapping("/theCustomerhealthdetails")
	public String theCustomerHealthDetails(@RequestParam int customerId, Model theModel) {
			
		// get health details from database
		CustomerHealthDetail theCustomersHealthDetails = customerHealthDetailService.findByCustomerId(customerId); 
		
		// add the customer's health details into theModel with name "customersHealthDetails"
		theModel.addAttribute("customersHealthDetails", theCustomersHealthDetails);
			
		return "customershealthdetails/list-customersHealthDetails";
	}
		
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		
		CustomerHealthDetail theCustomerHealthDetail = new CustomerHealthDetail();
		
		theModel.addAttribute("customersHealthDetail", theCustomerHealthDetail);
		
		return "customershealthdetails/customersHealthDetails-form";
	}
	
	@PostMapping("/save")
	public String saveCustomerHealthDetail(@ModelAttribute("customerHealthDetail") CustomerHealthDetail theCustomerHealthDetail) {
		
		/*
		byte isMale = (byte) (theCustomer.isGender() == true ? 0 : 1);
		byte hadCancer = (byte) (theCustomer.isHadCancer() == true ? 1 : 0);
		byte hadHeartAttack = (byte) (theCustomer.isHadHeartAttack() == true ? 1 : 0);
		byte hasDiabetes = (byte) (theCustomer.isHasDiabetes() == true ? 1 : 0);
		
		// initial cost
		double cost = 1000;
		cost += isMale*200 + hadCancer*300 + hadHeartAttack*200 + hasDiabetes*100;
		
		theCustomer.setCost(cost); */
		customerHealthDetailService.save(theCustomerHealthDetail);
		
		// to prevent duplicate submission we use redirect
		return "redirect:/customershealthdetails/list";
	}
	
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("customerHealthDetailId") int theId, Model theModel) {
		
		CustomerHealthDetail theCustomerHealthDetail = customerHealthDetailService.findById(theId);
		
		theModel.addAttribute("customerHealthDetail", theCustomerHealthDetail);
		
		return "/customershealthdetails/customersHealthDetails-form";
	}
	
	@GetMapping("/delete")
	public String deleteCustomerHealthDetail(@RequestParam("customerHealthDetailId") int theId) {
		
		customerHealthDetailService.deleteById(theId);
		
		return "redirect:/customershealthdetails/list";
	}
	
	
	
	
	
	
	
}
