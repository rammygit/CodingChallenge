package com.app.manager;

import com.app.data.DataHolder;
import com.app.model.Seat.SeatStatus;
import com.app.model.SeatHold;

/**
 * TODO: need to throw validationException on email validity fail.
 * @author ramkumarsundarajan
 *
 */
public class TicketServiceManager implements IServiceManager{
	
	private final DataHolder dataHolder = DataHolder.getInstance();
	
	
	@Override
	public int getSeatCount(SeatStatus status) {
		return dataHolder.getAvailableSeatCount();
	}

	@Override
	public SeatHold holdSeats(int seatCount, String email) {
		
		return dataHolder.holdSeat(seatCount, email);
	}

	@Override
	public String reserve(int holdId,String email) {
		
		return dataHolder.reserveSeats(holdId, email);
	}

}
