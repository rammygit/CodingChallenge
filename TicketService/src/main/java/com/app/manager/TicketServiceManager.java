package com.app.manager;

import com.app.data.ApplicationConfig;
import com.app.data.InMemoryDataHolder;
import com.app.factory.AppObjectFactory;
import com.app.model.Seat.SeatStatus;
import com.app.model.SeatHold;
import com.app.util.ObjectUtil;
import com.app.util.exception.DBException;
import com.app.util.exception.ExceptionHandler;

/**
 * TODO: need to throw validationException on email validity fail.
 * @author ramkumarsundarajan
 *
 */
public class TicketServiceManager implements IServiceManager{
	
	private final InMemoryDataHolder inMemoryDataHolder = InMemoryDataHolder.getInstance();
	
	private ExceptionHandler exceptionHandler;
	
	public TicketServiceManager(ExceptionHandler exceptionHandler) {
		this.exceptionHandler = exceptionHandler;
	}
	
	
	@Override
	public int getSeatCount(SeatStatus status) {
		
		int count = 0;
		
		try {
			count = inMemoryDataHolder.getAvailableSeatCount();
		} catch (DBException e) {
			exceptionHandler.handle(e, e.getMessage());
		}
		//inMemoryDataHolder.printData();
		return count;
	}

	@Override
	public SeatHold holdSeats(int seatCount, String email) {
		
		SeatHold seatHold = null;
		try {
			seatHold = inMemoryDataHolder.holdSeat(seatCount, email);
		} catch (DBException e) {
			exceptionHandler.handle(e, e.getMessage());
		} catch (Exception ex){
			/**
			 * need to log this for error parsing.
			 */
			seatHold = AppObjectFactory.createAtomicSeatHold(email, null, true);
			exceptionHandler.handle(ex, ex.getMessage());
		}
		//inMemoryDataHolder.printData();
		return seatHold;
	}

	@Override
	public String reserve(int holdId,String email) {
		String code = null;
		try{
			code =  inMemoryDataHolder.reserveSeats(holdId, email);
		}catch(Exception e){
			/**/
			code = ApplicationConfig.errorCode;
			exceptionHandler.handle(e, e.getMessage());
		}
		
		//inMemoryDataHolder.printData();
		return code;
	}

}
