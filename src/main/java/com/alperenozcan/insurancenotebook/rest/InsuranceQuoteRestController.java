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

import com.alperenozcan.insurancenotebook.entity.InsuranceQuote;
import com.alperenozcan.insurancenotebook.service.InsuranceQuoteService;

@RestController
@RequestMapping("/api")
public class InsuranceQuoteRestController {

	private InsuranceQuoteService insuranceQuoteService;

	@Autowired
	public InsuranceQuoteRestController(InsuranceQuoteService insuranceQuoteService) {
		this.insuranceQuoteService = insuranceQuoteService;
	}
	
	@GetMapping("/insurance-quotes")
	public List<InsuranceQuote> findAll() {
		return insuranceQuoteService.findAll();
	}

	@GetMapping("/insurance-quotes/{insuranceQuoteId}")
	public InsuranceQuote getInsuranceQuote(@PathVariable int insuranceQuoteId) {

		InsuranceQuote theInsuranceQuote = insuranceQuoteService.findById(insuranceQuoteId);
		
		if (theInsuranceQuote == null) {
			throw new RuntimeException("There is no Insurance Quote information with id " + insuranceQuoteId);
		}
		
		return theInsuranceQuote;
	}
	
	@PostMapping("/insurance-quotes")
	public InsuranceQuote addInsuranceQuote(@RequestBody InsuranceQuote theInsuranceQuote) {
		
		theInsuranceQuote.setId(0);
		insuranceQuoteService.save(theInsuranceQuote);
		
		return theInsuranceQuote;
	}
	
	@PutMapping("/insurance-quotes")
	public InsuranceQuote updateInsuranceQuote(@RequestBody InsuranceQuote theInsuranceQuote) {
		insuranceQuoteService.save(theInsuranceQuote);
		return theInsuranceQuote;
	}
	
	@DeleteMapping("/insurance-quotes/{insuranceQuoteId}")
	public String deleteInsuranceQuote(@PathVariable int insuranceQuoteId) {
		
		InsuranceQuote tempInsuranceQuote = insuranceQuoteService.findById(insuranceQuoteId);
		
		if (tempInsuranceQuote == null) {
			throw new RuntimeException("There is no Insurance Quote with id " + insuranceQuoteId);
		}
		
		insuranceQuoteService.deleteById(insuranceQuoteId);
		
		return "Insurance Quote with id " + insuranceQuoteId + " is deleted";
	}
}
