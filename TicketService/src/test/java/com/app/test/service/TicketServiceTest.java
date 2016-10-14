package com.app.test.service;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.app.data.ApplicationConfig;
import com.app.manager.TicketServiceManager;
import com.app.model.SeatHold;
import com.app.service.ITicketService;
import com.app.service.TicketService;
import com.app.util.exception.ConsoleExceptionHandler;

/**
 * 
 * @author ramkumarsundarajan
 *
 */
public class TicketServiceTest {
	
	private static ITicketService ticketService;
	
	private static ApplicationConfig applicationConfig;
	
	private final static int totalSeats = 100;
	
	private final static long holdTimerInterval = 10L;
	
	@BeforeClass
	public static void initService(){
		System.out.println("initialize service ..... ");
		
		applicationConfig = new ApplicationConfig(totalSeats,holdTimerInterval);
		
	}
	
	@Before
	public void beforeTest(){
		
		System.out.println("calling before test .... ");
		
		ticketService = new TicketService(
				new TicketServiceManager(
						new ConsoleExceptionHandler(),applicationConfig));
	}
	
	@After
	public void afterTest(){
		ticketService = null;
	}
	
	@Test
	public void testTotalSeats(){
		
		int seatCount = ticketService.numSeatsAvailable();
		System.out.println(seatCount);
		assertEquals(applicationConfig.getTotalSeats(), seatCount);
		
	}
	
	@Test
	public void testHoldSeats(){
		
		SeatHold seatHold = ticketService.findAndHoldSeats(applicationConfig.getTotalSeats() -10, "r@gmail.com");
		assertNotNull(seatHold);
		assertNotNull(seatHold.getAtomicSeats());
		assertEquals(totalSeats-10,seatHold.getAtomicSeats().size());
		
	}
	
	@Test
	public void testReserveSuccess(){
		SeatHold seatHold = ticketService.findAndHoldSeats(applicationConfig.getTotalSeats()-10, "r@gmail.com");
		String confirmationCode = ticketService.reserveSeats(seatHold.getId(), "r@gmail.com");
		assertNotNull(confirmationCode);
	}
	
	@Test
	public void testReserveError(){
		String confirmationCode = ticketService.reserveSeats(0, "r@gmail.com");
		assertNotNull(confirmationCode);
		assertEquals(ApplicationConfig.errorCode,confirmationCode);
	}
	
	@Test
	public void testHoldSeatFailure(){
		SeatHold seatHold = ticketService.findAndHoldSeats(totalSeats+1, "r@gmail.com");
		assertNotNull(seatHold);
		assertNotNull(seatHold.getTransaction());
		assertNotNull(seatHold.getTransaction().isError());
		assertTrue(seatHold.getTransaction().isError());
	}
	
	

}
