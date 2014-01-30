package io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/* Importera kundklassen */
import constructs.Customer;

/**
 * The class DataIO contains the functionality for reading from and writing to file, for the Gym-project.
 * 
 * @author evabl45
 *
 */
public class DataIO {

	/**
	 * Public (static) method for reading data from file.
	 * This is the entry point for reading data from the files, 
	 * this is the method that should be called from your main-method
	 * in another class. Note that it is a void-method, hence, your 
	 * classes, e.g. the customer class, needs to automatically keep track 
	 * of any instances hat are created, this should not be handled here. 
	 */
	public static void readData(){
		//Starting by reading the customers from file...
		readCustomers();
		//Next, read all the membership card details and gym visits...
		//readCards();
	}

	/**
	 * Private method that reads all customers from file.
	 * It reads a set of customer-strings from file and creates
	 * new customer instances from those. Customer class should 
	 * automatically keep track of all its instances, no list is
	 * kept here. The file, customers.txt, needs to be placed in the 
	 * project folder in the Eclipse workspace, or a proper file path
	 * needs to be added. If you modify the file syntax, you need to 
	 * modify this method as well.
	 */
	private static void readCustomers(){
		//Create a representation of the customer data file
		File customerfile = new File("customers.txt");
		try {
			// Create a file reader to read from the customer file
			FileReader fr = new FileReader(customerfile);
			// To be able to read one line at a time, wrap it in a BufferedReader
			BufferedReader br = new BufferedReader(fr);
			// Read the first line from the file
			String s=br.readLine();
			// As long as we have not reached the end of the file, do...
			int cid = 0;
			while (s != null){
				// Split the string read from the file by the separator ;

				String[] data = s.split(";");
				
				// If it has 4 parts, it is a correct representation of a customer (error-checking)
				if (data.length == 4){
					// Below we make the assumption that the data is always correctly entered in the file (no error-checking)
					
					
					// We know the first part is the name
					String name = data[0];
					// We know the second part is the social security number (personnummer)

					//String pnr = data[1];
					// Personnummer lagras som long i databasen pga jämförelseoperationer, läser ut och lagrar det som long i java istället för string
					long pnr = Long.parseLong(data[1]);

					// We know the third part is the address
					String addr = data[2];
					// We know the fourth part is the phone number
					String phone = data[3];

					// Create a new customer instance based on this data!
					
					// This is where we create our customer instance based on the data
					new Customer(cid, pnr, name, addr, phone, null);

					// cid kommer tas från key index i databasen
					cid++;
					
				}
				// Read the next line from the file...
				s = br.readLine();
			}
			// Once we have reached the end of the file, close the input stream
			br.close();

			// Catch any exceptions that may occur during reading the file 	
		} catch (Exception e){
			
			// Add any additional error handling that you find necessary...
			e.printStackTrace();
			
		}
	}
	
	/**
	 * Private method that reads all membership cards and gym visits from file.
	 * It reads a card string from the file, then reads all the following 
	 * gym visit-strings (on the following lines) and creates new visit  
	 * instances from those and puts them in a list. Next, creates a new card
	 * instance from data in the card string and the visits list, and
	 * finally adds the card to the card list of the appropriate customer.
	 *  
	 */
	private static void readCards(){
		//Create a representation of the cards data file
		File cardfile = new File("cards.txt");
		try {
			// Create a file reader to read from the cards file
			FileReader fr = new FileReader(cardfile);
			// To be able to read one line at a time, wrap it in a BufferedReader
			BufferedReader br = new BufferedReader(fr);
			// Read the first line from the file
			String s=br.readLine();
			// As long as we have not reached the end of the file... do another lap...
			while (s != null){
				// Split the string read from the file by the separator ;
				String[] data = s.split(";");
				// If the string has 5 or 7 parts, and the first part says "Coupons" or "Period", the line represents a card
				if ((data.length == 5 || data.length == 7) && (data[0].equalsIgnoreCase("Coupons") || data[0].equalsIgnoreCase("Period"))){
					// We know that the first part is the type of the card
					String type = data[0];
					// We know that the second part is the card number
					int cardno = Integer.parseInt(data[1]);
					// We know that the third part is the social security no (personnummer) of the card owner
					String pnr = data[2];
					// We know that the fourth part is the price of the card
					double price = Double.parseDouble(data[3]);

					// We know that the fifth part is the expiration date of the card
					String datestring = data[4];
					// Create an instance of SimpleDateFormat that knows about this format, and which we will use to interpret the date string
					SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd");
					// Use the format to parse (read and interpret) the datestring, e.g. "2013-09-10", as a date instance
					Date date = dformat.parse(datestring);
					// Create a new calendar instance (Calendar is an abstract class, the instance will be a GregorianCalendar)
					Calendar expdate = Calendar.getInstance();
					// Use the date instance, to set the correct values in the new calendar, so that it represents our date
					expdate.setTime(date);
					
					
					//A list for collecting the gym visits that are read for this card 
					//TODO: Replace "Object" with your visit type (class)
					ArrayList<Object> visits = new ArrayList<Object>(); 
					
					//Now, read the next line that contains a visit for this card
					s = br.readLine();
					
					//As long as we have not reached the end of the file, and the line starts with a "V" (as in "visit") 
					//then this is still a visit to be added to the list
					while (s != null && s.startsWith("V;")){
						// Call a method for handling the string, creating a new visit, and adding it to the list
						addVisitToList(visits,s);
						// Read next line
						s = br.readLine();
					}
					
					// Once all visits of this card has been read from the file...
					// Create a new card instance (of the right type) based on this data and add to the customer
					
					// If the card was a "periods card" (periodkort) then create an instance of that type...
					if (type.equalsIgnoreCase("Period")){
						
						// TODO: This is where you add your code to create a periods card instance based on the data
						
						// Find the person who holds the card and add it to that person's list of cards
						// TODO: Add a call to the addCardToCustomer()-method passing the new card instnace as a parameter
						
					} else 
						// If the card was a "coupons ticket" (klippkort) then create an instance of that type...
						if (type.equalsIgnoreCase("Coupons") && data.length == 7){
							// Get the activity type the card is valid for, i.e. either "Gym", "Spinning" or "Aerobics"
							String activitytype = data[5];
							// Get the total number of coupons of the card
							int totalcoupons = Integer.parseInt(data[6]);
							
							// TODO: This is where you add your code to create a coupons card instance based on the data
							
							// Find the person who holds the card and add it to that person's list of cards
							// TODO: Add a call to the addCardToCustomer()-method passing the new card as a parameter

					}


				}
			}
			// Once we have reached the end of the file, close the input stream
			br.close();

			// Catch any exceptions that may occur during reading the file 	
		} catch (Exception e){
			// Add any additional error handling that you find necessary...
			e.printStackTrace();
		}
	}
	
	/**
	 * Method for creating a new visit based on data in a string, and adding it to the list of visits.
	 * Note that since we are passing a reference to the actual list, then we do not need to return
	 * the list, but rather we are modifying the list directly.
	 * 
	 * @param visits the list of visits to which to add the new one you create
	 * @param s the string that contains data about the visit to create
	 */
	//TODO: Replace "Object" in the list parameter with your transaction type (class)
	private static void addVisitToList(ArrayList<Object> visits, String s){
		// First split the string around the separator ;
		String[] visitdata = s.split(";");
		// If the string has 4 parts, and the first part is a V, then it is a correct visit string
		if (visitdata.length == 4 && visitdata[0].equalsIgnoreCase("V")){
			try {
				// We know that the second part of the string is the time and date of the visit 
				String visitdatetimestring = visitdata[1];
				// Create a SimpleDateFormat instance, that represents the format we know that the date and time string for visits has
				SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm");
				// Create a date instance by parsing (reading an interpreting) the string using the format we have just created
				Date datetime = dformat.parse(visitdatetimestring);
				// Create a new calendar instance (Calendar is an abstract class, the instance will be a GregorianCalendar)
				Calendar visitdatetime = Calendar.getInstance();
				// Set the values of the calendar according to the date instance that we just created above 
				visitdatetime.setTime(datetime);

				// We know that the third part is the type of activity for the visit
				String type = visitdata[2];
				// We know that the fourth part is the title of the specific activity the customer visited
				String activity = visitdata[3];

				// Create a new visit instance based on the data we got from the string, i.e. the visitdatetime, the type and activity
				// TODO: Add your code here to create a new visit instance

				// ...then add the new visit to the list
				// TODO: Add your code here to add the new visit to the list (the variable "visits")
			
			} catch (Exception e) {
				// Add code here if needed to handle any exceptions that may arise above...
				e.printStackTrace();
			}
		}

	}
	
	/**
	 * Method for finding the person who owns the card and adding the new card to his list of cards.
	 * 
	 * @param pnr the social security number (personnummer) of the customer to which the card belongs
	 * @param newcard the card that should be added to the person's list of cards
	 */
	// TODO: Replace "Object" in the parameter list with your card type (class)
	private static void addCardToCustomer(String pnr, Object newcard){
		/* For each customer in the list of all customers in the system (hint: use a for-each loop 
		 * that loops over the list of all customers that is managed by your customer class, if that 
		 * list is private you have to use a method to retrieve it), and check if the
		 * social security number of each customer in the list is the same as the pnr-argument.
		 * If they are the same, connect this card to that customer, e.g. by adding the new 
		 * card to a list that keeps track of all cards of that customer. 
		 * For instance, by calling a method on that customer instance, e.g. something
		 * like addCard(...), and pass the new card (the newcard-argument) as an argument 
		 * to that method. As soon as you have found the right customer, you can use "break" to 
		 * stop the loop.  
		 * 
		 * You do not need to solve the problem in exactly this way, this is just a hint
		 * towards ONE way of doing it! The important thing is the outcome, after this method has 
		 * been ran, the new card should be connected to the right customer!
		 */
		
		// TODO: add your code here...
	}

	/**
	 * Public (static) method for writing all data to file.
	 * This is the entry point for writing data to the files, i.e.
	 * this is the method that should be called from your main-method
	 * in another class when the system is about to shut down, in
	 * order to store all the data before exiting. 
	 *  
	 */
	public static void writeData(){
				//Create a representation of the customer data file
				File customerfile = new File("customers.txt");
				//Create a representation of the cards data file
				File cardfile = new File("cards.txt");
				try {
					// Create a FileWriter to write to the customer file
					FileWriter custfw = new FileWriter(customerfile);
					// To be able to write one line at a time, including a newline, wrap it in a PrintWriter
					PrintWriter custpr = new PrintWriter(custfw);
					
					// Create a FileWriter to write to the cards file
					FileWriter cardfw = new FileWriter(cardfile);
					// To be able to write one line at a time, including a newline, wrap it in a PrintWriter
					PrintWriter cardpr = new PrintWriter(cardfw);
					// Catch any exceptions that may occur during reading the file 	
					
					/*
					 *  Loop over all the customers in the system (e.g. using a for-each loop over the list 
					 *  of all customers that is managed by your customer class, if that 
					 *  list is private you have to use a method to retrieve it), and for each one, call both the method
					 *  writeCustomer() and writeCards(), to each one passing the current customer instance
					 *  and the appropriate PrintWriter instance (custpr to writeCustomer() and cardpr to writeCards()).
					 */
					
					// TODO: Add your code for looping through all the customers, and calling the two methods
					
					// Close the streams
					custpr.close();
					cardpr.close();
					
				} catch (Exception e){
					// Add error handling as necessary...
					e.printStackTrace();
				}
				
				
		
	}
	
	/* TODO: Create a private static void-method called writeCustomer() that takes two parameters, 
	 * one customer instance and one PrintWriter. The method should then retrieve the data about the
	 * customer from the instance, create a string in the appropriate format, and write it to the file
	 * using the PrintWriter. The method could look something like (note that you have to modify the code
	 * so that it uses your customer class instead of "MyCustomerClass" and similarly with the get-methods): 
	 */
		/*	private static void writeCustomer (MyCustomerClass customer, PrintWriter pr){
		
					// Get the name
					String name = customer.myGetNameMethod();
					// Get the social security number
					String pnr = customer.myGetSocialSecurityNoMethod();
					// Get the address
					String addr = customer.myGetAddressMethod();
					// Get the phone number
					String phone = customer.myGetPhoneNoMethod();
					// Create the combined string to write
					String customerstring = name + ";" + pnr + ";" + addr + ";" + phone;
					// Write it to the file
					pr.println(customerstring);
					
		}*/
	
	/* TODO: Create a private static void-method called writeCards() that takes two parameters, 
	 * one customer instance and one PrintWriter. The method should then retrieve the data about the
	 * cards from the instance, create strings in the appropriate format, and write them to the file
	 * using the PrintWriter. For each card, you also need to write all its visits to the file.
	 * We suggest that you use a separate method for that, e.g. one called writeVisits(...).
	 * 
	 * The method could look something like (note that you have to modify the code
	 * so that it uses your customer class instead of "MyCustomerClass", and your card class instead of 
	 * "MyCardClass" etc., and similarly with the get-methods): 
	 */
		/*	private static void writeCards (MyCustomerClass customer, PrintWriter pr){
				String pnr = customer.myGetSocialSecNoMethod();
				
				for (MyCardClass card : customer.myGetCardListMethod()){
					int cardno = card.myGetCardNoMethod();
					double price = card.myGetPriceMethod();
					
					// Get the Calendar-instance representing the card expiration date
					Calendar carddate = card.myGetDateMethod();
					// Create an instance of SimpleDateFormat that represents the way we want the string formatted
					SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd");
					// Use the format()-method to produce a string that represents a date in this format 
					// (note that this method takes a Date as input, hence we need to first use the getTime() method on our Calendar)
					String carddatestring = dformat.format(carddate.getTime());
						
					// If the card is a period card...
					if (card instanceof MyPeriodCardClass){
						// Create the string to print to file
						String cardstring = "Period;" + cardno + ";" + pnr + ";" + price + ";" + carddatestring;
						pr.println(cardstring);
						// Proceed to write all the visits of this card
						writeVisits(card,pr);
						
					} else
					 	// If this is a coupon card (klippkort)...
						if (card instanceof MyCouponCardClass){
							// Get the type of activity the card is valid for
							String type = ((MyCouponCardClass)card).myGetActivityTypeMethod();
							// Get the total no of coupons of the card (the original number, not the remaining ones)
							int totalcoupons = ((MyCouponCardClass)card).myGetCouponsMethod();
							// Create the string to print to file
							String cardstring = "Coupons;" + cardno + ";" + pnr + ";" + price + ";" + carddatestring + ";" + type + ";" + totalcoupons;
							pr.println(cardstring);
							// Proceed to write all the visits of this card
							writeVisits(card,pr);
					}
				}
				
			}*/
	
	/* TODO: If you follow the structure suggested above, then you need to create a (private static void-)
	 * method called writeVisits() that takes two parameters, one card instance and one PrintWriter. 
	 * The method should then retrieve the data about the visits from the card instance, which 
	 * probably keeps a list of cards, create a string in the appropriate format for each one, and write 
	 * it to the file using the PrintWriter. 
	 * 
	 * The method could look something like (note that you have to modify the code
	 * so that it uses your customer class instead of "MyCardClass" etc., and similarly with the get-methods): 
	 */
	
		/*private static void writeVisits (MyCardClass card, PrintWriter pr){	
			for (MyVisitClass visit  : card.myGetVisitListMethod()){
				// Get the Calendar-instance representing the visit date and time
				Calendar visitdatetime = visit.myGetDateTimeMethod();
				// Create an instance of SimpleDateFormat that represents the way we want the string formatted
				SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm");
				// Use the format()-method to produce a string that represents a date in this format 
				// (note that this method takes a Date as input, hence we need to first use the getTime() method on our Calendar)
				String visitdatetimestring = dformat.format(visitdatetime.getTime());
						
				// Get the type of activity of this visit
				String type = visit.myGetActivityTypeMethod();
				// Get the title of the actual activity/gymclass
				String activity = visit.myGetActivityTitleMethod();
				// Create the string to be written to file
				String visitstring = "V;" + visitdatetimestring + ";" + type + ";" + activity;
				pr.println(visitstring);
				
			}
		}*/
	

}
