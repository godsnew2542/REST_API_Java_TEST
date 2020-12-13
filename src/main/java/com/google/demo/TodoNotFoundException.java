package com.google.demo;

public class TodoNotFoundException extends RuntimeException {

	public TodoNotFoundException() {
		// TODO Auto-generated constructor stub
	}

	public TodoNotFoundException(long id) {
		super("Could not find todo " + id);
	}

}
