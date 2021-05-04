package com.lwl.todo.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Todo {
	
		@Id
		private String id;
		private String taskTitle;
		private String description;
		private boolean status=true;
		private LocalDate targetDate;
		private String username;
}
