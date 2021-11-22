package com.alperenozcan.insurancenotebook.controller;

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
import com.alperenozcan.insurancenotebook.service.AutomobileDetailService;
import com.alperenozcan.insurancenotebook.service.CustomerHealthDetailService;
import com.alperenozcan.insurancenotebook.service.CustomerService;

@Controller
@RequestMapping("/automobile-details")
public class AutomobileDetailController {

	private AutomobileDetailService automobileDetailService;
	private CustomerService customerService;
	private CustomerHealthDetailService customerHealthDetailService;
	private CustomerHealthDetailController customerHealthDetailController;
	
	public AutomobileDetailController(AutomobileDetailService automobileDetailService, CustomerService customerService,
			CustomerHealthDetailService customerHealthDetailService, CustomerHealthDetailController customerHealthDetailController) {
		this.automobileDetailService = automobileDetailService;
		this.customerService = customerService;
		this.customerHealthDetailService = customerHealthDetailService;
		this.customerHealthDetailController = customerHealthDetailController;
	}
	
	@GetMapping("/list-customer-automobile-details")
	public String listCustomerAutomobileDetails(@RequestParam("customerId") int theCustomerId, Model theModel) {
		Optional<List<AutomobileDetail>> automobileList = automobileDetailService.findByCustomerId(theCustomerId);
		List<AutomobileDetail> resultAutomobileList = automobileList.get();
		
		theModel.addAttribute("automobileDetails", resultAutomobileList);
		
		Customer theCustomer = customerService.findById(theCustomerId);
		
		theModel.addAttribute("customer", theCustomer);
		
		return "automobile-details/list-customer-automobile-details";
	}
	
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(@RequestParam("customerId") int theId, Model theModel) {
		
		// check given customer has health detail or not
		Optional<CustomerHealthDetail> customerHealtDetail = customerHealthDetailService.findByCustomerId(theId);
		
		if (customerHealtDetail.isEmpty()) {
			
			return customerHealthDetailController.showFormForUpdate(theId, theModel);
		}
		
		Customer customer = customerService.findById(theId);
		theModel.addAttribute("customer", customer);
		
		AutomobileDetail automobile = new AutomobileDetail();
		
		automobile.setCustomerId(customerService.findById(theId));
		
		theModel.addAttribute("automobile", automobile);
		
		return "automobile-details/automobile-details-form";
	}
	
	@PostMapping("/save")
	public String saveAutomobileDetail(@ModelAttribute("automobileDetail") AutomobileDetail theAutomobileDetail) {
		automobileDetailService.save(theAutomobileDetail);
		return "redirect:/customers/list";
	}
	
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("automobileDetailId") int theId, Model theModel) {
		AutomobileDetail theAutomobileDetail = automobileDetailService.findById(theId);
		Customer customer = customerService.findById(theAutomobileDetail.getCustomerId().getId());
		
		theModel.addAttribute("automobile", theAutomobileDetail);
		theModel.addAttribute("theCustomer", customer);
		
		return "automobile-details/automobile-details-form-update";
	}
	
	@GetMapping("/delete")
	public String deleteAutomobileDetail(@RequestParam("automobileDetailId") int theAutomobileId) {
		int customerId = automobileDetailService.findById(theAutomobileId).getCustomerId().getId();
		automobileDetailService.deleteById(theAutomobileId);
		
		return "redirect:/automobile-details/list-customer-automobile-details?customerId="+customerId;
	}
	
}
