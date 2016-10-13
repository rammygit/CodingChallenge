package com.app.data;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

import com.app.factory.AppObjectFactory;
import com.app.model.AtomicSeatReference;
import com.app.model.Seat;
import com.app.model.Seat.SeatStatus;
import com.app.model.SeatHold;
import com.app.util.ObjectUtil;
import com.app.util.exception.DBException;

/**
 * InMemory DB kind of.  In place of DB. 
 * Assumption: there is no DB availability. 
 * total no of seats are loaded in the constructor and 
 * added to skip list for lock-free thread safety.
 * 
 * Reasoning of Data Structure choice : operations on seat reference are atomic by using AtomicReference. 
 * no need of AtomicStampedReference as there is no A-B-A problem, as reference are not changed or created new everytime.
 * value in the reference is change, so went with the AtomicReference.
 * 
 * this is a singleton class. thread-safe ensured by initializing during classload time and made final. 
 * did not want to double-check idiom.
 * 
 * did not implement separate background thread for releasing the seats from HOLD to AVAILABLE , 
 * instead releasing when calling the available seat count.
 * 
 * 
 * 
 * @author ramkumarsundarajan
 *
 */
public class InMemoryDataHolder {
	
	private final NavigableSet<AtomicSeatReference> seats = new ConcurrentSkipListSet<AtomicSeatReference>();
	
	private final Map<Integer,SeatHold> seatHoldMap = new ConcurrentHashMap<Integer,SeatHold>();
	
	private static final InMemoryDataHolder dataHolder = new InMemoryDataHolder();
	
	private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock(false);
	
	
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
		return Math.toIntExact(seats.parallelStream().filter(p->p.get().getStatus().equals(SeatStatus.AVAILABLE)).count());
	}
	
	
	/**
	 * block seats into the HOLD status. 
	 * 
	 * @param seatCount
	 * @param email
	 * @return
	 */
	public SeatHold holdSeat(int seatCount,String email) throws DBException{
		
		SeatHold seatHold = null;
		
		long timeInMS = Calendar.getInstance().getTimeInMillis();
		
		List<AtomicSeatReference> seatList = new ArrayList<AtomicSeatReference>();
		
		int totalAvaialbleSeats = getAvailableSeatCount();
		
		if(totalAvaialbleSeats == 0 || totalAvaialbleSeats < seatCount)
			throw new DBException("requested number of seats are not available !");
		
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
				
				seatHold = addToSeatHoldMap(email, seatList, false);
				

			}
		}catch(Exception ex){
			System.err.println(ObjectUtil.getStackStraceAsString(ex));
			
			seatHold = addToSeatHoldMap(email, seatList, false);
			
			throw new DBException("Cannot process the transaction.");
		}
		
		return seatHold;
	}
	
	
	
	/**
	 * reserve seats for the given hold id and email.
	 * @param seatHoldId
	 * @param email
	 * @return
	 */
	public String reserveSeats(int seatHoldId,String email) throws DBException{
		
		try{
			/**
			 * to synchronize the get and remove operation on concurrent hashmap as 
			 * it has to be externally synchronized when together.
			 */
			readWriteLock.writeLock().lock();
			SeatHold seatHold = seatHoldMap.get(seatHoldId);
			
			if(ObjectUtil.isNotNull(seatHold) && ObjectUtil.validCollection(seatHold.getAtomicSeats())){
				for(AtomicSeatReference atomicSeatReference:seatHold.getAtomicSeats()){
					//resetting the hold start time back.
					atomicSeatReference.get().setHoldStartTime(0L);
					atomicSeatReference.get().setStatus(SeatStatus.RESERVED);
				}
			} else {
				throw new DBException("requested hold id is not valid!!");
			}
			
			seatHoldMap.remove(seatHoldId);
			
			return UUID.randomUUID().toString();
		}catch(Exception ex){
			//log the error.
			System.out.println(ObjectUtil.getStackStraceAsString(ex));
			throw new DBException(ex.getMessage());
		}finally{
			readWriteLock.writeLock().unlock();
		}
		
	}
	
	/**
	 * add to the seathold map only if this is not called on Error.
	 * @param email
	 * @param seatList
	 * @param isError
	 * @return
	 */
	private SeatHold addToSeatHoldMap(String email,List<AtomicSeatReference> seatList,boolean isError){
		SeatHold seatHold = AppObjectFactory.createAtomicSeatHold(email, seatList,isError);
		/* concurrent hashmap will lock only the write operation/ entry */
		/**
		 * add to map only if there is no error and transaction can be processed successfully.
		 */
		if(!isError) seatHoldMap.put(seatHold.getId(), seatHold);
		return seatHold;
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
