package com.app.main;

import java.util.concurrent.TimeUnit;

public class Sample {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.println("hello");
		
		System.out.println(TimeUnit.SECONDS.toMillis(30));
		
		long inmillis = TimeUnit.SECONDS.toMillis(30);
		
		System.out.println(inmillis);
	}

}
