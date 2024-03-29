package org.juliagift.copaydrugprogram.exception;

public class UserNotFoundException extends Exception {

	/**
	 * Custom exception indicating no users were found.
	 */
	private static final long serialVersionUID = 1L;

	public UserNotFoundException() {
		super();
	}

	public UserNotFoundException(String message) {
		super(message);
	}
}
