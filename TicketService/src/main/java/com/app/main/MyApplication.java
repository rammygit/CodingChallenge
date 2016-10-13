package com.app.main;

import com.app.data.ApplicationConfig;
import com.app.manager.TicketServiceManager;
import com.app.model.SeatHold;
import com.app.service.ITicketService;
import com.app.service.TicketService;

/**
 * main application 
 * 
 * @author ramkumarsundarajan
 *
 */
public class MyApplication {

	
	public static void main(String[] args) throws Exception {

		ApplicationConfig.setTotalSeats(10);
		
		ITicketService service = new TicketService(new TicketServiceManager());
		
		int totalAvailableSeats = service.numSeatsAvailable();
		
		
		
		System.out.println("total available seats -> "+totalAvailableSeats);
		
		SeatHold hold = service.findAndHoldSeats(2, "sample@gmail.com");
		
		String confirmation = service.reserveSeats(hold.getId(), "sample@gmail.com");
		
		SeatHold hold1 = service.findAndHoldSeats(2, "sample@gmail.com");
		
		
		
		SeatHold hold2 = service.findAndHoldSeats(2, "sample@gmail.com");
		
		String confirmation1 = service.reserveSeats(hold2.getId(), "sample@gmail.com");
		
		
		Thread.sleep(ApplicationConfig.holdIntervalTime+2L);
		
		
		int totalAvailableSeats2 = service.numSeatsAvailable();
		SeatHold hold3 = service.findAndHoldSeats(1, "sample@gmail.com");
		
		SeatHold hold4 = service.findAndHoldSeats(2, "sample@gmail.com");
		
		System.out.println("hold id -> "+hold.getId());
		System.out.println("hold seat count -> "+hold.getSeats().size());
		
		
		
		System.out.println("confirmation -> "+confirmation);
		
	}

}
