package com.app.data;

/**
 * Class to hold all the application related config. 
 * we can import from external properties file or
 * from cache and load on from DB backed by cache.
 * @author ramkumarsundarajan
 *
 */
public class ApplicationConfig {
	
	private static int totalSeats = 0;
	
	public static final long holdIntervalTime = 10L;
	
	public static final String errorCode = "00000";
	
	
	
	public static int getTotalSeats(){
		return totalSeats;
	}
	
	public static void setTotalSeats(int totalSeats){
		ApplicationConfig.totalSeats = totalSeats;
	}
	
	
	

}
