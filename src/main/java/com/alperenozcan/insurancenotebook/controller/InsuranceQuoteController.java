package com.alperenozcan.insurancenotebook.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alperenozcan.insurancenotebook.entity.AutomobileDetail;
import com.alperenozcan.insurancenotebook.entity.Customer;
import com.alperenozcan.insurancenotebook.entity.CustomerHealthDetail;
import com.alperenozcan.insurancenotebook.entity.House;
import com.alperenozcan.insurancenotebook.entity.InsuranceQuote;
import com.alperenozcan.insurancenotebook.service.AutomobileDetailService;
import com.alperenozcan.insurancenotebook.service.CustomerHealthDetailService;
import com.alperenozcan.insurancenotebook.service.CustomerService;
import com.alperenozcan.insurancenotebook.service.HouseService;
import com.alperenozcan.insurancenotebook.service.InsuranceQuoteService;

@Controller
@RequestMapping("/insurance-quotes")
public class InsuranceQuoteController {
	
	private InsuranceQuoteService insuranceQuoteService;
	private CustomerService customerService;
	private CustomerHealthDetailService customerHealthDetailService;
	private AutomobileDetailService automobileDetailService;
	private HouseService houseService;	
	private InsuranceQuote insuranceQuote;
	
	public InsuranceQuoteController (InsuranceQuoteService insuranceQuoteService, CustomerService customerService,
			CustomerHealthDetailService customerHealthDetailService, AutomobileDetailService automobileDetailService,
			HouseService houseService) {
		this.insuranceQuoteService = insuranceQuoteService;
		this.customerService = customerService;
		this.customerHealthDetailService = customerHealthDetailService;
		this.automobileDetailService = automobileDetailService;
		this.houseService = houseService;
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
		List<InsuranceQuote> automobileQuoteList = new ArrayList<InsuranceQuote>(); 
		if (automobileDetails.isPresent()) {
			for(AutomobileDetail automobileDetail: automobileDetails.get()) {
				List<InsuranceQuote> insuranceQuotes = insuranceQuoteService.findByDetailIdAndInsuranceType(automobileDetail.getId(), "Automobile");
				automobileQuoteList.addAll(insuranceQuotes);
			}
		}
		
		// get earthquake insurances
		Optional<List<House>> houses = houseService.findByCustomerId(theCustomerId);
		List<InsuranceQuote> earthquakeQuoteList = new ArrayList<InsuranceQuote>(); 
		if(houses.isPresent()) {
			for(House h: houses.get()) {
				List<InsuranceQuote> insuranceQuotes = insuranceQuoteService.findByDetailIdAndInsuranceType(h.getId(), "Earthquake");
				earthquakeQuoteList.addAll(insuranceQuotes);
			}
		}		
		
		List<InsuranceQuote> resultQuoteList = new ArrayList<>();
		if(healthQuoteList != null && !healthQuoteList.isEmpty()) {
			resultQuoteList.addAll(healthQuoteList);
		}
		if(!automobileQuoteList.equals(new ArrayList<InsuranceQuote>()) && !automobileQuoteList.isEmpty()) {
			resultQuoteList.addAll(automobileQuoteList);			
		}
		if(!earthquakeQuoteList.equals(new ArrayList<InsuranceQuote>()) && !earthquakeQuoteList.isEmpty()) {
			resultQuoteList.addAll(earthquakeQuoteList);
		}
		
		theModel.addAttribute("insuranceQuotes", resultQuoteList);
		
		Customer theCustomer = customerService.findById(theCustomerId);
		
		theModel.addAttribute("customer", theCustomer);
		
		return "insurance-quotes/list-theCustomer-insuranceQuotes";
	}
	
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(@RequestParam("customerId") int theId, Model theModel) {

		boolean healthDetail_warning = true;
		boolean automobileDetail_warning = true;
		boolean earthquake_warning = true;
		
		List<String> availableInsuranceTypes = new ArrayList<String>();
		
		// check given customer has any house or not
		Optional<List<House>> houses = houseService.findByCustomerId(theId);
		if (houses.isPresent() && houses.get().size() != 0) {
			availableInsuranceTypes.add("Earthquake");
			earthquake_warning = false;
		}
						
		// check given customer has health detail or not
		Optional<CustomerHealthDetail> customerHealtDetail = customerHealthDetailService.findByCustomerId(theId);
		if (customerHealtDetail.isPresent()) {
			availableInsuranceTypes.add("Health");
			healthDetail_warning = false;
			
			Optional<List<AutomobileDetail>> automobiles = automobileDetailService.findByCustomerId(theId);
			if (automobiles.isPresent() && automobiles.get().size() != 0) {
				availableInsuranceTypes.add("Automobile");
				automobileDetail_warning = false;
			}
		}
		
		theModel.addAttribute("availableInsuranceTypes", availableInsuranceTypes);
		theModel.addAttribute("healthDetail_warning", healthDetail_warning);
		theModel.addAttribute("automobileDetail_warning", automobileDetail_warning);
		theModel.addAttribute("earthquake_warning", earthquake_warning);
		
		InsuranceQuote theQuote = new InsuranceQuote();
		
		theModel.addAttribute("quote", theQuote);
		
		theModel.addAttribute("customerId", theId);
		
		return "insurance-quotes/insurance-quote-form";
	}
	
	
	@PostMapping("/pre-save")
	public String saveQuoteShell(@ModelAttribute("quote") InsuranceQuote theQuote, @RequestParam("customerId") int customerId, Model theModel) {
		
		if (theQuote.getInsuranceType().equals("Automobile")) {
			
			Optional<List<AutomobileDetail>> automobileList = automobileDetailService.findByCustomerId(customerId);
			List<AutomobileDetail> resultAutomobileList = automobileList.get();
			
			theModel.addAttribute("automobileDetails", resultAutomobileList);
			
			Customer theCustomer = customerService.findById(customerId);
			
			theModel.addAttribute("customer", theCustomer);
			
			this.insuranceQuote = theQuote;
			
			return "insurance-quotes/select-customers-automobile";
		}
		
		else if (theQuote.getInsuranceType().equals("Earthquake")) {
			List<House> houseList = houseService.findByCustomerId(customerId).get();
			theModel.addAttribute("houses", houseList);
			
			Customer theCustomer = customerService.findById(customerId);
			theModel.addAttribute("customer", theCustomer);
			
			this.insuranceQuote = theQuote;
			
			return "insurance-quotes/select-customers-house";
		}
		
		else if (theQuote.getInsuranceType().equals("Health")) {
			int detailId = customerHealthDetailService.findByCustomerId(customerId).get().getId();
			theQuote.setDetailId(detailId);
			saveQuoteCore(theQuote);
			return "redirect:/insurance-quotes/list-customer-quotes?customerId="
									+ customerHealthDetailService.findById(detailId).getCustomer().getId();
		}
		
		else {
			throw new RuntimeException("No such insurance quote type");
		}
	}
	
	@GetMapping("/save-automobile-quote")
	public String saveAutomobileQuote(@RequestParam("automobileId") int theId) {
		InsuranceQuote quote = this.insuranceQuote;
		quote.setDetailId(theId);
		
		quote.setDate(new Date(System.currentTimeMillis()));		
		
		insuranceQuoteService.save(quote);
		
		return "redirect:/insurance-quotes/list-customer-quotes?customerId=" 
									+ automobileDetailService.findById(theId).getCustomer().getId();
	}
	
	@GetMapping("/save-earthquake-quote")
	public String saveEarthquakeQuote(@RequestParam("houseId") int theId) {
		InsuranceQuote quote = this.insuranceQuote;
		quote.setDetailId(theId);
		
		quote.setDate(new Date(System.currentTimeMillis()));		
		
		insuranceQuoteService.save(quote);
		
		return "redirect:/insurance-quotes/list-customer-quotes?customerId=" 
									+ houseService.findById(theId).getCustomer().getId();
	}

	private void saveQuoteCore(InsuranceQuote theQuote) {
		
		theQuote.setDate(new Date(System.currentTimeMillis()));		
		
		insuranceQuoteService.save(theQuote);
	}
	

	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("quoteId") int theId, Model theModel) {
		
		InsuranceQuote insuranceQuote = insuranceQuoteService.findById(theId);
		
		Customer customer = null;
		if (insuranceQuote.getInsuranceType().equals("Health")) {
			customer = customerHealthDetailService.findById(insuranceQuote.getDetailId()).getCustomer();
		}
		else if (insuranceQuote.getInsuranceType().equals("Automobile")) {
			customer = automobileDetailService.findById(insuranceQuote.getDetailId()).getCustomer();
		}
		else if (insuranceQuote.getInsuranceType().equals("Earthquake")) {
			customer = houseService.findById(insuranceQuote.getDetailId()).getCustomer();
		}
		else {
			throw new RuntimeException("There is no such insurance type");
		}
	
		theModel.addAttribute("quote", insuranceQuote);
		theModel.addAttribute("theCustomer", customer);
	
		return "insurance-quotes/insurance-quote-form-update";
	}
	
	@PostMapping("/update")
	public String updateQuote(@ModelAttribute("quote") InsuranceQuote theInsuranceQuote) {
		
		theInsuranceQuote.setDate(new Date(System.currentTimeMillis()));
		insuranceQuoteService.save(theInsuranceQuote);
		
		int customerId;
		if (theInsuranceQuote.getInsuranceType().equals("Health")) {
			customerId = customerHealthDetailService.findById(theInsuranceQuote.getDetailId()).getCustomer().getId();
		}
		else if (theInsuranceQuote.getInsuranceType().equals("Automobile")) {
			customerId = automobileDetailService.findById(theInsuranceQuote.getDetailId()).getCustomer().getId();
		}
		else if(theInsuranceQuote.getInsuranceType().equals("Earthquake")) {
			customerId = houseService.findById(theInsuranceQuote.getDetailId()).getCustomer().getId();
		}
		else {
			throw new RuntimeException("There is no such insurance type");
		}
		
		return "redirect:/insurance-quotes/list-customer-quotes?customerId=" + customerId;
	}
	
	@GetMapping("/delete")
	public String deleteInsuranceQuote(@RequestParam("quoteId") int quoteId) {
		
		// insuranceQuoteService.deleteByDetailIdAndInsuranceType(detailId, insuranceType);
		int detailId = insuranceQuoteService.findById(quoteId).getDetailId();
		String insuranceType = insuranceQuoteService.findById(quoteId).getInsuranceType();
		
		insuranceQuoteService.deleteById(quoteId);
		
		int customerId;
		if(insuranceType.equals("Health")) {
			customerId = customerHealthDetailService.findById(detailId).getCustomer().getId();
		}
		else if(insuranceType.equals("Automobile")){
			customerId = automobileDetailService.findById(detailId).getCustomer().getId();
		}
		else if(insuranceType.equals("Earthquake")) {
			customerId = houseService.findById(detailId).getCustomer().getId();
		}
		else {
			throw new RuntimeException("No such insurance quote type");
		}
		
		return "redirect:/insurance-quotes/list-customer-quotes?customerId=" + customerId;
	}
}
