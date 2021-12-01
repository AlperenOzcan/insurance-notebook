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
@Table(name="house_detail")
public class House {

	// Attributes
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="built_date")
	private Date builtDate;

	@Column(name="square_meters")
	private int squareMeters;

	@Column(name="structure_type")
	private String structureType;
	
	@OneToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name="customer_id")
	private Customer customer;

	
	// Constructors
	public House() {
		
	}

	public House(Date builtDate, int squareMeters, String structureType, Customer customer) {
		this.builtDate = builtDate;
		this.squareMeters = squareMeters;
		this.structureType = structureType;
		this.customer = customer;
	}

	// Getters&Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getBuiltDate() {
		return builtDate;
	}

	public void setBuiltDate(Date builtDate) {
		this.builtDate = builtDate;
	}

	public int getSquareMeters() {
		return squareMeters;
	}

	public void setSquareMeters(int squareMeters) {
		this.squareMeters = squareMeters;
	}

	public String getStructureType() {
		return structureType;
	}

	public void setStructureType(String structureType) {
		this.structureType = structureType;
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
		return "House [id=" + id + ", builtDate=" + builtDate + ", squareMeters=" + squareMeters + ", structureType="
				+ structureType + ", customer=" + customer + "]";
	}	
}
