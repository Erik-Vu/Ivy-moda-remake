package com.funix.linhvm.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.funix.linhvm.entity.ImageEntity;
import com.funix.linhvm.entity.ProductEntity;
import com.funix.linhvm.entity.SizeEntity;
import com.funix.linhvm.entity.SizeNameEntity;
import com.funix.linhvm.entity.TypeEntity;
import com.funix.linhvm.model.Product;
import com.funix.linhvm.repository.ImageRepository;
import com.funix.linhvm.repository.ProductRepository;
import com.funix.linhvm.repository.SizeNameRepository;
import com.funix.linhvm.repository.SizeRepository;
import com.funix.linhvm.repository.TypeRepository;

@Service
public class ProductServiceImpl implements ProductService{
	
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private TypeRepository typeRepository;
	@Autowired
	private ImageRepository imageRepository;
	@Autowired
	private SizeNameRepository sizeNameRepository;
	@Autowired
	private SizeRepository sizeRepository;


	@Override
	public Page<ProductEntity> getAllProduct(){
		Page<ProductEntity> productEntities = productRepository.findAll(PageRequest.of(0, 10));
		return productEntities;
	}
	
	@Override
	public Page<ProductEntity> getLastProduct(){
		Page<ProductEntity> productEntities = productRepository.findAll(PageRequest.of(0, 12, Sort.by("price").descending()));
		return productEntities;
	}
	
	@Override
	public Page<ProductEntity> getNextProduct(int page) {
		Page<ProductEntity> productEntities = productRepository.findAll(PageRequest.of(page, 10));
		return productEntities;
	}
	
	@Override
	public List<ProductEntity> searchProduct(String keyword) {
		return productRepository.searchByName(keyword);
	}

	@Override
	public ProductEntity getProductByID(int id) {
		Optional<ProductEntity> productEntity = productRepository.findById(id);
		return productEntity.get();
	}
	
	@Override
	public List<ProductEntity> getProductByType(String type) {
		List<ProductEntity> productEntity = productRepository.findByType(type);
		return productEntity;
	}
	
	
	@Override
	public String editProductByID(int id, Product product, String file) {
		Optional<ProductEntity> productEntity = productRepository.findById(id);
		productEntity.get().setName(product.getName());
		productEntity.get().setPrice(product.getPrice());
		productEntity.get().setLastPrice(product.getLastPrice());
		productEntity.get().setDescription(product.getDescription());
		productEntity.get().setType(product.getType());
		if (file != "") productEntity.get().setMainImage(file);
		productRepository.save(productEntity.get());
		return "success";
	}

	@Override
	public void saveProduct(ProductEntity pro) {
		productRepository.save(pro);
	}

	@Override
	public void saveTypeProduct(TypeEntity type) {
		typeRepository.save(type);
	}

	@Override
	public List<TypeEntity> getAllType() {
		List<TypeEntity> typeEntities = typeRepository.findAll();
		return typeEntities;
	}

	@Override
	public int findTypeID(String name) {
		List<TypeEntity> types = typeRepository.findByType(name);
		return types.get(0).getId();
	}

	@Override
	public String convertTypeId(int id) {
		Optional<TypeEntity> typeEntity = typeRepository.findById(id);
		return typeEntity.get().getType();
	}
	
	@Override
	public void saveImage(int id, String img) {
		ImageEntity imageEntity = new ImageEntity(id,img);
		imageRepository.save(imageEntity);
		
	}

	@Override
	public void deleteProducts(List<Integer> ids) {
		productRepository.deleteAllById(ids);
		imageRepository.deleteAllById(findListImageID(ids));
		sizeRepository.deleteAllById(findListSizeID(ids));
	}

	@Override
	public List<Integer> findListImageID(List<Integer> ids) {
		List<Integer> list = new ArrayList<>();
		List<ImageEntity> listImages = new ArrayList<>();
		for (Integer integer : ids) {
			List<ImageEntity> listImage = imageRepository.findByProduct(integer);
			listImages.addAll(listImage);
		};
		for (ImageEntity img : listImages) {
			list.add(img.getId());
		}
		return list;
	}
	
	@Override
	public List<Integer> findListSizeID(List<Integer> ids) {
		List<Integer> list = new ArrayList<>();
		List<SizeEntity> listSizes = new ArrayList<>();
		for (Integer integer : ids) {
			List<SizeEntity> listSize = sizeRepository.findByProductID(integer);
			listSizes.addAll(listSize);
		};
		for (SizeEntity size : listSizes) {
			list.add(size.getId());
		}
		return list;
	}

	@Override
	public List<ImageEntity> findImagesByID(int id) {
		List<ImageEntity> list = imageRepository.findByProduct(id);
		return list;
	}
	
	@Override
	public void deleteImage(int id) {
		imageRepository.deleteById(id);
	}

	@Override
	public void saveSize(SizeNameEntity size) {
		sizeNameRepository.save(size);
	}

	@Override
	public List<SizeNameEntity> getAllSize() {		
		return sizeNameRepository.findAll();
	}

	@Override
	public void deleteSizeName(int id) {
		sizeNameRepository.deleteById(id);
	}

	@Override
	public void saveSizes(int id, String[] sizes) {
		for (String name : sizes) {
			SizeEntity sizeName = new SizeEntity(id,name);
			sizeRepository.save(sizeName);
		}
		
	}
	
	@Override
	public List<SizeEntity> findListSizeEntity(int id) {
		return sizeRepository.findByProductID(id);
	}
	
	@Override
	public void deleteSizes(int id) {
		sizeRepository.deleteById(id);
	}

	@Override
	public void deleteType(int id) {
		typeRepository.deleteById(id);
	}

	

	

	

	

	


	

	

	
	
	
}
