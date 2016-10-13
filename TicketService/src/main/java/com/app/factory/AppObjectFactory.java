package com.app.factory;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.app.model.AtomicSeatReference;
import com.app.model.Customer;
import com.app.model.Seat;
import com.app.model.SeatHold;

/**
 * 
 * @author ramkumarsundarajan
 *
 */
public class AppObjectFactory {
	
	private static final AtomicInteger customerIdCounter = new AtomicInteger(0);
	
	private static final AtomicInteger seatHoldIdCounter = new AtomicInteger(0);
	
	
	/**
	 * create SeatHold with given email and list of seats
	 * @param email
	 * @param seatList
	 * @return
	 */
	public static SeatHold createSeatHold(String email,List<Seat> seatList){
		SeatHold seatHold = new SeatHold();
		seatHold.setId(seatHoldIdCounter.incrementAndGet());
		seatHold.setCustomer(createCustomer(email));
		seatHold.setSeats(seatList);
		return seatHold;
		
	}
	
	/**
	 * 
	 * @param email
	 * @param seatList
	 * @return
	 */
	public static SeatHold createAtomicSeatHold(String email,List<AtomicSeatReference> seatList){
		SeatHold seatHold = new SeatHold();
		seatHold.setId(seatHoldIdCounter.incrementAndGet());
		seatHold.setCustomer(createCustomer(email));
		seatHold.setAtomicSeats(seatList);
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
	 * to avoid instantiation
	 */
	private AppObjectFactory(){
		
	}
	
	

}
