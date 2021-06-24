package bct.coding.challenge.fgracia.calculator.exception;

public class APIAccessException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public APIAccessException() {
		super();
	}

	public APIAccessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public APIAccessException(String message, Throwable cause) {
		super(message, cause);
	}

	public APIAccessException(String message) {
		super(message);
	}

	public APIAccessException(Throwable cause) {
		super(cause);
	}

	
	
}
