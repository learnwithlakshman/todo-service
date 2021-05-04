package com.lwl.todo.web;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lwl.todo.model.Todo;
import com.lwl.todo.service.TodoService;

@RestController
@RequestMapping ("/api/v1/task")
public class TodoController {

	@Autowired
	private TodoService todoService;

	@PostMapping
	public Todo addNewTodo(@RequestBody Todo todo) {
		String username = SecurityContextHolderUtil.getUserName();
		todo.setUsername(username);
		return todoService.addTodo(todo);
	}

	@GetMapping
	public List<Todo> getTodos() {
		String username = SecurityContextHolderUtil.getUserName();
		return todoService.getTodosByUsername(username);
	}

	@GetMapping("{id}")
	public Todo getTodo(@RequestParam("id") String id) {
		return todoService.getTodoById(id);
	}

	@PutMapping
	public Todo updateTodo(@RequestBody Todo todo) {
		return todoService.updateTodo(todo);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<HttpStatus> deleteTodo(@RequestParam("id") String id) {
		try {
			boolean isDeleted = todoService.deleteTodo(id);
			if (isDeleted)
				return new ResponseEntity<>(HttpStatus.OK);
			else
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
