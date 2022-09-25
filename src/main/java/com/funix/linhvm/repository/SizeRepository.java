package com.funix.linhvm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.funix.linhvm.entity.SizeEntity;

public interface SizeRepository extends JpaRepository<SizeEntity, Integer>{

	List<SizeEntity> findByProductID(int id);
}
