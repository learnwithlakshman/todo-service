package com.lwl.todo.auth.repo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.lwl.todo.auth.domain.User;

public interface UserRepo extends MongoRepository<User,String> {

	Optional<User> findByUsername(String username);
}
