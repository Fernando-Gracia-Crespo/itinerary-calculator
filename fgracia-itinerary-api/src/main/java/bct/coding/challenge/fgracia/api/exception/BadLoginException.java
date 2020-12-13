package bct.coding.challenge.fgracia.api.exception;

public class BadLoginException extends Exception {

	private static final long serialVersionUID = 1L;

	public BadLoginException() {
	}

	public BadLoginException(String message) {
		super(message);
	}

	public BadLoginException(Throwable cause) {
		super(cause);
	}

	public BadLoginException(String message, Throwable cause) {
		super(message, cause);
	}

	public BadLoginException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
