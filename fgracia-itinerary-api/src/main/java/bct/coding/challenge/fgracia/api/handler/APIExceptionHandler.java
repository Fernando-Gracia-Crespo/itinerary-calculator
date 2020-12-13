package bct.coding.challenge.fgracia.api.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import bct.coding.challenge.fgracia.api.exception.BadLoginException;
import bct.coding.challenge.fgracia.api.exception.ResourceNotFoundException;

@ControllerAdvice
public class APIExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(value = {ResourceNotFoundException.class})
	protected ResponseEntity<Object> handleNoValidCityException(final Exception e, final WebRequest request){
		return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}
	
	@ExceptionHandler(value = {BadLoginException.class})
	protected ResponseEntity<Object> handleBadLoginException(final Exception e, final WebRequest request){
		return handleExceptionInternal(e, "Wrong Credentials", new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
	}
	
	@ExceptionHandler(value = {Exception.class})
	protected ResponseEntity<Object> handleUnknownException(final Exception e, final WebRequest request){
		return handleExceptionInternal(e, "Unknown error. Please contact the administrator", new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

}
