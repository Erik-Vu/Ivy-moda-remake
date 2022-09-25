package com.funix.linhvm.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;


@Data
@Entity
@Table(name="products")
public class ProductEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private int id;
	
	private String name;
	
	private String type;

	private int price;
	
	private int lastPrice;
	
	private String mainImage;
	
	@Column(columnDefinition = "TEXT")
	private String description;
	
	@CreationTimestamp
	private Date create_at;

	public ProductEntity(int id, String name, String type, int price,int lastPrice, String mainImage, String description, Date create_at) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.price = price;
		this.lastPrice = lastPrice;
		this.mainImage = mainImage;
		this.description = description;
		this.create_at = create_at;
	}
	
	public ProductEntity() {
		super();
	}

	public ProductEntity(String name, String type, int price, int lastPrice ,String mainImage, String description) {
		super();
		this.name = name;
		this.type = type;
		this.price = price;
		this.lastPrice = lastPrice;
		this.mainImage = mainImage;
		this.description = description;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getMainImage() {
		return mainImage;
	}

	public void setMainImage(String mainImage) {
		this.mainImage = mainImage;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreate_at() {
		return create_at;
	}

	public void setCreate_at(Date create_at) {
		this.create_at = create_at;
	}

	public int getLastPrice() {
		return lastPrice;
	}

	public void setLastPrice(int lastPrice) {
		this.lastPrice = lastPrice;
	}

	
}
