package gymmet_main.model;

import gymmet_main.dao.CustomerDAO;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

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
private List<Card> custCards;

// Static variable containing loaded customer instances
private static LinkedHashMap<Long, Customer> customers = new LinkedHashMap<Long, Customer>();
// Static variable containing new or externally loaded customer instances
private static LinkedHashMap<Long, Customer> extCustomers = new LinkedHashMap<Long, Customer>();

public Customer() {

}

public Customer(int custID, long custPnr, String custName, String  custAddress,  String custPhone) {
	this.custID = custID;
	this.setCustPnr(custPnr);
	this.setCustName(custName);
	this.setCustAddress(custAddress);
	this.setCustPhone(custPhone);
	this.setCustAltered(false);
}

/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
public String toString() {
	// String output = "CUSTOMER:ID=" + custID + ", Name=" + custName + ", Identification=" +  custPnr + ", Address=" + custAddress + ", Phone=" + custPhone;
    String output = "ID: " +  custPnr + 
    		"	[Name: " + custName + 
    		",	Address: " + custAddress + 
    		", Phone: " + custPhone +
    		", DBID: " + custID +"]";
    return output;
}

/**
 * Add a customer to the list of customers 
 * @param index
 * @param cust
 */
public void addToList(Long index, Customer cust) {
	customers.put(index, cust);
}

/**
 * Add to the external list of customers not in database (created or imported)
 * @param index
 * @param cust
 */
public void addToExtList(Long index, Customer cust) {
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
 * @return Get a specific customer based on identification number
 */
public static Customer getCustomer(long custID) {
	return customers.get(custID);
}

/**
 * @return Get list of customers
 */
public static Collection<Customer> getCustomers() {
	return customers.values();
}

/**
 * @return Get list of external customers
 */
public static Collection<Customer> getExtCustomers() {
	return extCustomers.values();
}

/**
 * Validate if ID is actually a customer
 * @param custPnr Identification number
 * @return true if such a customer exists
 */
public static boolean isCustomer(long custPnr) {
	return customers.containsKey(custPnr);
}

/**
 * Set customer id
 * @param custID
 */
public void setID(int custID) {
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

public List<Card> getCustCards() {
	return custCards;
}

public void setCustCards(List<Card> custCards) {
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