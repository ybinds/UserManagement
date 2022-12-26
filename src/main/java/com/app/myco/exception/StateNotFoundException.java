package com.app.myco.exception;

public class StateNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public StateNotFoundException() {
		super();
	}
	
	public StateNotFoundException(String msg) {
		super(msg);
	}
}
