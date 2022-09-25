package com.funix.linhvm.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.funix.linhvm.entity.UserEntity;
import com.funix.linhvm.hashing.EncrytedPassword;
import com.funix.linhvm.model.Account;
import com.funix.linhvm.model.User;
import com.funix.linhvm.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
		    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	SendEmail sendEmail;
	
	
	public String saveUser(User user) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(user.getEmail());
		String result = "0";
		if (matcher.find() && user.getPassword().equals(user.getPass())) { 
			UUID uuid = UUID.randomUUID();
			UserEntity userEntity = new UserEntity();
			userEntity.setEmail(user.getEmail());
			userEntity.setFullName(user.getFullName());
			userEntity.setPhone(user.getPhone());
			userEntity.setCode(uuid.toString());
			userEntity.setEncrytedPassword(EncrytedPassword.encrytePassword(user.getPassword()));
			userRepository.save(userEntity);
			sendEmail.sendSimpleEmail(user.getEmail(), uuid.toString());
			result = "1";
		} else if (!user.getPassword().equals(user.getPass())) result = "2";
		else result = "3";
		return result;
	}


	@Override
	public List<UserEntity> findUserByEmail(String email) {
		return userRepository.findUserByEmail(email);
	}


	@Override
	public List<Account> getFirstUser() {
		Page<UserEntity> userEntities = userRepository.findAll(PageRequest.of(0, 10));
		List<Account> accounts = new ArrayList<>();
		for (UserEntity userEntity : userEntities) {
			Account acc = new Account(userEntity.getId(), userEntity.getEmail(), userEntity.getFullName(), userEntity.getPhone(), userEntity.getCreateAt());
			acc.setRole(userEntity.isRole());
			acc.setEnabled(userEntity.isEnabled());
			acc.setState(userEntity.isState());
			accounts.add(acc);
		}
		return accounts;
	}


	@Override
	public Page<UserEntity> getNextUser(int page) {
		Page<UserEntity> userEntities = userRepository.findAll(PageRequest.of(page, 10));
		return userEntities;
	}


	@Override
	public UserEntity confirmUser(String code) {
		List<UserEntity> list = userRepository.findUserByCode(code);
		if (list.isEmpty()) return null;
		UserEntity user = list.get(0);
		user.setEnabled(true);
		userRepository.save(user);
		return user;
	}
}
