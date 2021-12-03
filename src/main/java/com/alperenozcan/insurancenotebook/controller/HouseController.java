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

import com.alperenozcan.insurancenotebook.entity.Customer;
import com.alperenozcan.insurancenotebook.entity.House;
import com.alperenozcan.insurancenotebook.service.CustomerService;
import com.alperenozcan.insurancenotebook.service.HouseService;

@Controller
@RequestMapping("/houses")
public class HouseController {

	private HouseService houseService;
	private CustomerService customerService;
	
	public HouseController(HouseService houseService, CustomerService customerService) {
		this.houseService = houseService;
		this.customerService = customerService;
	}
	
	@GetMapping("/list-customer-houses")
	public String listCustomerHouses(@RequestParam("customerId") int theCustomerId, Model theModel) {
		Optional<List<House>> houseList = houseService.findByCustomerId(theCustomerId);
		List<House> resultHouseList = houseList.get();
		theModel.addAttribute("houses", resultHouseList);
		Customer theCustomer = customerService.findById(theCustomerId);
		theModel.addAttribute("customer", theCustomer);
		
		return "houses/list-customer-houses";
	}
	
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(@RequestParam("customerId") int theId, Model theModel) {
		Customer customer = customerService.findById(theId);
		theModel.addAttribute("customer", customer);
		
		House house = new House();
		house.setCustomer(customer);
		theModel.addAttribute("house", house);
		
		return "houses/house-form";
	}
	
	@PostMapping("/save")
	public String saveHouse(@ModelAttribute("house") House theHouse) {
		houseService.save(theHouse);
		return "redirect:/houses/list-customer-houses?customerId=" + theHouse.getCustomer().getId();
	}
	
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("houseId") int theId, Model theModel) {
		House house = houseService.findById(theId);
		Customer customer = customerService.findById(house.getCustomer().getId());
		
		theModel.addAttribute("customer",customer);
		theModel.addAttribute("house",house);
		
		return "houses/house-form";
	}
	
	@GetMapping("/delete")
	public String deleteHouse(@RequestParam("houseId") int theHouseId) {
		int customerId = houseService.findById(theHouseId).getCustomer().getId();
		houseService.deleteById(theHouseId);
		return "redirect:/houses/list-customer-houses?customerId=" + customerId;
	}
	
}
