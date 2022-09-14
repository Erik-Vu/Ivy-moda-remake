package com.funix.linhvm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.funix.linhvm.entity.ImageEntity;

public interface ImageRepository extends JpaRepository<ImageEntity, Integer>{

	List<ImageEntity> findByProduct(int id);
}
