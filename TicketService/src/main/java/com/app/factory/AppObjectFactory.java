package com.app.factory;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.app.model.AtomicSeatReference;
import com.app.model.Customer;
import com.app.model.Seat;
import com.app.model.SeatHold;
import com.app.model.Transaction;

/**
 * 
 * @author ramkumarsundarajan
 *
 */
public class AppObjectFactory {
	
	private static final AtomicInteger customerIdCounter = new AtomicInteger(0);
	
	private static final AtomicInteger seatHoldIdCounter = new AtomicInteger(0);
	
	private static final AtomicInteger transactionCounter = new AtomicInteger(0);
	
	
	/**
	 * create SeatHold with given email and list of seats
	 * @param email
	 * @param seatList
	 * @return
	 */
	@Deprecated
	public static SeatHold createSeatHold(String email,List<Seat> seatList){
		SeatHold seatHold = new SeatHold();
		seatHold.setId(seatHoldIdCounter.incrementAndGet());
		seatHold.setCustomer(createCustomer(email));
		//seatHold.setSeats(seatList);
		return seatHold;
		
	}
	
	/**
	 * 
	 * @param email
	 * @param seatList
	 * @return
	 */
	public static SeatHold createAtomicSeatHold(String email,List<AtomicSeatReference> seatList,boolean isError){
		SeatHold seatHold = new SeatHold();
		seatHold.setId(seatHoldIdCounter.incrementAndGet());
		seatHold.setCustomer(createCustomer(email));
		seatHold.setAtomicSeats(seatList);
		seatHold.setTransaction(createTransaction(isError));
		return seatHold;
		
	}
	
	/**
	 * 
	 * @param email
	 * @return
	 */
	private static Customer createCustomer(String email){
		
		Customer customer = new Customer();
		customer.setId(customerIdCounter.incrementAndGet());
		customer.setEmail(email);
		return customer;
	}
	
	/**
	 * 
	 * @return
	 */
	private static Transaction createTransaction(boolean isError){
		
		Transaction transaction = new Transaction();
		transaction.setCode(isError?"01":"00");
		transaction.setDescription(isError?"Transaction Failed.":"Transaction Successful");
		transaction.setError(isError);
		return transaction;
	}
	
	/**
	 * to avoid instantiation
	 */
	private AppObjectFactory(){
		
	}
	
	

}
