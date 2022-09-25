package com.funix.linhvm.hashing;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncrytedPassword {

	   public static String encrytePassword(String password) {
	        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	        return encoder.encode(password);
	    }
}
