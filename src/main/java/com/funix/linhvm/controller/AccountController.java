package com.funix.linhvm.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
		model.put("listA", listA);
		return new ModelAndView("account",model);
	}
}
