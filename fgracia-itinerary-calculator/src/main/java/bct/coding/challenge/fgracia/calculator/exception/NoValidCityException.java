package bct.coding.challenge.fgracia.calculator.exception;

public class NoValidCityException extends Exception {

	private static final long serialVersionUID = 1L;

	public NoValidCityException() {
		super();
	}

	public NoValidCityException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NoValidCityException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoValidCityException(String message) {
		super(message);
	}

	public NoValidCityException(Throwable cause) {
		super(cause);
	}
	
}
