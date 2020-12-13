package bct.coding.challenge.fgracia.calculator.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import bct.coding.challenge.fgracia.calculator.exception.NoValidCityException;

@ControllerAdvice
public class ItineraryExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(value = {NoValidCityException.class})
	protected ResponseEntity<Object> handleNoValidCityException(final Exception e, final WebRequest request){
		return handleExceptionInternal(e, "PRUEBA", new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
}
