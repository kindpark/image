package com.spring.file.exception;
/*
파일 사이즈가 넘으면 스프링MVC에 넘어오기 전에 이미 해당 예외가 터져버립니다. 따라서 스프링 MVC가 사용하는 @ExceptionHandler에서 해당 예외를 잡을 수 없습니다.
resolve-lazily=true 옵션을 주면 실제 해당 파일에 접근하는 시점에 파일을 체크합니다. 그러니까 스프링 MVC에 충분히 들어와서 @ExceptionHandler에서 해당 예외를 잡을 수 있는 시점에 체크하고 파싱하는 것이지요.
그래서 어노테이션을 사용해도 예외를 잡을 수 없음
*/
public class FileStorageException extends RuntimeException{
	public FileStorageException(String message) {
		super(message);
	}
	public FileStorageException(String message, Throwable t) {
		super(message, t);
	}
}
