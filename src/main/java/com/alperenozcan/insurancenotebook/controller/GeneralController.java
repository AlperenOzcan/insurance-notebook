package com.alperenozcan.insurancenotebook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GeneralController {

	@GetMapping("/informations")
	public String informations(@RequestParam("customerId") int customerId, Model theModel) {
		theModel.addAttribute("customerId", customerId);
		return "general/information-types";
	}
}
