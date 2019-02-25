package com.mc.exception;


public class CityConnectivityException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public CityConnectivityException(){
		super();
	}
	public CityConnectivityException(String msg) {
		super(msg);
	}
}
