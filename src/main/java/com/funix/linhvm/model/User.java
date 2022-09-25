package com.funix.linhvm.model;


import lombok.Data;

@Data
public class User {

	private String fullName;	
	private String email;	
	private String password;
	private String pass;
	private int phone;
	
	public User() {
	}

	public User(String fullName, String email, String password, String pass, int phone) {
		this.fullName = fullName;
		this.email = email;
		this.password = password;
		this.pass = pass;
		this.phone = phone;
	}
	
	public User(String fullName, String email, int phone) {
		this.fullName = fullName;
		this.email = email;
		this.phone = phone;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPhone() {
		return phone;
	}

	public void setPhone(int phone) {
		this.phone = phone;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}
	
	
}
