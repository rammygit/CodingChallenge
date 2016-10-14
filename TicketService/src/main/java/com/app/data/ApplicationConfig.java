package com.app.data;

/**
 * Class to hold all the application related config. 
 * we can import from external properties file or
 * from cache and load on from DB backed by cache.
 * @author ramkumarsundarajan
 *
 */
public class ApplicationConfig {
	
	private int totalSeats = 0;
	
	public long holdIntervalTime = 10L;
	
	public static final String errorCode = "00000";
	
	public static final boolean printLog = false;
	
	
	public ApplicationConfig(int totalSeats){
		
		this.totalSeats = totalSeats;
	}
	
	public ApplicationConfig(int totalSeats,long holdTimeInterval){
		
		this.totalSeats = totalSeats;
		this.holdIntervalTime = holdTimeInterval;
	}
	
	public int getTotalSeats(){
		return this.totalSeats;
	}
	
	public long getHoldIntervalTime(){
		return this.holdIntervalTime;
	}
	
}
