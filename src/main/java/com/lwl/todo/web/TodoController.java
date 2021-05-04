package com.lwl.todo.web;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
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

import com.lwl.todo.auth.dto.TodoDTO;
import com.lwl.todo.model.Todo;
import com.lwl.todo.service.TodoService;

@RestController
@RequestMapping("/api/v1/task")
public class TodoController {

	@Autowired
	private TodoService todoService;

	@Autowired
	private ModelMapper modelMapper;

	@PostMapping
	public Todo addNewTodo(@RequestBody TodoDTO todoDto) {
		String username = SecurityContextHolderUtil.getUserName();
		Todo todo = modelMapper.map(todoDto, Todo.class);
		todo.setUsername(username);
		return todoService.addTodo(todo);
	}

	@GetMapping
	public List<TodoDTO> getTodos() {
		String username = SecurityContextHolderUtil.getUserName();
		return todoService.getTodosByUsername(username).stream().map(e -> modelMapper.map(e, TodoDTO.class))
				.collect(Collectors.toList());
	}
	
	@GetMapping("/archive")
	public List<TodoDTO> getArchivedTodos() {
		String username = SecurityContextHolderUtil.getUserName();
		return todoService.getArchivedTodos(username).stream().map(e -> modelMapper.map(e, TodoDTO.class))
				.collect(Collectors.toList());
	}

	@GetMapping("{id}")
	public TodoDTO getTodo(@RequestParam("id") String id) {
		TodoDTO todoDTO = modelMapper.map(todoService.getTodoById(id), TodoDTO.class);
		return todoDTO;
	}

	@PutMapping
	public TodoDTO updateTodo(@RequestBody TodoDTO todoDTO) {
		Todo todo = todoService.getTodoById(todoDTO.getId());
		todo.setDescription(todoDTO.getDescription());
		todo.setTargetDate(todoDTO.getTargetDate());
		todo.setTaskTitle(todoDTO.getTaskTitle());
		todoDTO = modelMapper.map(todoService.updateTodo(todo), TodoDTO.class);
		return todoDTO;
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
