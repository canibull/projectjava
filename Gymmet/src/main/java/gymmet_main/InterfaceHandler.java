package gymmet_main;

import gymmet_main.model.Customer;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class InterfaceHandler extends Application {
	private static HashMap<String, String> helpData = new HashMap<String, String>();

	
	public static void interactionLoop() throws Exception {
        System.out.println("--- START ---\n");

		// Hashmap som innehåller hjälpdata 
		helpData.put("add","Add a series of integers.");
		helpData.put("help","Displays a help message.");
		helpData.put("quit","Ends the program.");
		helpData.put("story","Generates a story.");
		helpData.put("generateStories","Generates stories from a file.");

        // Read customers from mySQL database
        loadCustomers();

		System.out.println("Welcome to the program\nWrite ´quit´ to quit.");

        // Print a list of all customers
        listCustomers();

        try {
			// Kör den primära loopen i programet
			topLoop();
		} catch (Exception e) {
			// Ge ett standardfel om inte Exception:en hanteras på ett annat ställe
			e.printStackTrace();
		}

        // Print info about a specific customer
        //listCustomer(8501293345L);

//        List<Card> cards = Card.jdbc_DAO.listCards();
//        for (Card record : cards) {
//           System.out.println(record.toString());
//        }

        System.out.println("\n--- STOP ---");
	}
	public static void topLoop() throws Exception {
		// Skapa en instans av Scannerklassen
		Scanner in = new Scanner(System.in);
		String kommando;
		// Kör en loop som läser in data från standard input
		while(true) {
			System.out.print("» ");
			// Läs in nästa rad från standard input
			kommando = in.nextLine();
			// Hantera specialkommandot quit som måste kunna bryta loopen eller skicka vidare
			// kommandot som en array där indata separeras på mellanslagstecken till funktionen handleInput
			if (kommando.compareTo("quit") == 0) {
				// Fråga användaren om hen verkligen vill avsluta programmet
				if (confirm(in)) break;
			} else {
				// Dela upp kommandosträngen vid mellanslagstecken och lagra i en strängarray
				handleInput(kommando.split(" "));
			}
		}
		// Stäng scannerinstansen
		in.close();
	}

	// Passar vidare scannerreferensen eftersom jag inte avslutat scannern i topLoop
	public static boolean confirm(java.util.Scanner in) throws Exception {
		System.out.print("Write ’y’ if you really want to quit: ");
		// Läs in nästa rad från standard input
		String kommando = in.nextLine();
		if (kommando.compareTo("y") == 0) {
			return true;
		} else {
			return false;
		}
	}

	// Hantering av ej implementerade funktioner
	public static void handleInput(String[] kommandoarr) throws Exception {

		if (kommandoarr[0].compareTo("view") == 0) {

			try {				
				listCustomer(Long.parseLong(kommandoarr[1]));
			// Fånga exception NumberFormatException från parseLong
			} catch (NumberFormatException e) {
					System.out.println("Argument error: The ’view’ command only accepts numerics.");
			}

		// Om kommandot är help och det har ytterligare ett attribut och attributet är en key som finns i helpData..
		} else if (kommandoarr[0].compareTo("help") == 0 && kommandoarr.length > 1 && helpData.containsKey(kommandoarr[1])) {

			// Skriv ut värdet från helpData med key value från andra värdet i kommandoarr
			System.out.println(helpData.get(kommandoarr[1]));

		// Om förra if satsen var falsk och kommandot fortfarande är help..
		} else if (kommandoarr[0].compareTo("help") == 0) {

			// Skriv ut en lista på tillgängliga hjälpavsnitt
			System.out.println("Available commands:\nhelp\nlog\nlist\nview\nedit\ncreate\nremove\nstatus\npurchase\nregister");

		// Om kommandot är story och det finns ett andra argument ska storyn skrivas till ett filnamn 
		} else if (kommandoarr[0].compareTo("story") == 0 && kommandoarr.length > 1) {

			// Anropa funktionen makeStory med andra värdet av kommandoarray för filnamn
			//makeStory(true,kommandoarr[1]);

		// Om det inte finns något andra argument ska makestory 
		} else if (kommandoarr[0].compareTo("list") == 0) {

	        loadCustomers();
	        listCustomers();

		// Om det inte finns något andra argument ska makestory 
		} else if (kommandoarr[0].compareTo("import") == 0) {

	        readCustomersFromCSV();
	        loadCustomers();

		// Kör funktionen generateStories med ett filnamn som argument
		} else if (kommandoarr[0].compareTo("generateStories") == 0 && kommandoarr.length > 1) {

			//parseFile(kommandoarr[1]);

		} else {

			// Skriv ut meddelande om ej implementerad funktion och dess namn
			System.out.println("Unknown command ’"+kommandoarr[0]+"’");

		}

//	
//		// Hantera kommandot add
//		if (kommandoarr[0].compareTo("add") == 0) {
//
//			try {
//				int sum = 0;
//				// Summera alla heltal efter add kommandot i kommandoarrayen
//				for (int i=1; i<kommandoarr.length; i++) {
//					// Gör om input i kommandoarray till ett heltal för att kunna summera
//					sum = sum + Integer.parseInt(kommandoarr[i]);
//				}
//				// Skriv ut summan, konvertera den till en sträng pga att System.out.println ej hanterar konvertering
//				System.out.println(String.valueOf(sum));
//			// Fånga exception NumberFormatException från parseInt om argumentet ej är ett heltal
//			} catch (NumberFormatException e) {
//					System.out.println("Argument error: The ’add’ command can only add integers.");
//			}
//
//		// Om kommandot är help och det har ytterligare ett attribut och attributet är en key som finns i helpData..
//		} else if (kommandoarr[0].compareTo("help") == 0 && kommandoarr.length > 1 && helpData.containsKey(kommandoarr[1])) {
//
//			// Skriv ut värdet från helpData med key value från andra värdet i kommandoarr
//			System.out.println(helpData.get(kommandoarr[1]));
//
//		// Om förra if satsen var falsk och kommandot fortfarande är help..
//		} else if (kommandoarr[0].compareTo("help") == 0) {
//
//			// Skriv ut en lista på tillgängliga hjälpavsnitt
//			System.out.println("Available commands:\nhelp\nlog\nlist\nview\nedit\ncreate\nremove\nstatus\npurchase\nregister");
//
//		// Om kommandot är story och det finns ett andra argument ska storyn skrivas till ett filnamn 
//		} else if (kommandoarr[0].compareTo("story") == 0 && kommandoarr.length > 1) {
//
//			// Anropa funktionen makeStory med andra värdet av kommandoarray för filnamn
//			makeStory(true,kommandoarr[1]);
//
//		// Om det inte finns något andra argument ska makestory 
//		} else if (kommandoarr[0].compareTo("story") == 0) {
//			
//			makeStory(false,"");
//
//		// Kör funktionen generateStories med ett filnamn som argument
//		} else if (kommandoarr[0].compareTo("generateStories") == 0 && kommandoarr.length > 1) {
//
//			parseFile(kommandoarr[1]);
//
//		} else {
//
//			// Skriv ut meddelande om ej implementerad funktion och dess namn
//			System.out.println("Unknown command ’"+kommandoarr[0]+"’");
//
//		}
	}

	/**
	 * Load customers from database
	 */
	public static void loadCustomers() {
		Customer.getCustomers().clear();
		Customer.jdbc_DAO.loadCustomers();
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

}