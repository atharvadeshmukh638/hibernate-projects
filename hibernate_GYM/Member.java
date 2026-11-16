package com.hibernate_GYM;
import javax.persistence.*;

@Entity
@Table(name = "member")
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;
	private String plan;
	private double fee;

	public Member() {
	}

	public Member(String name, String plan, double fee) {
		this.name = name;
		this.plan = plan;
		this.fee = fee;
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

	public String getPlan() {
		return plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}

	public double getFee() {
		return fee;
	}

	public void setFee(double fee) {
		this.fee = fee;
	}

	@Override
	public String toString() {
		return "Member{id=" + id + ", name='" + name + "', plan='" + plan + "', fee=" + fee + "}";
	}
}
