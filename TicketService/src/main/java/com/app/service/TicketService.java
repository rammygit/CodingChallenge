package com.app.service;

import com.app.manager.IServiceManager;
import com.app.model.Seat.SeatStatus;
import com.app.model.SeatHold;

/**
 * 
 * @author ramkumarsundarajan
 *
 */
public class TicketService implements ITicketService {
	
	private final IServiceManager serviceManager;
	
	public TicketService(IServiceManager serviceManager) {
		// TODO Auto-generated constructor stub
		this.serviceManager = serviceManager;
		
	}

	@Override
	public int numSeatsAvailable() {
		
		return serviceManager.getSeatCount(SeatStatus.AVAILABLE);
	}

	@Override
	public SeatHold findAndHoldSeats(int numSeats, String customerEmail) {
		return serviceManager.holdSeats(numSeats, customerEmail);
	}

	@Override
	public String reserveSeats(int seatHldId, String customerEmail) {
		return serviceManager.reserve(seatHldId, customerEmail);
	}

}
