import io.DataIO;

import java.util.ArrayList;
import java.util.Iterator;

import constructs.Customer;

public class main {

	public static void main(String[] args) {
		// L�sning fr�n fil mha. DataIO, vi bygger p� databaskoppling men redovisar detta
		// tills vidare f�r att m�ta deadline.

		DataIO.readData();
		ArrayList<Customer> customers = Customer.getCustomers();
		Iterator<Customer> blah = customers.iterator();
		while (blah.hasNext()) {
			Customer currentCustomer = blah.next();
			System.out.println(currentCustomer.getCustName() + " " + currentCustomer.getCustPnr());
		}
	}

}
