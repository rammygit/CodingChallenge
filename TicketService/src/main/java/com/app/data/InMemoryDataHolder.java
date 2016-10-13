package com.app.data;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;

import com.app.factory.AppObjectFactory;
import com.app.model.AtomicSeatReference;
import com.app.model.Seat;
import com.app.model.Seat.SeatStatus;
import com.app.model.SeatHold;
import com.app.util.ObjectUtil;

/**
 * 
 * @author ramkumarsundarajan
 *
 */
public class InMemoryDataHolder {
	
	private final NavigableSet<AtomicSeatReference> seats = new ConcurrentSkipListSet<AtomicSeatReference>();
	
	private final Map<Integer,SeatHold> seatHoldMap = new ConcurrentHashMap<Integer,SeatHold>();
	
	private static final InMemoryDataHolder dataHolder = new InMemoryDataHolder();
	
	
	/**
	 * initialize the data
	 */
	private InMemoryDataHolder(){
		
		for(int i=1;i<=ApplicationConfig.getTotalSeats();i++){
			
			seats.add(new AtomicSeatReference(new Seat(i, SeatStatus.AVAILABLE)));
		}
	
	}

	/**
	 * get instance of DataHolder.
	 * @return
	 */
	public static InMemoryDataHolder getInstance(){
		
		return dataHolder;
	}
	
	/**
	 * return the current available seat count
	 * @return
	 */
	public int getAvailableSeatCount(){
		/**
		 * to release the seats on getting the latest.
		 * 
		 */
		releaseSeats();
		try{
			//seatReadLock.lock();
			return Math.toIntExact(seats.stream().filter(p->p.get().getStatus().equals(SeatStatus.AVAILABLE)).count());
		}finally{
			//seatReadLock.unlock();
		}
	}
	
	
	/**
	 * block seats into the HOLD status. 
	 * 
	 * @param seatCount
	 * @param email
	 * @return
	 */
	public SeatHold holdSeat(int seatCount,String email){
		
		
		
		long timeInMS = Calendar.getInstance().getTimeInMillis();
		
		List<AtomicSeatReference> seatList = new ArrayList<AtomicSeatReference>();
		
		try{
			
			for(AtomicSeatReference atomicSeatReference : seats
					.parallelStream().
					filter(p->p.get().getStatus().equals(SeatStatus.AVAILABLE))
					.sorted((f1, f2) -> {
						//System.out.println(f1.getId()+" -> "+f2.getId());
						if(Math.abs(Math.subtractExact(f1.get().getId(), f2.get().getId())) > 1) return 1;
						else if(Math.abs(Math.subtractExact(f1.get().getId(), f2.get().getId())) == 1) return 0;
						else return -1;
						//return Integer.signum(f1.getId() - f2.getId());
					})
					.limit(seatCount)
					.collect(Collectors.toList())){
				atomicSeatReference.get().setHoldStartTime(timeInMS);
				atomicSeatReference.get().setStatus(SeatStatus.HOLD);
				seatList.add(atomicSeatReference);
			}
		}finally {
			//seatWriteLock.unlock();
		}
		
		SeatHold seatHold = AppObjectFactory.createAtomicSeatHold(email, seatList);
		/* concurrent hashmap will lock only the write operation/ entry */
		seatHoldMap.put(seatHold.getId(), seatHold);
		
		return seatHold;
	}
	
	/**
	 * reserve seats for the given hold id and email.
	 * @param seatHoldId
	 * @param email
	 * @return
	 */
	public String reserveSeats(int seatHoldId,String email){
		
		SeatHold seatHold = seatHoldMap.get(seatHoldId);
		
		if(ObjectUtil.isNotNull(seatHold) && ObjectUtil.validCollection(seatHold.getSeats())){
			for(Seat seat:seatHold.getSeats()){
				//resetting the hold start time back.
				seat.setHoldStartTime(0L);
				seat.setStatus(SeatStatus.RESERVED);
			}
		}
		
		
		/**
		 *  
		 */
		return UUID.randomUUID().toString();
	}
	
	
	/**
	 * to release the seat from hold status after the hold expires.
	 * time configured in the application config.
	 */
	private void releaseSeats(){
		
		try{
			//seatWriteLock.lock();
			for(AtomicSeatReference atomicSeatReference:seats
					.parallelStream()
					.filter(p->p.get().getHoldStartTime() >= ApplicationConfig.holdIntervalTime)
					.collect(Collectors.toList())){
				
				atomicSeatReference.get().setStatus(SeatStatus.AVAILABLE);
				atomicSeatReference.get().setHoldStartTime(0L);
			}
			
		}finally {
			//seatWriteLock.unlock();
		}
	}
	
	
	/**
	 * initializes the data in here from the application config
	 */
	
	
	public  void printData(){
		System.out.println("<----------- print -------------- >");
		for(AtomicSeatReference atomicSeatReference:seats)
			System.out.println("seat -> "+atomicSeatReference.get().getId() +" status -> "+atomicSeatReference.get().getStatus().toString() 
					+"holdStartTime -> "+atomicSeatReference.get().getHoldStartTime());
	}
	
	



}
