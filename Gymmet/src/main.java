import io.DataIO;

import java.util.ArrayList;
import java.util.Iterator;

import constructs.Customer;

public class main {

	public static void main(String[] args) {
		// Läsning från fil mha. DataIO, vi bygger på databaskoppling men redovisar detta
		// tills vidare för att möta deadline.

		DataIO.readData();
		ArrayList<Customer> customers = Customer.getCustomers();
		Iterator<Customer> blah = customers.iterator();
		while (blah.hasNext()) {
			Customer currentCustomer = blah.next();
			System.out.println(currentCustomer.getCustName() + " " + currentCustomer.getCustPnr());
		}
	}

}
