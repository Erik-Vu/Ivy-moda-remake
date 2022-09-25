package com.funix.linhvm.model;

import lombok.Data;

@Data
public class Product {

	private String name;
	private int price;
	private int lastPrice;
	private String description;
	private String type;
	
	
	public Product() {
		super();
	}

	public Product(String name, int price, int lastPrice,
			String description, String type) {
		super();
		this.name = name;
		this.price = price;
		this.lastPrice = lastPrice;
		this.description = description;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getLastPrice() {
		return lastPrice;
	}

	public void setLastPrice(int lastPrice) {
		this.lastPrice = lastPrice;
	}

}
