package com.app.manager;

import com.app.model.Seat.SeatStatus;
import com.app.model.SeatHold;

/**
 * service manager interface
 * cater to mutiple service
 * @author ramkumarsundarajan
 *
 */
public interface IServiceManager {
	
	

	
	/**
	 * get count of the seats based on the status.
	 * @param status
	 * @return
	 */
	public int getSeatCount(SeatStatus status);
	
	
	/**
	 * hold seats temporarily for booking with payment / reserve status.
	 * @param seatCount
	 * @param email
	 * @return
	 */
	public SeatHold holdSeats(int seatCount,String email);
		
		
	/**
	 * reserve seats.
	 * @param holdId
	 * @return
	 */
	public String reserve(int holdId,String email);
	

}
