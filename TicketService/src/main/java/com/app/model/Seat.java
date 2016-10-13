package com.app.model;

import java.util.Comparator;

public class Seat implements Comparable<Seat>{
	
	public enum SeatStatus {
		AVAILABLE,HOLD,RESERVED;
	}
	
	private Integer id;
	
	private SeatStatus status;
	
	private long holdStartTime;
	
	public Seat(Integer id,SeatStatus status){
		
		this.id = id;
		this.status = status;
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public SeatStatus getStatus() {
		return status;
	}

	public void setStatus(SeatStatus status) {
		this.status = status;
	}

	public long getHoldStartTime() {
		return holdStartTime;
	}

	public void setHoldStartTime(long holdStartTime) {
		this.holdStartTime = holdStartTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (holdStartTime ^ (holdStartTime >>> 32));
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Seat other = (Seat) obj;
		if (holdStartTime != other.holdStartTime)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (status != other.status)
			return false;
		return true;
	}
	
	/**
	 * 
	 * @return
	 */
	public static Comparator<Seat> getSeatComparator(){
		
		return Comparator.comparing(Seat::getStatus)
				.thenComparing(Seat::getId);
	}
	
	
	

	@Override
	public int compareTo(Seat o) {
		// TODO Auto-generated method stub
		return Math.subtractExact(this.getId(), o.getId());
	}
	

}
