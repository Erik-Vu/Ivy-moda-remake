package com.funix.linhvm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.funix.linhvm.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer>{
	List<UserEntity> findUserByEmail(String email);
	List<UserEntity> findUserByCode(String code);
	
	@Modifying
	@Query(value="select * from users u where CONCAT(u.email, u.full_name, u.phone) like %?1%", nativeQuery=true)
	List<UserEntity> searchByName(@Param("keyword") String keyword);
}
