package com.funix.linhvm.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.funix.linhvm.entity.ImageEntity;
import com.funix.linhvm.entity.ProductEntity;
import com.funix.linhvm.entity.TypeEntity;
import com.funix.linhvm.model.Product;
import com.funix.linhvm.repository.ImageRepository;
import com.funix.linhvm.repository.ProductRepository;
import com.funix.linhvm.repository.TypeRepository;

@Service
public class ProductServiceImpl implements ProductService{
	
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private TypeRepository typeRepository;
	@Autowired
	private ImageRepository imageRepository;


	@Override
	public Page<ProductEntity> getAllProduct(){
		Page<ProductEntity> productEntities = productRepository.findAll(PageRequest.of(0, 10));
		return productEntities;
	}
	
	@Override
	public Page<ProductEntity> getNextProduct(int page) {
		Page<ProductEntity> productEntities = productRepository.findAll(PageRequest.of(page, 10));
		return productEntities;
	}
	
	@Override
	public Product getProductByID(int pid) {
		Optional<ProductEntity> productEntity = productRepository.findById(pid);
		List<ImageEntity> imageEntity = imageRepository.findByProduct(pid);
		Product product = new Product(productEntity.get().getName(), imageEntity.get(0).getImage(), imageEntity.get(1).getImage(), imageEntity.get(2).getImage(), imageEntity.get(3).getImage(), productEntity.get().getPrice(), productEntity.get().getDescription(), productEntity.get().getType());
		return product;
	}
	
	@Override
	public List<ProductEntity> searchProduct(String keyword) {
		return productRepository.searchByName(keyword);
	}

	@Override
	public String editProductByID(int id, Product product) {
		Optional<ProductEntity> productEntity = productRepository.findById(id);
		List<ImageEntity> imageEntity = imageRepository.findByProduct(id);
		productEntity.get().setName(product.getName());
		productEntity.get().setPrice(product.getPrice());
		productEntity.get().setDescription(product.getDescription());
		productEntity.get().setType_id(product.getType());
		productRepository.save(productEntity.get());
		String[] arr = {product.getImageOne(),product.getImageTwo(),product.getImageThree(),product.getImageFour()};
		for (int i = 0; i < 4; i++) {
			imageEntity.get(i).setImage(arr[i]);
			imageRepository.save(imageEntity.get(i));
		}
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
	public List<ImageEntity> findImagesByID(int id) {
		List<ImageEntity> list = imageRepository.findByProduct(id);
		return list;
	}

	


	

	

	
	
	
}
