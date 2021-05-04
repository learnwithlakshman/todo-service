package com.lwl.todo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.lwl.todo.auth.config.JwtUtil;
import com.lwl.todo.auth.domain.AuthResponse;
import com.lwl.todo.auth.domain.User;
import com.lwl.todo.auth.service.AuthUserService;
import com.lwl.todo.auth.service.exception.UserAlreadyExistsException;


@RestController
@RequestMapping("/auth")
public class LoginController {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private AuthUserService userService;
	
	@Autowired
	private AuthUserService authUserService;
	
	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("/register")
	public User registerUser(@RequestBody User user) {
		try {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		return userService.addUser(user);
		}catch (UserAlreadyExistsException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
		}
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody User user) throws Exception {
		authenticate(user.getUsername(), user.getPassword());
		final UserDetails userDetails = authUserService.loadUserByUsername(user.getUsername());
		final String token = jwtUtil.generateToken(userDetails);
		return ResponseEntity.ok(new AuthResponse(token));
	}
	
	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}