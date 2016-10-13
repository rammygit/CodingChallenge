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
		
		int count =  dataHolder.getAvailableSeatCount();
		dataHolder.printData();
		return count;
	}

	@Override
	public SeatHold holdSeats(int seatCount, String email) {
		
		SeatHold seatHold =  dataHolder.holdSeat(seatCount, email);
		dataHolder.printData();
		return seatHold;
	}

	@Override
	public String reserve(int holdId,String email) {
		
		String code =  dataHolder.reserveSeats(holdId, email);
		
		dataHolder.printData();
		return code;
	}

}
