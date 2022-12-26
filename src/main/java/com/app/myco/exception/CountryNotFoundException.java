package com.app.myco.exception;

public class CountryNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CountryNotFoundException() {
		super();
	}
	
	public CountryNotFoundException(String msg) {
		super(msg);
	}
}
