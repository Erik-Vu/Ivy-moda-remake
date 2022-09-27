package com.funix.linhvm.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.funix.linhvm.model.Account;
import com.funix.linhvm.services.UserService;

@RestController
@RequestMapping("/admin")
public class AccountController {

	
	@Autowired
	UserService userService;
	
	@GetMapping("/account") 
	public ModelAndView getAccount() {
		Map<String, Object> model = new HashMap<>();
		List<Account> listA = userService.getFirstUser();
		int pageNumber = 0;
		model.put("pageNumber", pageNumber);
		model.put("listA", listA);
		return new ModelAndView("account",model);
	}
	
	@PostMapping("/pageaccount")
	public ModelAndView getPage(int page) {
		Map<String, Object> model = new HashMap<>();
		List<Account> listA = userService.getNextUser(page);
		model.put("pageNumber", page);
		model.put("listA", listA);
		return new ModelAndView("account",model);
	}
	
	@PostMapping("/deleteuser")
	public String deleteUser(int id) {	
		return userService.deleteUser(id);
	}
	
	@PostMapping("/resetuser")
	public String resetUser(int id) {
		userService.resetUser(id);
		return "successfully";
	}
	
	@GetMapping("/edituser") 
	public ModelAndView getEditUser(int id) {
		Map<String, Object> model = new HashMap<>();
		Account acc = userService.findUserByID(id);
		model.put("id", id);
		model.put("account", acc);
		return new ModelAndView("edit-user",model);
	}
	
	@PostMapping("/edituser")
	public String editUser(Account acc) {
		return userService.editUserByID(acc);
	}
	
	@PostMapping("/searchuser")
	public ModelAndView searchUser(String keyword) {
		Map<String, Object> model = new HashMap<>();
		List<Account> listA = userService.searchUser(keyword);
		model.put("listA", listA);
		return new ModelAndView("account",model);
	}
}
