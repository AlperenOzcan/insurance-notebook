package com.alperenozcan.insurancenotebook.entity;

import java.sql.Date;

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
@Table(name="insurance_quote")
public class InsuranceQuote {

	// Attributes
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;	
	
	@Column(name="insurance_type")
	private String insuranceType;
	
	@Column(name="acceptance")
	private boolean acceptance;
	
	@Column(name="premium")
	private double premium;
	
	@Column(name="date")
	private Date date;
	
	@OneToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name="customer_id")
	private Customer customerId;
	
	
	// Constructors
	// No-argument constructor needed for Hibernate
	public InsuranceQuote() {
		
	}

	public InsuranceQuote(String insuranceType, boolean acceptance, double premium, Customer customerId, Date date) {
		this.insuranceType = insuranceType;
		this.acceptance = acceptance;
		this.premium = premium;
		this.customerId = customerId;
		this.date = date;
	}

	
	// Getter&Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getInsuranceType() {
		return insuranceType;
	}

	public void setInsuranceType(String insuranceType) {
		this.insuranceType = insuranceType;
	}

	public boolean isAcceptance() {
		return acceptance;
	}

	public void setAcceptance(boolean acceptance) {
		this.acceptance = acceptance;
	}

	public double getPremium() {
		return premium;
	}

	public void setPremium(double premium) {
		this.premium = premium;
	}

	public Customer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Customer customerId) {
		this.customerId = customerId;
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	// toString
	@Override
	public String toString() {
		return "InsuranceQuote [id=" + id + ", insuranceType=" + insuranceType + ", acceptance=" + acceptance
				+ ", premium=" + premium + ", date=" + date + ", customerId=" + customerId + "]";
	}
	
}
