package com.lwl.todo.auth.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lwl.todo.auth.domain.AppUserDetails;
import com.lwl.todo.auth.domain.User;
import com.lwl.todo.auth.repo.UserRepo;
import com.lwl.todo.auth.service.exception.UserAlreadyExistsException;

@Service
public class AuthUserService implements UserDetailsService {

	@Autowired
	private UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> optionalUser = userRepo.findByUsername(username);
		optionalUser.orElseThrow(() -> new UsernameNotFoundException("User name Found!"));
		AppUserDetails appUserDetails = new AppUserDetails();
		appUserDetails.setUser(optionalUser.get());
		return appUserDetails;
	}

	public User addUser(User user) {
		Optional<User> optionalUser = userRepo.findByUsername(user.getUsername());
		if(optionalUser.isPresent())
			throw new UserAlreadyExistsException("With given email already user exists");
		return userRepo.save(user);
	}

}
