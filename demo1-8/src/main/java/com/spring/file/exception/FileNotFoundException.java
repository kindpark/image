package com.spring.file.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
//HttpStatus 빨간줄 해결
import org.springframework.http.HttpStatus;
@ResponseStatus(HttpStatus.NOT_FOUND)
public class FileNotFoundException extends RuntimeException{
	public FileNotFoundException(String message) {
		super(message);
	}
	public FileNotFoundException(String message, Throwable t) {
		super(message,t);
	}
}
