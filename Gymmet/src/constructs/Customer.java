package constructs;
import java.util.ArrayList;

/**
 * Customer class, stores and handles customer information
 */

/**
 * @author riz
 *
 */
public class Customer {
	private long custID, custPnr;
	private String custName, custAddress, custPhone;
	// TODO läs in Cards objekt till en arraylist då informationen efterfrågas från databasen istället
	// Array innehållande index till de kort kunden har på sitt konto
	private int[] custCards;
	// Statisk variabel där kunder lagras då instanser skapas
	private static ArrayList<Customer> customers = new ArrayList<Customer>();

	public Customer(int custID, long custPnr, String custName, String  custAddress,  String custPhone, int[] custCards) {
		this.custID = custID;
		this.setCustPnr(custPnr);
		this.setCustName(custName);
		this.setCustAddress(custAddress);
		this.setCustPhone(custPhone);
		this.setCustCards(custCards);
		// Add this instance to the list of customers with custID as index
		customers.add(custID, this);
	}

	/**
	 * Fetch a specific customer object based on it's ID
	 * @param custID
	 * @return A customer object
	 */
	public static Customer getCustomer(int custID) {
		return customers.get(custID);
	}

	/**
	 * @return Get the ArrayList containing customers
	 */
	public static ArrayList<Customer> getCustomers() {
		return customers;
	}

	/**
	 * Commit changes to customer and update data-set
	 * @return true if successful false if an error occurred
	 */
	public boolean commitChanges() {
		// TODO implement functionality
		return true;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getCustAddress() {
		return custAddress;
	}

	public void setCustAddress(String custAddress) {
		this.custAddress = custAddress;
	}

	public int[] getCustCards() {
		return custCards;
	}

	public void setCustCards(int[] custCards) {
		this.custCards = custCards;
	}

	public String getCustPhone() {
		return custPhone;
	}

	public void setCustPhone(String custPhone) {
		this.custPhone = custPhone;
	}

	public long getCustPnr() {
		return custPnr;
	}

	public void setCustPnr(long custPnr) {
		this.custPnr = custPnr;
	}

	public long getCustID() {
		return custID;
	}

}
