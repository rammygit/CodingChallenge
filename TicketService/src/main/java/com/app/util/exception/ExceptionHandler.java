package com.app.util.exception;

/**
 * common interface for various exception handler implementation. 
 * can be wrapper, logged or thrown back.
 * @author ramkumarsundarajan
 *
 */
public interface ExceptionHandler {
	
	public void handle(Exception e,String message);

}
