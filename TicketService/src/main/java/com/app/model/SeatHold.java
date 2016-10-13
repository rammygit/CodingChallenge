package com.app.model;

import java.io.Serializable;
import java.util.List;

/**
 * seat hold object.
 * 
 * @author ramkumarsundarajan
 *
 */
public class SeatHold implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int id;
	
	private long timeStamp;
	
	private Customer customer;
	
	//private List<Seat> seats;
	
	private List<AtomicSeatReference> atomicSeats;
	
	private Transaction transaction;

	
	
	public List<AtomicSeatReference> getAtomicSeats() {
		return atomicSeats;
	}

	public void setAtomicSeats(List<AtomicSeatReference> atomicSeats) {
		this.atomicSeats = atomicSeats;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Transaction getTransaction() {
		return transaction;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}


}
