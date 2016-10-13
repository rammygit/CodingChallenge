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
public class Application {

	
	public static void main(String[] args) {

		ApplicationConfig.setTotalSeats(100);
		
		ITicketService service = new TicketService(new TicketServiceManager());
		
		int totalAvailableSeats = service.numSeatsAvailable();
		
		System.out.println("total available seats -> "+totalAvailableSeats);
		
		SeatHold hold = service.findAndHoldSeats(10, "sample@gmail.com");
		
		System.out.println("hold id -> "+hold.getId());
		System.out.println("hold seat count -> "+hold.getSeats().size());
		
		String confirmation = service.reserveSeats(hold.getId(), "sample@gmail.com");
		
		System.out.println("confirmation -> "+confirmation);
		
	}

}
