package com.app.model;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 
 * @author ramkumarsundarajan
 *
 */
public final class AtomicSeatReference extends AtomicReference<Seat> implements Comparable<AtomicSeatReference>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * seat reference will be returned.
	 * @return
	 */
	
	public AtomicSeatReference(){
		super();
	}
	
	public AtomicSeatReference(Seat seat){
		super(seat);
	}
	
	
	public Seat getReference(){
		return this.get();
	}
	
	public void setReference(Seat seat){
		 this.set(seat);
	}

	@Override
	public int compareTo(AtomicSeatReference o) {
		return Math.subtractExact(this.get().getId(), o.get().getId());
	}
	
}
