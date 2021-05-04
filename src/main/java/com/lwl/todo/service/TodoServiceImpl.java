package com.lwl.todo.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import com.lwl.todo.model.Todo;
import com.lwl.todo.repo.TodoRepo;
import com.lwl.todo.service.exception.TaskNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TodoServiceImpl implements TodoService {

	@Autowired
	private TodoRepo todoRepo;

	@Override
	public Todo addTodo(Todo todo) {
		Assert.notNull(todo.getTaskTitle(), "Task list can't be null or empty");
		todo = todoRepo.save(todo);
		log.info("Task is added with id:{}", todo.getId());
		return todo;
	}

	@Override
	public boolean deleteTodo(String id) {
		Assert.notNull(id, "Task id can't be null or empty");
		Optional<Todo> optTodo = todoRepo.findById(id);
		if (optTodo.isPresent()) {
			Todo todo = optTodo.get();
			todoRepo.deleteById(todo.getId());
			log.info("Todo is deleted with id:{}", todo.getId());
			return true;
		} else {
			log.info("Todo with id:{} is not found", id);
		}
		return false;
	}

	@Override
	public Todo updateTodo(Todo todo) {
		Assert.notNull(todo.getId(), "Task id can't be null or empty");
		Assert.notNull(todo.getTaskTitle(), "Task titile can't be null or empty");
		Todo updatedTodo = todoRepo.save(todo);
		log.info("Task is updated {} and {}", todo.getTaskTitle(), todo.getTargetDate());
		return updatedTodo;
	}

	@Override
	public List<Todo> getTodosByUsername(String username) {
		List<Todo> taskList = todoRepo.findByUsernameAndStatus(username, true);
		log.info("Total {} active tasks found for user {}", taskList.size(), username);
		return taskList;
	}

	@Override
	public Todo getTodoById(String id) {
		Assert.notNull(id, "Task id can't be null or empty");
		Optional<Todo> optTodo = todoRepo.findById(id);
		if (optTodo.isPresent()) {
			Todo todo = optTodo.get();
			todoRepo.deleteById(todo.getId());
			log.info("Todo is found with id:{}", todo.getId());
			return todo;
		} else {

			log.info("Todo with id:{} is not found", id);
			throw new TaskNotFoundException("Task with id:" + id + " is not found");
		}

	}

}
