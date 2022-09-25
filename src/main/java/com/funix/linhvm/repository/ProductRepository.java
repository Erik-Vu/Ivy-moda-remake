package com.funix.linhvm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.funix.linhvm.entity.ProductEntity;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
	
	@Modifying
	@Query(value="select * from products p where CONCAT(p.name, p.type, p.description, p.price) like %?1%", nativeQuery=true)
	List<ProductEntity> searchByName(@Param("keyword") String keyword);
	
	List<ProductEntity> findByType(String type);
}
