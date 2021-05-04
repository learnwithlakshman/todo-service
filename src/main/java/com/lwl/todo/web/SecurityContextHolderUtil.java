package com.lwl.todo.web;

import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityContextHolderUtil {

		public static String getUserName() {
			return SecurityContextHolder.getContext().getAuthentication().getName();
		}
}
