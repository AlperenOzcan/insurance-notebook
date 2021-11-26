package com.alperenozcan.insurancenotebook.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alperenozcan.insurancenotebook.entity.AutomobileDetail;
import com.alperenozcan.insurancenotebook.entity.Customer;
import com.alperenozcan.insurancenotebook.entity.CustomerHealthDetail;
import com.alperenozcan.insurancenotebook.entity.InsuranceQuote;
import com.alperenozcan.insurancenotebook.service.AutomobileDetailService;
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
	private AutomobileDetailService automobileDetailService;
	

	public InsuranceQuoteController (InsuranceQuoteService insuranceQuoteService, CustomerService customerService,
			CustomerHealthDetailService customerHealthDetailService, CustomerHealthDetailController customerHealthDetailController,
			AutomobileDetailService automobileDetailService) {
		this.insuranceQuoteService = insuranceQuoteService;
		this.customerService = customerService;
		this.customerHealthDetailService = customerHealthDetailService;
		this.customerHealthDetailController = customerHealthDetailController;
		this.automobileDetailService = automobileDetailService;
	}

	
	@GetMapping("/list")
	public String listInsuranceQuotes(Model theModel) {
	
		List<InsuranceQuote> theInsuranceQuotes = insuranceQuoteService.findAll();
	
		theModel.addAttribute("insuranceQuotes", theInsuranceQuotes);
		
		return "insurance-quotes/list-insuranceQuotes";
	}
	
	@GetMapping("/list-customer-quotes")
	public String listCustomersInsuranceQuotes(@RequestParam("customerId") int theCustomerId, Model theModel) {
		
		// get health insurances
		List<InsuranceQuote> healthQuoteList = null;
		Optional<CustomerHealthDetail> healthDetail = customerHealthDetailService.findByCustomerId(theCustomerId);
		if(healthDetail.isPresent()) {
			healthQuoteList = insuranceQuoteService.findByDetailIdAndInsuranceType(healthDetail.get().getId(), "Health");
			
		}
		
		// get automobile insurances
		Optional<List<AutomobileDetail>> automobileDetails = automobileDetailService.findByCustomerId(theCustomerId);
		List<InsuranceQuote> automobileQuoteList = null; 
		if (automobileDetails.isPresent()) {
			for(AutomobileDetail automobileDetail: automobileDetails.get()) {
				automobileQuoteList = insuranceQuoteService.findByDetailIdAndInsuranceType(automobileDetail.getId(), "Automobile");
			}
		}
		
		List<InsuranceQuote> resultQuoteList = new ArrayList<>();
		if(healthQuoteList != null && !healthQuoteList.isEmpty()) {
			resultQuoteList.addAll(healthQuoteList);
		}
		if(automobileQuoteList != null && !automobileQuoteList.isEmpty()) {
			resultQuoteList.addAll(automobileQuoteList);			
		}
		
		theModel.addAttribute("insuranceQuotes", resultQuoteList);
		
		Customer theCustomer = customerService.findById(theCustomerId);
		
		theModel.addAttribute("customer", theCustomer);
		
		return "insurance-quotes/list-theCustomer-insuranceQuotes";
	}
	
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(@RequestParam("customerId") int theId, Model theModel) {
		
		List<String> availableInsuranceTypes = new ArrayList<String>();
		availableInsuranceTypes.add("Earthquake");
		
		List<AutomobileDetail> customersAutomobiles = new ArrayList<AutomobileDetail>();
		
		// boolean warning = false;
		
		// check given customer has health detail or not
		Optional<CustomerHealthDetail> customerHealtDetail = customerHealthDetailService.findByCustomerId(theId);
		
		if (customerHealtDetail.isPresent()) {
			availableInsuranceTypes.add("Health");
			
			Optional<List<AutomobileDetail>> automobiles = automobileDetailService.findByCustomerId(theId);
			if (automobiles.isPresent()) {
				availableInsuranceTypes.add("Automobile");
				customersAutomobiles.addAll(automobiles.get());
			}
		}
		/*else {
			warning = true;
			// return customerHealthDetailController.showFormForUpdate(theId, theModel);
		}*/
		theModel.addAttribute("availableInsuranceTypes", availableInsuranceTypes);
		//theModel.addAttribute("warning", warning);
		theModel.addAttribute("customersAutomobiles", customersAutomobiles);
		
		InsuranceQuote theQuote = new InsuranceQuote();
		//theQuote.setCustomerId(customerService.findById(theId));
		
		theModel.addAttribute("quote", theQuote);
		
		return "insurance-quotes/insurance-quote-form";
	}
	
	
	
	/*
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
		Customer customer = customerService.findById(insuranceQuote.getCustomerId().getId());
	
		theModel.addAttribute("quote", insuranceQuote);
		theModel.addAttribute("theCustomer", customer);
	
		return "insurance-quotes/insurance-quote-form-update";
	}
	
	
	@GetMapping("/delete")
	public String deleteInsuranceQuote(@RequestParam("insuranceQuoteId") int theQuoteId) {
		
		int customerId = insuranceQuoteService.findById(theQuoteId).getCustomerId().getId();
		
		insuranceQuoteService.deleteById(theQuoteId);
		
		return "redirect:/insurance-quotes/list-customer-quotes?customerId=" + customerId;
	}
	*/
	
	@GetMapping("/delete")
	public String deleteInsuranceQuote(@RequestParam("detailId") int detailId, @RequestParam("insuranceType") String insuranceType) {
		
		insuranceQuoteService.deleteByDetailIdAndInsuranceType(detailId, insuranceType);
		
		int customerId;
		if(insuranceType.equals("Health")) {
			customerId = customerHealthDetailService.findById(detailId).getCustomer().getId();
		}
		else if(insuranceType.equals("Automobile")){
			customerId = automobileDetailService.findById(detailId).getCustomerId().getId();
		}
		else {
			throw new RuntimeException("No such insurance quote type");
		}
		
		return "redirect:/insurance-quotes/list-customer-quotes?customerId=" + customerId;
	}
}
