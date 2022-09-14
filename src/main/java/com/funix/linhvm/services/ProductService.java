package com.funix.linhvm.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.funix.linhvm.entity.ImageEntity;
import com.funix.linhvm.entity.ProductEntity;
import com.funix.linhvm.entity.TypeEntity;
import com.funix.linhvm.model.Product;


@Service
public interface ProductService {
	
	public Page<ProductEntity> getAllProduct();
	public Page<ProductEntity> getNextProduct(int page);
	public Product getProductByID(int pid);
	public String editProductByID(int id, Product product);
	public List<ProductEntity> searchProduct(String keyword);
	
	public void saveProduct(ProductEntity pro);
	
	public void saveTypeProduct(TypeEntity type);
	public List<TypeEntity> getAllType();
	public int findTypeID(String name);
	public String convertTypeId(int id);
	
	public void saveImage(int id,String img);
	public List<Integer> findListImageID(List<Integer> ids);
	public List<ImageEntity> findImagesByID(int id);
	
	public void deleteProducts(List<Integer> ids);
	
}
