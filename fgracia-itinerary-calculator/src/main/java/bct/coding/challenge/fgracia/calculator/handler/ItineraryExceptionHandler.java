package bct.coding.challenge.fgracia.calculator.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import bct.coding.challenge.fgracia.calculator.exception.APIAccessException;
import bct.coding.challenge.fgracia.calculator.exception.NoReachableCityException;
import bct.coding.challenge.fgracia.calculator.exception.NoValidCityException;
import bct.coding.challenge.fgracia.calculator.exception.NoValidTimeException;

@ControllerAdvice
public class ItineraryExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(value = {NoValidCityException.class, NoReachableCityException.class})
	protected ResponseEntity<Object> handleCityException(final Exception e, final WebRequest request){
		return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler(value = {APIAccessException.class})
	protected ResponseEntity<Object> handleAPIAccessException(final Exception e, final WebRequest request){
		return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(), HttpStatus.BAD_GATEWAY, request);
	}
	
	@ExceptionHandler(value = {NoValidTimeException.class})
	protected ResponseEntity<Object> handleNoValidTimeException(final Exception e, final WebRequest request){
		NoValidTimeException nvte = (NoValidTimeException) e;
		String message = "Error retrieving the arrival and departure times of an itinerary: ";
		message += "{departure time:\""+nvte.getItineraryDTO().getDepartureTime()+"\", arrival time: "+nvte.getItineraryDTO().getArrivalTime()+"\"}";
		return handleExceptionInternal(e, message, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}
	
	@ExceptionHandler(value = {Exception.class})
	protected ResponseEntity<Object> handleUnknownException(final Exception e, final WebRequest request){
		return handleExceptionInternal(e, "Unknown error. Please contact the administrator", new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}
	
}
