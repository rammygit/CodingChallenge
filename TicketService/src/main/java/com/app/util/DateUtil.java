package com.app.util;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

/**
 * date utility for the application.
 * @author ramkumarsundarajan
 *
 */
public class DateUtil {

	
	private DateUtil(){
		
	}
	
	/**
	 * 
	 * @return
	 */
	public static long getCurrentTimeInMillis(){
		return Calendar.getInstance().getTimeInMillis();
	}
	
	/**
	 * 
	 * @param seconds
	 * @return
	 */
	public static long convertToMillis(int seconds){
		return TimeUnit.SECONDS.toMillis(seconds);
	}
	
	/**
	 * 
	 * @param millis
	 * @return
	 */
	public static long convertToSeconds(long millis){
		return TimeUnit.MILLISECONDS.toSeconds(millis);
	}
	
	/**
	 * 
	 * @param holdStartTime
	 * @param holdInterval
	 * @return
	 */
	public static boolean isHoldTimeExpired(long holdStartTime,long holdInterval){
		//System.out.println(holdStartTime + "" +holdInterval);
		if(holdStartTime == 0) return false;
		return (convertToSeconds(getCurrentTimeInMillis()-holdStartTime)) >= convertToSeconds(holdInterval);
	}
}
