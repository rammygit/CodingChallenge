package com.app.service;

import com.app.model.SeatHold;

/**
 * 
 * @author ramkumarsundarajan
 *
 */
public interface ITicketService {
	
	/**
	 * 
	 * @return
	 */
	int numSeatsAvailable();
	
	/**
	 * 
	 * @param numSeats
	 * @param customerEmail
	 * @return
	 */
	SeatHold findAndHoldSeats(int numSeats,String customerEmail);
	
	/**
	 * 
	 * @param seatHldId
	 * @param customerEmail
	 * @return
	 */
	String reserveSeats(int seatHldId,String customerEmail);

}
