package com.hibernate_Hospital;

import javax.persistence.*;

@Entity
@Table(name = "patient")
public class Patient {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;
	private String ward;
	private double billAmount;

	public Patient() {
	}

	public Patient(String name, String ward, double billAmount) {
		this.name = name;
		this.ward = ward;
		this.billAmount = billAmount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWard() {
		return ward;
	}

	public void setWard(String ward) {
		this.ward = ward;
	}

	public double getBillamount() {
		return billAmount;
	}

	public void setBillamount(double billAmount) {
		this.billAmount = billAmount;
	}

	@Override
	public String toString() {
		return "Patient{id=" + id + ", name='" + name + "', ward='" + ward + "', billAmount=" + billAmount + "}";
	}
}
