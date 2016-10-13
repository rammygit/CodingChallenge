package com.app.manager;

import com.app.data.DataHolder;
import com.app.data.InMemoryDataHolder;
import com.app.model.Seat.SeatStatus;
import com.app.model.SeatHold;

/**
 * TODO: need to throw validationException on email validity fail.
 * @author ramkumarsundarajan
 *
 */
public class TicketServiceManager implements IServiceManager{
	
	private final DataHolder dataHolder = DataHolder.getInstance();
	
	private final InMemoryDataHolder inMemoryDataHolder = InMemoryDataHolder.getInstance();
	
	
	@Override
	public int getSeatCount(SeatStatus status) {
		
		int count = inMemoryDataHolder.getAvailableSeatCount();
		inMemoryDataHolder.printData();
		return count;
	}

	@Override
	public SeatHold holdSeats(int seatCount, String email) {
		
		SeatHold seatHold =  inMemoryDataHolder.holdSeat(seatCount, email);
		inMemoryDataHolder.printData();
		return seatHold;
	}

	@Override
	public String reserve(int holdId,String email) {
		
		String code =  inMemoryDataHolder.reserveSeats(holdId, email);
		inMemoryDataHolder.printData();
		return code;
	}

}
