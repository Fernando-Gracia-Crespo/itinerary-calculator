package bct.coding.challenge.fgracia.calculator.exception;

public class NoReachableCityException extends Exception {

	private static final long serialVersionUID = 1L;

	public NoReachableCityException() {
		super();
	}

	public NoReachableCityException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NoReachableCityException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoReachableCityException(String message) {
		super(message);
	}

	public NoReachableCityException(Throwable cause) {
		super(cause);
	}
	
}
