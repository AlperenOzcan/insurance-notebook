package com.alperenozcan.insurancenotebook.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="customer")
public class Customer {

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
	
	
	// Constructors
	// No-argument constructor needed for Hibernate
	public Customer() {
		
	}
	
	public Customer(String personalIdentificationNumber, String firstName, String lastName, boolean gender) {
		this.personalIdentificationNumber = personalIdentificationNumber;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
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


	// toString
	@Override
	public String toString() {
		return "id=" + id + ", personalIdentificationNumber=" + personalIdentificationNumber + 
				", firstName=" + firstName + ", lastName=" + lastName + 
				", gender=" + gender + "]";
	}


}