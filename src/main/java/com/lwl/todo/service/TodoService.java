package com.lwl.todo.service;

import java.util.List;

import com.lwl.todo.model.Todo;

public interface TodoService {

	public Todo addTodo(Todo todo);

	public boolean deleteTodo(String id);

	public Todo updateTodo(Todo todo);

	public List<Todo> getTodosByUsername(String username);

	public List<Todo> getArchivedTodos(String username);

	public Todo getTodoById(String id);
}
