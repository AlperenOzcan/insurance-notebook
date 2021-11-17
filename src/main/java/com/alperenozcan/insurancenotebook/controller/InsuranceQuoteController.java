package com.alperenozcan.insurancenotebook.controller;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alperenozcan.insurancenotebook.entity.CustomerHealthDetail;
import com.alperenozcan.insurancenotebook.entity.InsuranceQuote;
import com.alperenozcan.insurancenotebook.service.CustomerHealthDetailService;
import com.alperenozcan.insurancenotebook.service.CustomerService;
import com.alperenozcan.insurancenotebook.service.InsuranceQuoteService;

@Controller
@RequestMapping("/insurance-quotes")
public class InsuranceQuoteController {
	
	private InsuranceQuoteService insuranceQuoteService;
	private CustomerService customerService;
	private CustomerHealthDetailService customerHealthDetailService;
	private CustomerHealthDetailController customerHealthDetailController;
	

	public InsuranceQuoteController (InsuranceQuoteService insuranceQuoteService, CustomerService customerService,
			CustomerHealthDetailService customerHealthDetailService, CustomerHealthDetailController customerHealthDetailController) {
		this.insuranceQuoteService = insuranceQuoteService;
		this.customerService = customerService;
		this.customerHealthDetailService = customerHealthDetailService;
		this.customerHealthDetailController = customerHealthDetailController;
	}

	
	@GetMapping("/list")
	public String listInsuranceQuotes(Model theModel) {
	
		List<InsuranceQuote> theInsuranceQuotes = insuranceQuoteService.findAll();
	
		theModel.addAttribute("insuranceQuotes", theInsuranceQuotes);
		
		return "insurance-quotes/list-insuranceQuotes";
	}
	
	@GetMapping("/list-customer-quotes")
	public String listCustomersInsuranceQuotes(@RequestParam("customerId") int theCustomerId, Model theModel) {
		
		Optional<List<InsuranceQuote>> quoteList = insuranceQuoteService.findByCustomerId(theCustomerId);
		
		List<InsuranceQuote> resultQuoteList = quoteList.get();
		
		theModel.addAttribute("insuranceQuotes", resultQuoteList);
		theModel.addAttribute("theCustomerId", theCustomerId);
		
		return "insurance-quotes/list-theCustomer-insuranceQuotes";
	}
	
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(@RequestParam("customerId") int theId, Model theModel) {
		
		// check given customer has health detail or not
		Optional<CustomerHealthDetail> customerHealtDetail = customerHealthDetailService.findByCustomerId(theId);
		
		if (customerHealtDetail.isEmpty()) {
			
			return customerHealthDetailController.showFormForUpdate(theId, theModel);
		}
		
		InsuranceQuote theQuote = new InsuranceQuote();
		
		theQuote.setCustomerId(customerService.findById(theId));
		
		theModel.addAttribute("quote", theQuote);
		
		return "insurance-quotes/insurance-quote-form";
	}
	
	@PostMapping("/save")
	public String saveQuote(@ModelAttribute("quote") InsuranceQuote theQuote) {
		
		theQuote.setDate(new Date(System.currentTimeMillis()));		
		
		insuranceQuoteService.save(theQuote);
		
		// to prevent duplicate submission we use redirect
		return "redirect:/insurance-quotes/list-customer-quotes?customerId=" + theQuote.getCustomerId().getId();
	}
	
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("quoteId") int theId, Model theModel) {
		
		InsuranceQuote insuranceQuote = insuranceQuoteService.findById(theId);
	
		theModel.addAttribute("quote", insuranceQuote);
	
		return "insurance-quotes/insurance-quote-form-update";
	}
	
	@GetMapping("/delete")
	public String deleteInsuranceQuote(@RequestParam("insuranceQuoteId") int theQuoteId) {
		
		int customerId = insuranceQuoteService.findById(theQuoteId).getCustomerId().getId();
		
		insuranceQuoteService.deleteById(theQuoteId);
		
		return "redirect:/insurance-quotes/list-customer-quotes?customerId=" + customerId;
	}
}
