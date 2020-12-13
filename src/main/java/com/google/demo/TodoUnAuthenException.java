package com.google.demo;

public class TodoUnAuthenException extends RuntimeException{

	public TodoUnAuthenException() {
		super("Token Invalid")
	}

}
