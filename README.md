# CodingChallenge

Assumptions 
 * There is no DB availability. 
 * Need Java 1.8 and Maven 3. 
 * total no of seats are loaded in the constructor and you will be passing value as int.
 * added to skip list for lock-free thread safety.
 * User will be entering options when the application starts. options like 1 , 2, 3 0 to exit
 * Seats will be chosen in the order of availability.
 * there is no priority in chosing the seats. no option of choosing seats manually. seats are allocated and hold.
 * status of a seat will be one of the these - > Available , Hold , Reserved.
 * Did not use the Logger framework like log4j or logback. it is printing to console. 
 * Hold time interval -> provided in seconds format. 
 
How to run:
 
 * mvn clean compile install. ( install will trigger the execution of the MyApplication.java )
 * added main program execution by  -> org.codehaus.mojo -   exec-maven-plugin
 
