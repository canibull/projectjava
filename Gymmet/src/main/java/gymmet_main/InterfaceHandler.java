package gymmet_main;

import java.util.List;

public class InterfaceHandler extends Application {
	public static void interactionLoop() {
        System.out.println("--- START ---\n");

        readCustomersFromCSV();
        System.out.println(Customer.getCustomers().size());

        loadCustomers();
        System.out.println(Customer.getCustomers().size());

        loadCustomers();
        System.out.println(Customer.getCustomers().size());

        for (Customer record : Customer.getCustomers()) {
            System.out.println(record.toString());
        }
        System.out.println(Customer.getCustomers().size());

//        List<Card> cards = Card.jdbc_DAO.listCards();
//        for (Card record : cards) {
//           System.out.println(record.toString());
//        }

        System.out.println("\n--- STOP ---");
	}

	/**
	 * Print a list of customers to terminal
	 */
	public static void loadCustomers() {
        List<Customer> customers = Customer.jdbc_DAO.listCustomers();
        for (Customer record : customers) {
           System.out.println(record.toString());
        }
	}

}
