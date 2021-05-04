package com.lwl.todo.auth.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoDTO {
	private String id;
	private String taskTitle;
	private String description;
	private LocalDate targetDate;
}
