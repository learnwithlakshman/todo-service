package com.lwl.todo.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.lwl.todo.model.Todo;

public interface TodoRepo extends MongoRepository<Todo,String> {

	List<Todo> findByUsernameAndStatus(String username, boolean b);

}
