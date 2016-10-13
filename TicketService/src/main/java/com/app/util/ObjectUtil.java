package com.app.util;

import java.util.Collection;

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
	
	

}
