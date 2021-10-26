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
@Table(name="customer_health_detail")
public class CustomerHealthDetail {

	// Attributes
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;	
	
	@Column(name="height")
	private int height;
	
	@Column(name="weight")
	private int weight;
	
	@Column(name="had_cancer")
	private boolean hadCancer;
	
	@Column(name="had_heart_attack")
	private boolean hadHeartAttack;
	
	@Column(name="has_diabetes")
	private boolean hasDiabetes;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="customer_id")
	private Customer customer;
	
	
	// Constructors
	// No-argument constructor needed for Hibernate
	public CustomerHealthDetail() {
		
	}

	public CustomerHealthDetail(int height, int weight, boolean hadCancer, boolean hadHeartAttack, boolean hasDiabetes,
			Customer customer) {
		this.height = height;
		this.weight = weight;
		this.hadCancer = hadCancer;
		this.hadHeartAttack = hadHeartAttack;
		this.hasDiabetes = hasDiabetes;
		this.customer = customer;
	}


	// Getters&Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public boolean isHasDiabetes() {
		return hasDiabetes;
	}

	public void setHasDiabetes(boolean hasDiabetes) {
		this.hasDiabetes = hasDiabetes;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	// toString
	@Override
	public String toString() {
		return "CustomerHealthDetail [id=" + id + ", height=" + height + ", weight=" + weight + ", hadCancer="
				+ hadCancer + ", hadHeartAttack=" + hadHeartAttack + ", hasDiabetes=" + hasDiabetes + ", customer="
				+ customer + "]";
	}

}