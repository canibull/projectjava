package gymmet_main;

import gymmet_main.model.Card;
import gymmet_main.model.Customer;
import gymmet_main.model.Visit;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Scanner;

public class InterfaceHandler extends Application {
	private static HashMap<String, String> helpData = new HashMap<String, String>();
	private static Scanner in = new Scanner(System.in);
	private static String commLineString = "» ";

	public static void interactionLoop() throws Exception {
        System.out.println("--- START ---\n");

		// Hashmap som innehåller hjälpdata 
		helpData.put("add","Add a series of integers.");
		helpData.put("help","Displays a help message.");
		helpData.put("quit","Ends the program.");
		helpData.put("story","Generates a story.");
		helpData.put("generateStories","Generates stories from a file.");

        // Read customers from mySQL database
        ActionHandler.loadCustomers();
        // Read customers from mySQL database
        ActionHandler.loadCards();
        // Read customers from mySQL database
        ActionHandler.loadVisits();

        //ActionHandler.readCardsFromCSV();
        //Visit.commitChanges();
		System.out.println("Welcome to the program\nWrite ´quit´ to quit.");

        // Print a list of all customers
        listCustomers();

// Debug cards, list all cards
//        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
//	    for (List<Card> cardl : Card.getCards().values()) {
//	    	for (Card card: cardl) { 
//	    	System.out.println("CardID:"+card.getCardID()+" Coupons:"+card.getCardCoupons()+" Expires:"+df.format(card.getCardExpiresDate()));
//	    	}
//	    }

        try {
			// Kör den primära loopen i programet
			topLoop();
		} catch (Exception e) {
			// Ge ett standardfel om inte Exception:en hanteras på ett annat ställe
			e.printStackTrace();
		}

        System.out.println("\n--- STOP ---");
	}

	public static void topLoop() throws Exception {
		String kommando = new String();
		// Kör en loop som läser in data från standard input
		showHelp();
		while(true) {
			// Reset on every loop
			commLineString = "» ";
			System.out.print(commLineString);
			// Läs in nästa rad från standard input
			kommando = in.nextLine();
			// Hantera specialkommandot quit som måste kunna bryta loopen eller skicka vidare
			// kommandot som en array där indata separeras på mellanslagstecken till funktionen handleInput
			if (kommando.compareTo("quit") == 0) {
				// Fråga användaren om hen verkligen vill avsluta programmet
				if (confirm(in)) break;
			} else {
				// Dela upp kommandosträngen vid mellanslagstecken och lagra i en strängarray
				handleToploop(kommando.split(" "));
			}
		}
		Customer.commitChanges();
		Card.commitChanges();
        Visit.commitChanges();
		// Stäng scannerinstansen
		in.close();
	}

	public static boolean confirm(java.util.Scanner in) throws Exception {
		System.out.print("Write ’y’ if you really want to quit: ");
		String kommando = in.nextLine();
		if (kommando.compareTo("y") == 0) {
			return true;
		} else {
			return false;
		}
	}

	public static void handleToploop(String[] kommandoarr) throws Exception {

		if (kommandoarr[0].compareTo("handle") == 0) {

			try {
				long custPnr = Long.parseLong(kommandoarr[1]);
				if (kommandoarr.length > 1 && Customer.isCustomer(custPnr)) {
					customerLoop(custPnr);
				} else {
					System.out.println("No such customer found, check id.");
				}
			} catch (NumberFormatException e) {
				System.out.println("Invalid identification number, check id.");
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("You need to supply a identification number to handle.");
			}

		// Show help about a specific command
		} else 	if (kommandoarr[0].compareTo("view") == 0) {

			try {				
				listCustomer(Long.parseLong(kommandoarr[1]));
			// Fånga exception NumberFormatException från parseLong
			} catch (NumberFormatException e) {
					System.out.println("Argument error: The ’view’ command only accepts numerics.");
			}

		// Show help about a specific command
		} else if (kommandoarr[0].compareTo("help") == 0 && kommandoarr.length > 1 && helpData.containsKey(kommandoarr[1])) {

			showHelp(kommandoarr[1]);

		// Show available commands
		} else if (kommandoarr[0].compareTo("help") == 0) {

			showHelp();

		// Command to edit a customer
		} else if (kommandoarr[0].compareTo("edit") == 0) {

			try {
				long custPnr = Long.parseLong(kommandoarr[1]);
				if (kommandoarr.length > 1 && Customer.isCustomer(custPnr)) {
					editCustomer(custPnr);
				} else {
					System.out.println("No such customer found, check id.");
				}
			} catch (NumberFormatException e) {
				System.out.println("Invalid identification number, check id.");
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("You need to supply a identification number to edit.");
			}

		// Hämta kunder från databasen och skriv ut en lista över dem på skärmen
		} else if (kommandoarr[0].compareTo("list") == 0) {

			ActionHandler.loadCustomers();
	        listCustomers();

		// Importera kunder från CSV fil och skriv unika kunder till databasen, uppdatera sedan objekten
		} else if (kommandoarr[0].compareTo("import") == 0) {

	        ActionHandler.readCustomersFromCSV();
	    	Customer.commitChanges();
	    	ActionHandler.loadCustomers();

	    // Import card and visit data from CSV file and write them to database, update objects
		} else if (kommandoarr[0].compareTo("importcards") == 0) {

	        ActionHandler.readCardsFromCSV();
	    	Card.commitChanges();
	    	Visit.commitChanges();
	    	ActionHandler.loadCards();
	    	ActionHandler.loadVisits();

		} else if (kommandoarr[0].compareTo("new") == 0) {

			createNewCustomer();

		} else if (kommandoarr[0].compareTo("remove") == 0) {

			try {
				long custPnr = Long.parseLong(kommandoarr[1]);
				if (kommandoarr.length > 1 && Customer.isCustomer(custPnr)) {
					removeCustomer(custPnr);
				} else {
					System.out.println("No such customer found, check id.");
				}
			} catch (NumberFormatException e) {
				System.out.println("Invalid identification number, check id.");
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("You need to supply a identification number to remove.");
			}

		} else {

			System.out.println("Unknown command ’"+kommandoarr[0]+"’");
			showHelp();

		}
	}

	private static void customerLoop(long custPnr) {
		String kommando = new String();
		while(true) {
			// Reset on every loop
			commLineString = "[" + custPnr + "] » ";
			System.out.print(commLineString);
			// Läs in nästa rad från standard input
			kommando = in.nextLine();
			// Hantera specialkommandot quit som måste kunna bryta loopen eller skicka vidare
			// kommandot som en array där indata separeras på mellanslagstecken till funktionen handleInput
			if (kommando.compareTo("exit") == 0) {
				break;
			} else {
				// Dela upp kommandosträngen vid mellanslagstecken och lagra i en strängarray
				handleCustomerLoop(kommando.split(" "), custPnr);
			}
		}
	}

	private static void handleCustomerLoop(String[] kommandoarr, long custPnr) {
		if (kommandoarr[0].compareTo("cards") == 0) {
			listCards(custPnr, true);
		} if (kommandoarr[0].compareTo("log") == 0) {
			logVisit(custPnr);
		}
	}

	/**
	 * Create a new customer and commit the changes to database
	 */
	private static void logVisit(Long custPnr) {
		Visit lvs = new Visit();
		lvs.setVisitCustomerPnr(custPnr);
		lvs.setVisitDate(new Timestamp(System.currentTimeMillis()));
		listCards(custPnr, false);
        System.out.print("Cardnumber: ");
        lvs.setVisitCardID(Integer.parseInt(in.nextLine())); 
        System.out.print("Type: ");
        lvs.setVisitType(in.nextLine());
        System.out.print("Description: ");
        lvs.setVisitDescription(in.nextLine());
        Card cardUsed = Card.getCards().get(custPnr).get(lvs.getVisitCardID());
        if (cardUsed.isCardValid(lvs.getVisitType())) {
        	lvs.addToList(lvs);
        	Visit.commitChanges();
        	cardUsed.setCardCoupons(cardUsed.getCardCoupons()-1);
        	Card.commitChanges();
        	System.out.println("Visit added:\n" + lvs.toString());
        } else {
        	System.out.println("The selected card is not valid.");
        }
	}

	public static void listCards(Long custPnr, boolean showVisits) {
		if (Card.getCards().get(custPnr) != null) {
			System.out.println("User has these cards");
		    for (Card card : Card.getCards().get(custPnr).values()) {
		    	System.out.println(card.toString());
		    	if (showVisits) {
		    		if (Visit.getVisits().get((long) card.getCardID()) != null) {
			    		for (Visit cardVisit: Visit.getVisits().get((long) card.getCardID())) {
			    			System.out.println(cardVisit.toString());
			    		}
		    		}
		    	}
		    }
		} else {
			System.out.println("User has no cards");
		}
	}

	private static void removeCustomer(long custPnr) {
		System.out.println("Remove customer\n"+Customer.getCustomer(custPnr).toString());
		System.out.print("Write ’y’ if you really want to remove user: ");
		// Läs in nästa rad från standard input
		String kommando = in.nextLine();
		if (kommando.compareTo("y") == 0) {
			Customer.jdbc_DAO.delete(Customer.getCustomer(custPnr).getCustID());
		} else {
			System.out.println("Deletion aborted.");
		}
	}

	/**
	 * Print a list of available commands
	 */
	private static void showHelp() {
		System.out.println("Available commands:handle, help, list, view, edit, new, remove, status, purchase, register, quit");
	}

	/**
	 * Show help about a specific command
	 * @param command The command to show help for
	 */
	private static void showHelp(String command) {
		System.out.println(helpData.get(command));
	}



	/**
	 * Print a list of customers currently in storage
	 */
	public static void listCustomers() {
	    for (Customer record : Customer.getCustomers()) {
	        System.out.println(record.toString());
	    }
	}

	/**
	 * Print information about a specific customer
	 */
	public static void listCustomer(long custPnr) {
		try {
			System.out.println(Customer.getCustomer(custPnr).toString());
		} catch (Exception NullPointerException) {
			System.out.println("User with id "+ custPnr + " not found.");
		}
	}

	/**
	 * Create a new customer and commit the changes to database
	 */
	private static void createNewCustomer() {
        Customer cust = new Customer();
        // Set ID to array list size + 1 since CSV does not contain any ID
        cust.setID(Customer.getExtCustomers().size());
        System.out.println("Enter customer information:");
        System.out.print("Name: ");
        cust.setCustName(in.nextLine());
        System.out.print("ID (10 digits): ");
        cust.setCustPnr(Long.parseLong(in.nextLine())); 
        System.out.print("Address: ");
        cust.setCustAddress(in.nextLine());
        System.out.print("Phonenumber: ");
        cust.setCustPhone(in.nextLine());
        cust.setCustAltered(false);
        cust.addToExtList(cust.getCustPnr(), cust);

        Customer.commitChanges();
        System.out.println("\nCustomer added:\n" + cust.toString());
	}

	/**
	 * Edit an existing customer
	 * @param custPnr Identification number of customer
	 */
	public static void editCustomer(long custPnr) {
        Customer cust = Customer.getCustomer(custPnr);
        System.out.println("Editing customer\n"+cust.toString()+"\nEnter new information:");
        System.out.print("Name:");
        cust.setCustName(in.nextLine());
        System.out.print("Address: ");
        cust.setCustAddress(in.nextLine());
        System.out.print("Phonenumber: ");
        cust.setCustPhone(in.nextLine());
        cust.setCustAltered(true);

        Customer.commitChanges();
        System.out.println("\nCustomer updated:\n" + cust.toString());
	}
}