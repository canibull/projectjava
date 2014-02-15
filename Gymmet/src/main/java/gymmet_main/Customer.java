package gymmet_main;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

@SuppressWarnings("serial")
public class Customer implements Serializable {

private int custID;
private long custPnr;
/**
 * custAltered is used if any information has been set and need to be committed
 */
private boolean custAltered = false;
private String custName, custAddress, custPhone;
// TODO läs in Cards objekt till en arraylist då informationen efterfrågas från databasen istället
// Array containing indexes of cards linked to customer
private int[] custCards;
// Static variable containing loaded customer instances
private static ArrayList<Customer> customers = new ArrayList<Customer>();

public Customer() {
	customers.add(custID, this);
}

public Customer(int custID, long custPnr, String custName, String  custAddress,  String custPhone, int[] custCards) {
	this.custID = custID;
	this.setCustPnr(custPnr);
	this.setCustName(custName);
	this.setCustAddress(custAddress);
	this.setCustPhone(custPhone);
	this.setCustCards(custCards);
	this.setCustAltered(false);
	// Add this instance to the list of customers with custID as index
	customers.add(custID, this);
}

/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
public String toString() {
    String output = "CUSTOMER:ID=" + custID + ", Name=" + custName + ", Identification=" +  custPnr +
    		", Address=" + custAddress + ", Phone=" + custPhone;
    return output;
}

/**
 * Set customer id, used by fieldmapper
 * @param custID
 */
protected void setID(int custID) {
	this.custID = custID;
	this.setCustAltered(true);
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
	if (this.isCustAltered()) {
		return true;
	}
	return false;
}

/**
 * Static function to store all unsaved changes to customers
 * @return True if anything changed
 */
public static boolean commitAllChanges() {
	boolean output = false;
	Iterator<Customer> iter = customers.iterator();
	while (iter.hasNext()) {
		Customer cust = iter.next();
		if (cust.isCustAltered()) {
			// TODO implement functionality
			output = true;
		}
	}
	return output;
}

public String getCustName() {
	return custName;
}

public void setCustName(String custName) {
	this.custName = custName;
	this.setCustAltered(true);
}

public String getCustAddress() {
	return custAddress;
}

public void setCustAddress(String custAddress) {
	this.custAddress = custAddress;
	this.setCustAltered(true);
}

public int[] getCustCards() {
	return custCards;
}

public void setCustCards(int[] custCards) {
	this.custCards = custCards;
	this.setCustAltered(true);
}

public String getCustPhone() {
	return custPhone;
}

public void setCustPhone(String custPhone) {
	this.custPhone = custPhone;
	this.setCustAltered(true);
}

public long getCustPnr() {
	return custPnr;
}

public void setCustPnr(long custPnr) {
	this.custPnr = custPnr;
	this.setCustAltered(true);
}

public int getCustID() {
	return custID;
}

public boolean isCustAltered() {
	return custAltered;
}

public void setCustAltered(boolean custAltered) {
	this.custAltered = custAltered;
}

}