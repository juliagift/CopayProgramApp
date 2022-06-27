package org.juliagift.copaydrugprogram.exception;

public class ClaimNotFoundException extends Exception {

	/**
	 * Custom exception indicating no claims were found.
	 */
	private static final long serialVersionUID = 1L;

	public ClaimNotFoundException() {
		super();
	}

	public ClaimNotFoundException(String message) {
		super(message);
	}
}
