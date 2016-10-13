package com.app.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * @author ramkumarsundarajan
 *
 */
public class ObjectUtil {
	
	
	
	
	public static boolean isNotNull(Object object){
		
		return object!=null;
	}
	
	/**
	 * returns true if this is a valid collection
	 * @param collection
	 * @return
	 */
	public static boolean validCollection(Collection collection){
		
		return collection!=null && !collection.isEmpty();
	}
	
	/**
	 * converts the stacktrace as string to overhead.
	 * @param t
	 * @return
	 */
	public static String getStackStraceAsString(Throwable t){
		
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		t.printStackTrace(pw);
		return sw.toString();
	}
	
	

}
