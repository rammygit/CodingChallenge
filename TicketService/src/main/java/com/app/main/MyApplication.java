package com.app.main;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import com.app.data.ApplicationConfig;
import com.app.manager.TicketServiceManager;
import com.app.model.SeatHold;
import com.app.service.ITicketService;
import com.app.service.TicketService;
import com.app.util.ObjectUtil;
import com.app.util.exception.ConsoleExceptionHandler;

/**
 * main application 
 * 
 * @author ramkumarsundarajan
 *
 */
public class MyApplication {


	/**
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		Scanner scanner = null;
		try{

			scanner = new Scanner(System.in);

			System.out.println("Enter total no of seats -> ");

			int totalSeats = scanner.nextInt();

			System.out.println("Enter the hold time out interval (in seconds)  -> ");

			long timeoutinMillis = TimeUnit.SECONDS.toMillis(scanner.nextInt());
			
			ApplicationConfig applicationConfig = new ApplicationConfig(totalSeats,timeoutinMillis);
			
			System.out.println("hold time interval -> "+applicationConfig.getHoldIntervalTime());

			


			System.out.println("do you want to do a dry run -> (Y or N)");
			
			String dryRun = scanner.next();

			if(dryRun.equalsIgnoreCase("Y")) 
				dryRun(applicationConfig);
			else 
				System.out.println("dry run not elected!!");
			
			ITicketService  ticketService = new TicketService(
					new TicketServiceManager(
							new ConsoleExceptionHandler(),applicationConfig));
				while (true) {
					
					// read line from the user input
					System.out.println(
							"Please Select an operation " + ""
									+ "\nEnter 1 -> find total available seats "
									+ "\nEnter 2 ->  Find and hold the available seats "
									+ "\nEnter 3 ->  reserve the seats "
									+ "\nEnter 0 -> exit " + "\nselect now ->");

					int num = scanner.nextInt();
					switch (num) {
					case 1:

						int seatsCount = ticketService.numSeatsAvailable();
						System.out.println("total available seats count -> "+seatsCount);
						
						break;
					case 2:
						System.out.println("enter number of seats to hold - ");
						int numSeats = scanner.nextInt();
						System.out.println("enter customer email - ");
						String customerEmail = scanner.next();
						SeatHold seatHold = ticketService.findAndHoldSeats(numSeats, customerEmail);
						System.out.println("booked id -> "+seatHold.getId());
						System.out.println(" total seats hold -> "+seatHold.getAtomicSeats().size());
						break;
					case 3:		
						System.out.println("enter your booking id -> ");
						int holdId = scanner.nextInt();
						System.out.println("enter the customer email -> ");
						String email = scanner.next();
						String confirmationCode = ticketService.reserveSeats(holdId, email);
						System.out.println("your confirmation code -> "+confirmationCode);
						break;
					case 0:
						System.out.println("exiting the application.");
						return;
					default:
						System.out.println("Invalid Input");
					}
				}


		}catch (Exception ex){

			System.err.println(ObjectUtil.getStackStraceAsString(ex));

		}finally{
			if(scanner!=null ) scanner.close();
		}


	}


	/**
	 * 
	 * @param applicationConfig
	 * @param service
	 * @throws InterruptedException
	 */
	private static void dryRun(ApplicationConfig applicationConfig)
			throws InterruptedException {
		
		ITicketService service = null;
		
		try{
			service = new TicketService(
					new TicketServiceManager(
							new ConsoleExceptionHandler(),applicationConfig));

			int totalAvailableSeats = service.numSeatsAvailable();

			System.out.println("total available seats -> "+totalAvailableSeats);

			/**
			 * hold 2
			 */
			SeatHold hold = service.findAndHoldSeats(2, "sample@gmail.com");

			System.out.println("hold 2 seats done");
			/**
			 * reserve 2
			 */
			String confirmation = service.reserveSeats(hold.getId(), "sample@gmail.com");
			System.out.println("reserve 2 seats done");
			/**
			 * hold 2
			 */
			service.findAndHoldSeats(2, "sample@gmail.com");
			System.out.println("hold 2 seats done");
			/**
			 * hold 2
			 */

			SeatHold hold2 = service.findAndHoldSeats(2, "sample@gmail.com");
			System.out.println("hold 2 seats done");
			/**
			 * reserve the above hold 2
			 */
			service.reserveSeats(hold2.getId(), "sample@gmail.com");
			System.out.println("reserve 2 seats done");

			//Thread.sleep(applicationConfig.getHoldIntervalTime()+2L);
			
			/**
			 * release and get count
			 */
			
			System.out.println("total seats available -> "+service.numSeatsAvailable());
			/**
			 * hold 1
			 */
			service.findAndHoldSeats(1, "sample@gmail.com");
			System.out.println("hold 1 seats done");
			/**
			 * hold 2
			 */
			service.findAndHoldSeats(2, "sample@gmail.com");
			System.out.println("hold 2 seats done");
			/**
			 * hold 2
			 */
			service.findAndHoldSeats(2, "sample@gmail.com");
			System.out.println("hold 2 seats done");
			
			System.out.println("total seats available -> "+service.numSeatsAvailable());

			System.out.println("confirmation -> "+confirmation);
		}finally{
			
			service = null;
		}

		
	}

}
