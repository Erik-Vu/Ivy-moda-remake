package com.funix.linhvm.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.funix.linhvm.entity.UserEntity;
import com.funix.linhvm.model.Account;
import com.funix.linhvm.model.User;

@Service
public interface UserService {

	public String saveUser(User user);
	public List<UserEntity> findUserByEmail(String email);
	public String getUserByEmail(String email);
	public List<Account> getFirstUser();
	public List<Account> getNextUser(int page);
	public UserEntity confirmUser(String code);
	public String deleteUser(int id);
	public void resetUser(int id);
	public Account findUserByID(int id);
	public String editUserByID(Account acc);
	public List<Account> searchUser(String keyword);
	public void logOutUser(int id);
	public String changePassword(int id, String password, String pass);
	public String sendToken(int id);
}
