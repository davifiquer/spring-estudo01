package br.com.fiquer.springestudo.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ClientExceptionHandler {
	
	@ExceptionHandler(ClientNotFoundException.class)
	public ResponseEntity<StandardError> entityNotFound(ClientNotFoundException e, HttpServletRequest req){
		StandardError err = new StandardError();
		err.setTimestamp(Instant.now());
		err.setStatus(HttpStatus.NOT_FOUND.value());
		err.setError("Client not found");
		err.setMessage(e.getMessage());
		err.setPath(req.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}
	
}
