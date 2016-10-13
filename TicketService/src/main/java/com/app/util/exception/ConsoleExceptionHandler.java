package com.app.util.exception;

import com.app.util.ObjectUtil;

/**
 * converts the exception to the String using print stream and writes it back to the 
 * console or to server log.
 * @author ramkumarsundarajan
 *
 */
public class ConsoleExceptionHandler implements ExceptionHandler{

	@Override
	public void handle(Exception e, String message) {
		// TODO Auto-generated method stub
		StringBuilder builder = new StringBuilder(ObjectUtil.getStackStraceAsString(e));
		builder.append(" with Message -> "+message);
		System.out.println(builder.toString());
	}

}
