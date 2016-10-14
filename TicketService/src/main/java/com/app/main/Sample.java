package com.app.main;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class Sample {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub

		System.out.println("hello");
		
		long startTime = Calendar.getInstance().getTimeInMillis();
		
		System.out.println("starttime -> "+startTime);
		
		long inmillis = TimeUnit.SECONDS.toMillis(5);
		
		long secodstohold = TimeUnit.MILLISECONDS.toSeconds(inmillis);
		
		System.out.println("secodstohold ->"+secodstohold);
		
		Thread.sleep(inmillis+3);
		
		long endtime = Calendar.getInstance().getTimeInMillis();
		
		System.out.println("endtime -> "+endtime);
		
		long elapsedSec = TimeUnit.MILLISECONDS.toSeconds(endtime - startTime);
		
		System.out.println("elapsed -> "+elapsedSec);
		
		//System.out.println(seconds/1000);
	}

}
