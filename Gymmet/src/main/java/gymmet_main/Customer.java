package gymmet_main;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;

@SuppressWarnings("serial")
public class Customer implements Serializable {

public static CustomerDAO jdbc_DAO;

private int custID;
private long custPnr;
/**
 * custAltered is used if any information has been set and need to be committed
 */
private boolean custAltered = false;
private String custName, custAddress, custPhone;
// Array containing indexes of cards linked to customer
private int[] custCards;
// Static variable containing loaded customer instances
private static LinkedHashMap<Integer, Customer> customers = new LinkedHashMap<Integer, Customer>();
// Static variable containing new or externally loaded customer instances
private static LinkedHashMap<Integer, Customer> extCustomers = new LinkedHashMap<Integer, Customer>();

public Customer() {

}

public Customer(int custID, long custPnr, String custName, String  custAddress,  String custPhone, int[] custCards) {
	this.custID = custID;
	this.setCustPnr(custPnr);
	this.setCustName(custName);
	this.setCustAddress(custAddress);
	this.setCustPhone(custPhone);
	this.setCustCards(custCards);
	this.setCustAltered(false);
}

/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
public String toString() {
    String output = "CUSTOMER:ID=" + custID + ", Name=" + custName + ", Identification=" +  custPnr +
    		", Address=" + custAddress + ", Phone=" + custPhone;
    return output;
}

public void addToList(int index, Customer cust) {
	customers.put(index, cust);
}

public void addToExtList(int index, Customer cust) {
	extCustomers.put(index, cust);
}

/**
 * Static function to store all unsaved changes to customers and add any external customers
 * @return true if a change is made false if not
 */
public static boolean commitChanges() {
	boolean output = false;
	if (!extCustomers.isEmpty()) {
		for (Customer excust: extCustomers.values()) {
			jdbc_DAO.create(excust);
		}
		extCustomers.clear();
	}
	for (Customer cust: customers.values()) {
		if (cust.isCustAltered()) {
			jdbc_DAO.update(cust);
			output = true;
		}
	}
	return output;
}

/**
 * @return Get the ArrayList containing customers
 */
public static Collection<Customer> getCustomers() {
	return customers.values();
}

/**
 * @return Get the ArrayList containing external customers
 */
public static Collection<Customer> getExtCustomers() {
	return extCustomers.values();
}

/**
 * Set customer id
 * @param custID
 */
protected void setID(int custID) {
	this.custID = custID;
	this.setCustAltered(true);
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
	return this.custID;
}

public boolean isCustAltered() {
	return custAltered;
}

public void setCustAltered(boolean custAltered) {
	this.custAltered = custAltered;
}

}