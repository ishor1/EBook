package com.EBook.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.EBook.Model.User;
import com.EBook.Repository.UserRepository;
@Service
public class CustomUserDetails implements UserDetailsService {
@Autowired
private UserRepository userRepo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	User user=this.userRepo.findByEmail(username);
		return user;
	}

}
