package com.funix.linhvm.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.funix.linhvm.entity.UserEntity;
import com.funix.linhvm.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		List<UserEntity> users = userRepository.findUserByEmail(userName);
		for (UserEntity userEntity : users) {
			if (!userEntity.isEnabled()) users.remove(userEntity);
		} 
		if (users.isEmpty()) {
	            System.out.println("User not found! " + userName);
	            throw new UsernameNotFoundException("User " + userName + " was not found in the database");
	        }
		UserEntity user = users.get(0);
		List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
		if (!user.isRole()) {
			 GrantedAuthority authority = new SimpleGrantedAuthority("USER");
             grantList.add(authority);
		} else {
			GrantedAuthority authorityAdmin = new SimpleGrantedAuthority("ADMIN");
            grantList.add(authorityAdmin);
		}
		UserDetails userDetails = (UserDetails) new User(user.getEmail(),
                user.getEncrytedPassword(), grantList);

		return userDetails;
	}

}
