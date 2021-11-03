package com.alperenozcan.insurancenotebook.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alperenozcan.insurancenotebook.entity.InsuranceQuote;
import com.alperenozcan.insurancenotebook.service.InsuranceQuoteService;

@Controller
@RequestMapping("/insurance-quotes")
public class InsuranceQuoteController {
	
	private InsuranceQuoteService insuranceQuoteService;

	public InsuranceQuoteController (InsuranceQuoteService insuranceQuoteService) {
		this.insuranceQuoteService = insuranceQuoteService;
	}

	
	@GetMapping("/list")
	public String listInsuranceQuotes(Model theModel) {
	
		List<InsuranceQuote> theInsuranceQuotes = insuranceQuoteService.findAll();
	
		theModel.addAttribute("insuranceQuotes", theInsuranceQuotes);
		
		return "insurance-quotes/list-insuranceQuotes";
	}
	
	@GetMapping("/delete")
	public String deleteInsuranceQuote(@RequestParam("insuranceQuoteId") int theId) {
		
		insuranceQuoteService.deleteById(theId);
		
		return "redirect:/insurance-quotes/list";
	}
}
