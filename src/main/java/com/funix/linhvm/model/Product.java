package com.funix.linhvm.model;

import lombok.Data;

@Data
public class Product {

	private String name;
	private String imageOne;
	private String imageTwo;
	private String imageThree;
	private String imageFour;
	private int price;
	private String description;
	private String type;
	
	
	public Product() {
		super();
	}

	public Product(String name, String imageOne, String imageTwo, String imageThree, String imageFour, int price,
			String description, String type) {
		super();
		this.name = name;
		this.imageOne = imageOne;
		this.imageTwo = imageTwo;
		this.imageThree = imageThree;
		this.imageFour = imageFour;
		this.price = price;
		this.description = description;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImageOne() {
		return imageOne;
	}

	public void setImageOne(String imageOne) {
		this.imageOne = imageOne;
	}

	public String getImageTwo() {
		return imageTwo;
	}

	public void setImageTwo(String imageTwo) {
		this.imageTwo = imageTwo;
	}

	public String getImageThree() {
		return imageThree;
	}

	public void setImageThree(String imageThree) {
		this.imageThree = imageThree;
	}

	public String getImageFour() {
		return imageFour;
	}

	public void setImageFour(String imageFour) {
		this.imageFour = imageFour;
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
	
}
