package com.funix.linhvm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.funix.linhvm.entity.TypeEntity;

public interface TypeRepository extends JpaRepository<TypeEntity, Integer>{
	
	List<TypeEntity> findByType(String type);

}
