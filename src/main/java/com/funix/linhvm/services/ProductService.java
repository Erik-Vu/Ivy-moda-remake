package com.funix.linhvm.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.funix.linhvm.entity.ImageEntity;
import com.funix.linhvm.entity.ProductEntity;
import com.funix.linhvm.entity.SizeEntity;
import com.funix.linhvm.entity.SizeNameEntity;
import com.funix.linhvm.entity.TypeEntity;
import com.funix.linhvm.model.Product;


@Service
public interface ProductService {
	
	public Page<ProductEntity> getAllProduct();
	public Page<ProductEntity> getLastProduct();
	public Page<ProductEntity> getNextProduct(int page);
	public String editProductByID(int id, Product product, String file);
	public List<ProductEntity> searchProduct(String keyword);
	public ProductEntity getProductByID(int id);
	public List<ProductEntity> getProductByType(String type);
	
	public void saveProduct(ProductEntity pro);
	public void deleteProducts(List<Integer> ids);
	
	public void saveTypeProduct(TypeEntity type);
	public List<TypeEntity> getAllType();
	public int findTypeID(String name);
	public String convertTypeId(int id);
	public void deleteType(int id);
	
	
	public void saveImage(int id,String img);
	public List<Integer> findListImageID(List<Integer> ids);
	public List<ImageEntity> findImagesByID(int id);
	public void deleteImage(int id);
	
	public void saveSize(SizeNameEntity size);
	public List<SizeNameEntity> getAllSize();
	public void deleteSizeName(int id);
	
	public void saveSizes(int id ,String[] sizes);
	public List<Integer> findListSizeID(List<Integer> ids);
	public List<SizeEntity> findListSizeEntity(int id);
	public void deleteSizes(int id);
}
