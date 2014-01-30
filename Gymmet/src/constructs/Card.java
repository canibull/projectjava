package constructs;
import java.util.ArrayList;

/**
 * Card class, stores and handles customer information
 */

/**
 * Constructor for card class
 * @author riz
 *
 */
public class Card {
	private long custID, custPnr;
	private String custName, custAddress, custPhone;
	private int[] custCards;
	private static ArrayList<Card> customers = new ArrayList<Card>();

	public Card(int custID, long custPnr, String custName, String  custAddress,  String custPhone, int[] custCards) {
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
	 * Fetch a specific card object based on it's ID
	 * @param custID
	 * @return A customer object
	 */
	public static Card getCustomer(int custID) {
		return customers.get(custID);
	}

	/**
	 * @return Get the ArrayList containing customers
	 */
	public static ArrayList<Card> getCustomers() {
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
