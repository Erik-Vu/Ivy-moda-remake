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
	
	private String mainImage;
	
	public String getMainImage() {
		return mainImage;
	}

	public void setMainImage(String mainImage) {
		this.mainImage = mainImage;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(columnDefinition = "TEXT")
	private String description;
	
	private int user_id;
	
	@CreationTimestamp
	private Date create_at;

	public ProductEntity(int id, String name, String type, int price, String description, int user_id, Date create_at) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.price = price;
		this.description = description;
		this.user_id = user_id;
		this.create_at = create_at;
	}
	
	public ProductEntity() {
		super();
	}

	public ProductEntity(String name, String type, int price, String description, int user_id) {
		super();
		this.name = name;
		this.type = type;
		this.price = price;
		this.description = description;
		this.user_id = user_id;
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

	public void setType_id(String type) {
		this.type = type;
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

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public Date getCreate_at() {
		return create_at;
	}

	public void setCreate_at(Date create_at) {
		this.create_at = create_at;
	}
	
	
}
