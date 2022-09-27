package com.funix.linhvm.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
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
		List<UserEntity> users = userRepository.findUserByEmail(email);
		users.get(0).setState(true);
		userRepository.save(users.get(0));
		return users;
	}
	
	@Override
	public String getUserByEmail(String email) {
		String result = "0";
		List<UserEntity> users = userRepository.findUserByEmail(email);
		for (UserEntity userEntity : users) {
			if (!userEntity.isEnabled()) users.remove(userEntity);
		}
		if (!users.isEmpty()) {
			resetUser(users.get(0).getId());
			result = "1";
		}
		return result;
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
	public List<Account> getNextUser(int page) {
		Page<UserEntity> userEntities = userRepository.findAll(PageRequest.of(page, 10));
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
	public UserEntity confirmUser(String code) {
		List<UserEntity> list = userRepository.findUserByCode(code);
		if (list.isEmpty()) return null;
		UserEntity user = list.get(0);
		user.setEnabled(true);
		userRepository.save(user);
		return user;
	}


	@Override
	public String deleteUser(int id) {
		String result = "successfully";
		Optional<UserEntity> user = userRepository.findById(id);
		if (user.get().isState()) {
			user.get().setEnabled(false);
			userRepository.save(user.get());
			result = "Tài khoản đang online - tắt kích hoạt";
		}
		else userRepository.deleteById(id);
		return result;
	}


	@Override
	public void resetUser(int id) {
		Optional<UserEntity> user = userRepository.findById(id);
		int leftLimit = 48; // numeral '0'
	    int rightLimit = 122; // letter 'z'
	    int targetStringLength = 8;
	    Random random = new Random();

	    String pass = random.ints(leftLimit, rightLimit + 1)
	      .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
	      .limit(targetStringLength)
	      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
	      .toString();
	    sendEmail.sendSimpleEmail(user.get().getEmail(), pass);
	   user.get().setEncrytedPassword(EncrytedPassword.encrytePassword(pass));
	   userRepository.save(user.get());
	}


	@Override
	public Account findUserByID(int id) {
		Optional<UserEntity> user = userRepository.findById(id);
		Account acc = new Account(user.get().getId(), user.get().getEmail(), user.get().getFullName(), user.get().getPhone(), user.get().getCreateAt());
		acc.setRole(user.get().isRole());
		acc.setEnabled(user.get().isEnabled());
		acc.setState(user.get().isState());
		return acc;
	}


	@Override
	public String editUserByID(Account acc) {
		Optional<UserEntity> user = userRepository.findById(acc.getId());
		user.get().setFullName(acc.getFullName());
		user.get().setPhone(acc.getPhone());
		if (acc.getRole().equalsIgnoreCase("USER")) user.get().setRole(false);
		else user.get().setRole(true);
		if (acc.getEnabled().equalsIgnoreCase("ACTIVE")) user.get().setEnabled(true);
		else user.get().setEnabled(false);
		userRepository.save(user.get());
		return "successfully";
	}


	@Override
	public List<Account> searchUser(String keyword) {
		 List<UserEntity> users = userRepository.searchByName(keyword);
		 List<Account> accounts = new ArrayList<>();
			for (UserEntity userEntity : users) {
				Account acc = new Account(userEntity.getId(), userEntity.getEmail(), userEntity.getFullName(), userEntity.getPhone(), userEntity.getCreateAt());
				acc.setRole(userEntity.isRole());
				acc.setEnabled(userEntity.isEnabled());
				acc.setState(userEntity.isState());
				accounts.add(acc);
			}
		return accounts;
	}


	@Override
	public void logOutUser(int id) {
		Optional<UserEntity> user = userRepository.findById(id);
		user.get().setState(false);
		userRepository.save(user.get());
	}


	@Override
	public String changePassword(int id, String password, String pass) {
		String result = "0";
		Optional<UserEntity> user = userRepository.findById(id);
		if (user.get().getCode().equals(password)) {
			user.get().setEncrytedPassword(EncrytedPassword.encrytePassword(pass));
			userRepository.save(user.get());
			result = "1";
		}
		return result;
	}


	@Override
	public String sendToken(int id) {
		Optional<UserEntity> user = userRepository.findById(id);
		UUID uuid = UUID.randomUUID();
		user.get().setCode(uuid.toString());
		sendEmail.sendSimpleEmail(user.get().getEmail(), uuid.toString());
		userRepository.save(user.get());
		return "1";
	}


	
}
