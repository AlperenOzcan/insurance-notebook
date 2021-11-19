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
@Table(name="automobile_detail")
public class AutomobileDetail {

	// Attributes
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="age")
	private int age;
	
	@Column(name="driver_experience")
	private int experience;
	
	@Column(name="car_used_kilometer")
	private int kilometer;
	
	@Column(name="health_detail_score")
	private int health_score;
	
	@OneToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name="customer_id")
	private Customer customerId;

	
	// Constructors
	public AutomobileDetail() {
		
	}

	public AutomobileDetail(int age, int experience, int kilometer, int health_score, Customer customerId) {
		this.age = age;
		this.experience = experience;
		this.kilometer = kilometer;
		this.health_score = health_score;
		this.customerId = customerId;
	}

	// Getters&Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	public int getKilometer() {
		return kilometer;
	}

	public void setKilometer(int kilometer) {
		this.kilometer = kilometer;
	}

	public int getHealth_score() {
		return health_score;
	}

	public void setHealth_score(int health_score) {
		this.health_score = health_score;
	}

	public Customer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Customer customerId) {
		this.customerId = customerId;
	}

	// toString
	@Override
	public String toString() {
		return "AutomobileDetail [id=" + id + ", age=" + age + ", experience=" + experience + ", kilometer=" + kilometer
				+ ", health_score=" + health_score + ", customerId=" + customerId + "]";
	}
		
}
