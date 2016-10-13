package com.app.data;

/**
 * 
 * @author ramkumarsundarajan
 *
 */
public class DataHolder {
//	
//	
//	private final NavigableSet<Seat> seats = new ConcurrentSkipListSet<Seat>(Seat.getSeatComparator());
//	
//	private final Map<Integer,SeatHold> seatHoldMap = new ConcurrentHashMap<Integer,SeatHold>();
//	
//	//private final Random randomKey = new SecureRandom();
//	
//	private static final DataHolder dataHolder = new DataHolder();
//	
//	/**
//	 * for CAS lock-free implementation.
//	 */
//	private final AtomicStampedReference<NavigableSet<Seat>> atomicSeats 
//						= new AtomicStampedReference<NavigableSet<Seat>>(new ConcurrentSkipListSet<Seat>(Seat.getSeatComparator()),0);
//	
//	
//	
//	/**
//	 * get instance of DataHolder.
//	 * @return
//	 */
//	public static DataHolder getInstance(){
//		
//		return dataHolder;
//	}
//	
//	/**
//	 * return the current available seat count
//	 * @return
//	 */
//	public int getAvailableSeatCount(){
//		/**
//		 * to release the seats on getting the latest.
//		 * 
//		 */
//		releaseSeats();
//		try{
//			//seatReadLock.lock();
//			return Math.toIntExact(atomicSeats.getReference().stream().filter(p->p.getStatus().equals(SeatStatus.AVAILABLE)).count());
//		}finally{
//			//seatReadLock.unlock();
//		}
//	}
//	
//	
//	/**
//	 * block seats into the HOLD status. 
//	 * 
//	 * @param seatCount
//	 * @param email
//	 * @return
//	 */
//	public SeatHold holdSeat(int seatCount,String email){
//		
//		
//		
//		long timeInMS = Calendar.getInstance().getTimeInMillis();
//		
//		List<Seat> seatList = new ArrayList<>();
//		
//		try{
//			
//			for(Seat seat : atomicSeats.getReference()
//					.parallelStream().
//					filter(p->p.getStatus().equals(SeatStatus.AVAILABLE))
//					.sorted((f1, f2) -> {
//						//System.out.println(f1.getId()+" -> "+f2.getId());
//						if(Math.abs(Math.subtractExact(f1.getId(), f2.getId())) > 1) return 1;
//						else if(Math.abs(Math.subtractExact(f1.getId(), f2.getId())) == 1) return 0;
//						else return -1;
//						//return Integer.signum(f1.getId() - f2.getId());
//					})
//					.limit(seatCount)
//					.collect(Collectors.toList())){
//				seat.setHoldStartTime(timeInMS);
//				seat.setStatus(SeatStatus.HOLD);
//				seatList.add(seat);
//			}
//		}finally {
//			//seatWriteLock.unlock();
//		}
//		
//		SeatHold seatHold = AppObjectFactory.createSeatHold(email, seatList);
//		/* concurrent hashmap will lock only the write operation/ entry */
//		seatHoldMap.put(seatHold.getId(), seatHold);
//		
//		return seatHold;
//	}
//	
//	/**
//	 * reserve seats for the given hold id and email.
//	 * @param seatHoldId
//	 * @param email
//	 * @return
//	 */
//	public String reserveSeats(int seatHoldId,String email){
//		
//		SeatHold seatHold = seatHoldMap.get(seatHoldId);
//		
//		if(ObjectUtil.isNotNull(seatHold) && ObjectUtil.validCollection(seatHold.getSeats())){
//			for(Seat seat:seatHold.getSeats()){
//				//resetting the hold start time back to 0.
//				seat.setHoldStartTime(0L);
//				seat.setStatus(SeatStatus.RESERVED);
//			}
//		}
//		
//		
//		/**
//		 *  
//		 */
//		return UUID.randomUUID().toString();
//	}
//	
//	
//	/**
//	 * to release the seat from hold status after the hold expires.
//	 * time configured in the application config.
//	 */
//	private void releaseSeats(){
//		
//		try{
//			//seatWriteLock.lock();
//			for(Seat seat:atomicSeats.getReference()
//					.parallelStream()
//					.filter(p->p.getHoldStartTime() >= ApplicationConfig.holdIntervalTime)
//					.collect(Collectors.toList())){
//				
//				seat.setStatus(SeatStatus.AVAILABLE);
//				seat.setHoldStartTime(0L);
//			}
//			
//		}finally {
//			//seatWriteLock.unlock();
//		}
//	}
//	
//	
//	/**
//	 * initializes the data in here from the application config
//	 */
//	private DataHolder(){
//		for(int i=1;i<=ApplicationConfig.getTotalSeats();i++)
//			atomicSeats.getReference().add(new Seat(i, SeatStatus.AVAILABLE));
//	}
//	
//	
//	public  void printData(){
//		System.out.println("<----------- print -------------- >");
//		for(Seat seat:atomicSeats.getReference())
//			System.out.println("seat -> "+seat.getId() +" status -> "+seat.getStatus().toString() +"holdStartTime -> "+seat.getHoldStartTime());
//	}
//	
//	
//
}
