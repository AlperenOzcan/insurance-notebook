package com.alperenozcan.insurancenotebook.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="customer")
public class Customer {
/*
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="customer_health_detail_id")
	private CustomerHealthDetail customerHealthDetail;
	*/
	// Attributes
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="personal_identification_number")
	private String personalIdentificationNumber;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	@Column(name="gender")
	private boolean gender;
	
	@Column(name="cost")
	private double cost;
	
	
	// Constructors
	// No-argument constructor needed for Hibernate
	public Customer() {
		
	}
	
	public Customer(CustomerHealthDetail customerHealthDetail, String personalIdentificationNumber, String firstName,
			String lastName, boolean gender, double cost) {
		super();
		//this.customerHealthDetail = customerHealthDetail;
		this.personalIdentificationNumber = personalIdentificationNumber;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.cost = cost;
	}




	// Getters&Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPersonalIdentificationNumber() {
		return personalIdentificationNumber;
	}

	public void setPersonalIdentificationNumber(String personalIdentificationNumber) {
		this.personalIdentificationNumber = personalIdentificationNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public boolean isGender() {
		return gender;
	}

	public void setGender(boolean gender) {
		this.gender = gender;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}
	/*
	public CustomerHealthDetail getCustomerHealthDetail() {
		return customerHealthDetail;
	}

	public void setCustomerHealthDetail(CustomerHealthDetail customerHealthDetail) {
		this.customerHealthDetail = customerHealthDetail;
	}
	
	// toString
	@Override
	public String toString() {
		return "Customer [customerHealthDetail=" + customerHealthDetail + ", id=" + id
				+ ", personalIdentificationNumber=" + personalIdentificationNumber + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", gender=" + gender + ", cost=" + cost + "]";
	}
*/

}