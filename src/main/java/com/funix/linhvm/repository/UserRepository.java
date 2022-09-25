package com.funix.linhvm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.funix.linhvm.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer>{
	List<UserEntity> findUserByEmail(String email);
	List<UserEntity> findUserByCode(String code);
}
