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
	
	@Column(name="height")
	private int height;
	
	@Column(name="weight")
	private int weight;
	
	@Column(name="had_cancer")
	private boolean hadCancer;
	
	@Column(name="had_heart_attack")
	private boolean hadHeartAttack;
	
	@Column(name="is_diabetic")
	private boolean isDiabetic;
	
	@Column(name="cost")
	private double cost;
	
	
	// Constructors
	// No-argument constructor needed for Hibernate
	public Customer() {
		
	}

	public Customer(String personalIdentificationNumber, String firstName, String lastName, boolean gender, int height,
			int weight, boolean hadCancer, boolean hadHeartAttack, boolean isDiabetic, double cost) {
		this.personalIdentificationNumber = personalIdentificationNumber;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.height = height;
		this.weight = weight;
		this.hadCancer = hadCancer;
		this.hadHeartAttack = hadHeartAttack;
		this.isDiabetic = isDiabetic;
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

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public boolean isHadCancer() {
		return hadCancer;
	}

	public void setHadCancer(boolean hadCancer) {
		this.hadCancer = hadCancer;
	}

	public boolean isHadHeartAttack() {
		return hadHeartAttack;
	}

	public void setHadHeartAttack(boolean hadHeartAttack) {
		this.hadHeartAttack = hadHeartAttack;
	}

	public boolean isDiabetic() {
		return isDiabetic;
	}

	public void setDiabetic(boolean isDiabetic) {
		this.isDiabetic = isDiabetic;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}


	// toString
	@Override
	public String toString() {
		return "Customer [id=" + id + ", personalIdentificationNumber=" + personalIdentificationNumber + ", firstName="
				+ firstName + ", lastName=" + lastName + ", gender=" + gender + ", height=" + height + ", weight="
				+ weight + ", hadCancer=" + hadCancer + ", hadHeartAttack=" + hadHeartAttack + ", isDiabetic="
				+ isDiabetic + ", cost=" + cost + "]";
	}
}
