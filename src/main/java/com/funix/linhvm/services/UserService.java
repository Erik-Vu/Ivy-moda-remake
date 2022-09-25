package com.funix.linhvm.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.funix.linhvm.entity.UserEntity;
import com.funix.linhvm.model.Account;
import com.funix.linhvm.model.User;

@Service
public interface UserService {

	public String saveUser(User user);
	public List<UserEntity> findUserByEmail(String email);
	public List<Account> getFirstUser();
	public Page<UserEntity> getNextUser(int page);
	public UserEntity confirmUser(String code);
	
}
