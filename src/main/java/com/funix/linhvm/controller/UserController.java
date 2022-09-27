package com.funix.linhvm.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.funix.linhvm.entity.UserEntity;
import com.funix.linhvm.model.User;
import com.funix.linhvm.services.SendEmail;
import com.funix.linhvm.services.UserService;

@RestController
public class UserController {

	@Autowired
	UserService userService;
		
	@Autowired
	SendEmail sendEmail;
	
	@GetMapping("/login")
	public ModelAndView getLogin() {
		return new ModelAndView("login");
	}
	
	@PostMapping("/beforelogout")
	public String getLogOut(int id) {
		userService.logOutUser(id);
		return "1";
	}
	
	@GetMapping("/register")
	public ModelAndView getRegister() {
		return new ModelAndView("register");
	}
	
	@PostMapping("/register")
	public String addUser(User user) {
		String result = userService.saveUser(user);
		return result;
	}
	
	@GetMapping("/confirm")
	public ModelAndView getConfirm() {
		return new ModelAndView("confirm");
	}
	
	@PostMapping("/confirm")
	public String postConfirm(String code, HttpServletRequest request) {
		String result = "0";
		UserEntity userEntity = userService.confirmUser(code);
		if (!userEntity.equals(null)) {
			User user = new User(userEntity.getFullName(), userEntity.getEmail(), userEntity.getPhone());
			request.getSession().setAttribute("user", user);
			result = "1";
		}
		return result;
	}
	
	@GetMapping("/users")
	public ModelAndView getAccount(Principal principal, HttpServletRequest request) {
		Map<String, Object> model = new HashMap<>();
		List<UserEntity> users = userService.findUserByEmail(principal.getName());
		request.getSession().setAttribute("id", users.get(0).getId());
		request.getSession().setAttribute("name", users.get(0).getFullName());
		request.getSession().setAttribute("email", users.get(0).getEmail());
		request.getSession().setAttribute("phone", users.get(0).getPhone());
		model.put("name", "Xin Chào, " + users.get(0).getFullName());
		if (users.get(0).isRole()) return new ModelAndView("admin",model);
		 return new ModelAndView("user",model);
	}
	
	
	@GetMapping("/403")
    public ModelAndView accessDenied(Principal principal) {
		 Map<String, Object> model = new HashMap<>();
        if (principal != null) {         
            String message = "Hi " + principal.getName() //
                    + "<br> You do not have permission to access this page!";
            model.put("message", message);
        }
        return new ModelAndView("403", model);
	 }
	
	@PostMapping("/sendemail")
	public String sendEmail(String email) {	
		return userService.getUserByEmail(email);
	}
	
	@PostMapping("/sendpassword")
	public String sendPassword(HttpServletRequest request, String password, String pass) {
		int id = (Integer) request.getSession().getAttribute("id");
		return userService.changePassword(id, password, pass);
	}
	
	@PostMapping("/sendtoken")
	public String sendToken(HttpServletRequest request) {	
		int id = (Integer) request.getSession().getAttribute("id");		
		return userService.sendToken(id);
	}
}
