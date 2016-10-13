package com.app.util.exception;

/**
 * custom to detect exception thrown from InMemory Data Handler class.
 * @author ramkumarsundarajan
 *
 */
public class DBException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public DBException(String message){
		super(message);
	}
	
	public DBException(Throwable t){
		super(t);
	}

}
