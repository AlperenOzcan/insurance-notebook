package com.alperenozcan.insurancenotebook.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alperenozcan.insurancenotebook.entity.Customer;
import com.alperenozcan.insurancenotebook.entity.CustomerHealthDetail;
import com.alperenozcan.insurancenotebook.entity.InsuranceQuote;
import com.alperenozcan.insurancenotebook.service.CustomerHealthDetailService;
import com.alperenozcan.insurancenotebook.service.CustomerService;
import com.alperenozcan.insurancenotebook.service.InsuranceQuoteService;

@RestController
@RequestMapping("/api")
public class CustomerRestController {

	private CustomerService customerService;
	private CustomerHealthDetailService customerHealthDetailService;
	private InsuranceQuoteService insuranceQuoteService;
	
	@Autowired
	public CustomerRestController (CustomerService theCustomerService, CustomerHealthDetailService theCustomerHealthDetailService,
			InsuranceQuoteService theInsuranceQuoteService) {
		customerService = theCustomerService;
		customerHealthDetailService = theCustomerHealthDetailService;
		insuranceQuoteService = theInsuranceQuoteService;
	}
	
	@GetMapping("/customers")
	public List<Customer> findAll() {
		return customerService.findAll();
	}
	
	@GetMapping("/customers/{customerId}")
	public Customer getCustomer(@PathVariable int customerId) {
		
		Customer theCustomer = customerService.findById(customerId);
		
		if (theCustomer == null) {
			throw new RuntimeException("There is no customer with id " + customerId);
		}
		
		return theCustomer;
	}
	
	@PostMapping("/customers")
	public Customer addCustomer(@RequestBody Customer theCustomer) {
		
		theCustomer.setId(0);
		customerService.save(theCustomer);
		
		return theCustomer;
	}
	
	@PutMapping("/customers")
	public Customer updateCustomer(@RequestBody Customer theCustomer) {
		customerService.save(theCustomer);
		return theCustomer;
	}
	
	@DeleteMapping("/customers/{customerId}")
	public String deleteCustomer(@PathVariable int customerId) {
		
		// delete customer health details of the customer
		Optional<CustomerHealthDetail> customerHealthDetail = customerHealthDetailService.findByCustomerId(customerId);
		// do we have customer health detail with given customerID. If do not ...
		if (customerHealthDetail.get() != null) {
			int customerHealthDetailId = customerHealthDetail.get().getId();
			customerHealthDetailService.deleteById(customerHealthDetailId);	
		}
		
		// Delete customer's insurance quote(s) if any 
		Optional<List<InsuranceQuote>> insuranceQuotes = insuranceQuoteService.findByCustomerId(customerId);
		if (insuranceQuotes.isPresent()) {
			List<InsuranceQuote> list = insuranceQuotes.get();
			for (InsuranceQuote quote : list){
				insuranceQuoteService.deleteById(quote.getId());
			}
		}
				
		Customer tempCustomer = customerService.findById(customerId);
		
		if (tempCustomer == null) {
			throw new RuntimeException("There is no customer with id " + customerId);
		}
		
		customerService.deleteById(customerId);
		
		return "Customer with id " + customerId + " is deleted";
	}
}
