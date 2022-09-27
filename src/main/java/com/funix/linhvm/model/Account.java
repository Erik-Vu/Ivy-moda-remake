package com.funix.linhvm.model;

import java.sql.Date;

public class Account {

	private int id;
	
	private String email;
	
	private String fullName;
	
	private int phone;
		
	private String role;
	
	private String enabled;
	
	private String state;
	
	private Date createAt;

	public Account() {
		super();
	}

	public Account(int id, String email, String fullName, int phone,  Date createAt) {
		super();
		this.id = id;
		this.email = email;
		this.fullName = fullName;
		this.phone = phone;
		this.createAt = createAt;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public int getPhone() {
		return phone;
	}

	public void setPhone(int phone) {
		this.phone = phone;
	}

	public String getRole() {
		return role;
	}

	public void setRole(Boolean role) {
		if (role) this.role = "ADMIN";
		else this.role = "USER";
	}
	
	public void setRole(String role) {
		this.role = role;
	}

	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		if (enabled) this.enabled = "ACTIVE";
		else this.enabled = "DISABLED";
	}
	
	public void setEnabled(String enabled) {
		 this.enabled = enabled;
	}

	public String getState() {
		return state;
	}

	public void setState(Boolean state) {
		if (!state) this.state = "OFFLINE";
		else this.state = "ONLINE";
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	
	
}
