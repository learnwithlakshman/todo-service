package com.lwl.todo.auth.domain;

import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
	@Id
	private String userId;
	private String username;
	private String password;

}
