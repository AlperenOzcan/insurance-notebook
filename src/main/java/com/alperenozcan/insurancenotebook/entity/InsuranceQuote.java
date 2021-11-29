package com.alperenozcan.insurancenotebook.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	
	@Column(name="detail_id")
	private int detailId;
	
	// Constructors
	// No-argument constructor needed for Hibernate
	public InsuranceQuote() {
		
	}

	public InsuranceQuote(String insuranceType, boolean acceptance, double premium,int detailId, Date date) {
		this.insuranceType = insuranceType;
		this.acceptance = acceptance;
		this.premium = premium;
		this.detailId = detailId;
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public int getDetailId() {
		return detailId;
	}

	public void setDetailId(int detailId) {
		this.detailId = detailId;
	}

	// toString
	@Override
	public String toString() {
		return "InsuranceQuote [id=" + id + ", insuranceType=" + insuranceType + ", acceptance=" + acceptance
				+ ", premium=" + premium + ", date=" + date + ", detailId=" + detailId + "]";
	}
	
}
