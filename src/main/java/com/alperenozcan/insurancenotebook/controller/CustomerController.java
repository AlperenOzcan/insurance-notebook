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
import com.alperenozcan.insurancenotebook.entity.InsuranceQuote;
import com.alperenozcan.insurancenotebook.service.CustomerHealthDetailService;
import com.alperenozcan.insurancenotebook.service.CustomerService;
import com.alperenozcan.insurancenotebook.service.InsuranceQuoteService;

@Controller
@RequestMapping("/customers")
public class CustomerController {

	private CustomerService customerService;
	private CustomerHealthDetailService customerHealthDetailService;
	private InsuranceQuoteService insuranceQuoteService;
	
	@Autowired
	public CustomerController (CustomerService theCustomerService, CustomerHealthDetailService theCustomerHealthDetailService,
			InsuranceQuoteService theInsuranceQuoteService) {
		customerService = theCustomerService;
		customerHealthDetailService = theCustomerHealthDetailService;
		insuranceQuoteService = theInsuranceQuoteService;
	}
	
	@GetMapping("/list")
	public String listCustomers(Model theModel) {
		
		// get customers from database
		List<Customer> theCustomers = customerService.findAll(); 
			
		// add theCustomers into theModel with name "customers"
		theModel.addAttribute("customers", theCustomers);
				
		return "customers/list-customers";
	}
	
	
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {
		
		Customer theCustomer = new Customer();
		
		theModel.addAttribute("customer", theCustomer);
		
		return "customers/customer-form";
	}
	
	
	@PostMapping("/save")
	public String saveCustomer(@ModelAttribute("customer") Customer theCustomer) {
		
		customerService.save(theCustomer);
		
		// to prevent duplicate submission we use redirect
		return "redirect:/customers/list";
	}
	
	
	/*
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("customerId") int theId, Model theModel) {
		
		Customer theCustomer = customerService.findById(theId);
		
		theModel.addAttribute("customer", theCustomer);
		
		return "/customers/customer-form";
	}
	*/
	
	@GetMapping("/delete")
	public String deleteCustomer(@RequestParam("customerId") int theId) {
		
		// Delete customer's health detail if any 
		Optional<CustomerHealthDetail> customerHealthDetail = customerHealthDetailService.findByCustomerId(theId);
		if (customerHealthDetail.isPresent()) {
			int customerHealthDetailId = customerHealthDetail.get().getId();
			customerHealthDetailService.deleteById(customerHealthDetailId);	
		}
				
				
		// Delete customer's insurance quote(s) if any 
		Optional<List<InsuranceQuote>> insuranceQuotes = insuranceQuoteService.findByCustomerId(theId);
		if (insuranceQuotes.isPresent()) {
			List<InsuranceQuote> list = insuranceQuotes.get();
			for (InsuranceQuote quote : list){
				insuranceQuoteService.deleteById(quote.getId());
			}
		}
						
		
		
		Customer tempCustomer = customerService.findById(theId);
				
		if (tempCustomer == null) {
			throw new RuntimeException("There is no customer with id " + theId);
		}
				
		customerService.deleteById(theId);
					
		return "redirect:/customers/list";	
	}
	
	
}
