package com.app.main;

import com.app.data.ApplicationConfig;
import com.app.manager.TicketServiceManager;
import com.app.model.SeatHold;
import com.app.service.ITicketService;
import com.app.service.TicketService;
import com.app.util.exception.ConsoleExceptionHandler;

/**
 * main application 
 * 
 * @author ramkumarsundarajan
 *
 */
public class MyApplication {

	
	/**
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		ApplicationConfig applicationConfig = new ApplicationConfig(10);
		
		ITicketService service = new TicketService(
				new TicketServiceManager(
						new ConsoleExceptionHandler(),applicationConfig));
		
		int totalAvailableSeats = service.numSeatsAvailable();
		
		System.out.println("total available seats -> "+totalAvailableSeats);
		
		/**
		 * hold 2
		 */
		SeatHold hold = service.findAndHoldSeats(2, "sample@gmail.com");
		
		/**
		 * reserve 2
		 */
		String confirmation = service.reserveSeats(hold.getId(), "sample@gmail.com");
		
		/**
		 * hold 2
		 */
		 service.findAndHoldSeats(2, "sample@gmail.com");
		
		/**
		 * hold 2
		 */
		
		SeatHold hold2 = service.findAndHoldSeats(2, "sample@gmail.com");
		
		/**
		 * reserve the above hold 2
		 */
		service.reserveSeats(hold2.getId(), "sample@gmail.com");
		
		
		Thread.sleep(applicationConfig.getHoldIntervalTime()+2L);
		
		/**
		 * release and get count
		 */
		 service.numSeatsAvailable();
		 
		 /**
		  * hold 1
		  */
		 service.findAndHoldSeats(1, "sample@gmail.com");
		
		 /**
		  * hold 2
		  */
		service.findAndHoldSeats(2, "sample@gmail.com");
		
		/**
		 * hold 2
		 */
		 service.findAndHoldSeats(2, "sample@gmail.com");
		
		System.out.println("confirmation -> "+confirmation);
		
	}

}
