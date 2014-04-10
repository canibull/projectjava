package gymmet_main.model;

import gymmet_main.dao.VisitDAO;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@SuppressWarnings("serial")
public class Visit implements Serializable {

public static VisitDAO jdbc_DAO;	

private int visitID;

/**
 * visitAltered is used if any information has been set and needs to be committed
 */
private boolean visitAltered = false;
private long visitCustomer;
private int visitCardID;
private String visitType, visitDescription;
private Date visitDate;
private static DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");

/**
 * Needed for import from retarded source that does not link ID:s, used by CardFieldSetMapper
 */
public static int lastCardAddedID;
public static long lastCardAddedPnr;

// Static variable containing loaded Visit instances indexed by customer database ID
private static LinkedHashMap<Integer, List<Visit>> Visits = new LinkedHashMap<Integer, List<Visit>>();

public Visit() {

}

public Visit(int visitID, long visitCustomerPnr, String visitType) {
	this.visitID = visitID;
	this.setVisitCustomerPnr(visitCustomerPnr);
	this.setVisitAltered(false);
}

public String toString() {
	String output = "Visit: "+ visitType + " " + df.format(visitDate) + " " + visitDescription;
    return output;
}

/**
 * Set Visit id, used by fieldmapper
 * @param visitID
 */
public void setID(int visitID) {
	this.visitID = visitID;
	this.setVisitAltered(true);
}

/**
 * @return Get the ArrayList containing Visits
 */
public static LinkedHashMap<Integer, List<Visit>> getVisits() {
	return Visits;
}

/**
 * Static function to store all unsaved changes to customers and add any external customers
 * @return true if a change is made false if not
 */
public static boolean commitChanges() {
	boolean output = false;
	for (List<Visit> custvisits: Visits.values()) {
		for (Visit visit: custvisits) {
			if (visit.getVisitID() == -1) {
				jdbc_DAO.create(visit);
				visit.setVisitAltered(false);
				output = true;
			} else if (visit.isVisitAltered()) {
				jdbc_DAO.update(visit);
				visit.setVisitAltered(false);
				output = true;
			}
		}
	}
	return output;
}

public long getVisitCustomerPnr() {
	return visitCustomer;
}

public void setVisitCustomerPnr(long custPnr) {
	this.visitCustomer = custPnr;
	this.setVisitAltered(true);
}

public int getVisitID() {
	return visitID;
}

public boolean isVisitAltered() {
	return visitAltered;
}

public void setVisitAltered(boolean visitAltered) {
	this.visitAltered = visitAltered;
}

public String getVisitType() {
	return visitType;
}

public void setVisitType(String visitType) {
	this.visitType = visitType;
	this.setVisitAltered(true);
}

/**
 * Add visit to the static list based on card database ID, if non existent create index
 * @param visit Visit object to add
 */
public void addToList(Visit visit) {
		if (Visits.containsKey(visit.getVisitCardID())) {
			Visits.get(visit.getVisitCardID()).add(visit);
		} else {
			List<Visit> visitlist = new ArrayList<Visit>();
			visitlist.add(visit);
			Visits.put(visit.getVisitCardID(), visitlist);
		}
}

public Date getVisitDate() {
	return visitDate;
}

public void setVisitDate(Timestamp timestamp) {
	this.visitDate = new Date(timestamp.getTime());
	this.setVisitAltered(true);
}

public int getVisitCardID() {
	return visitCardID;
}

public void setVisitCardID(int visitCardID) {
	this.visitCardID = visitCardID;
	this.setVisitAltered(true);
}

/**
 * @return the visitDescription
 */
public String getVisitDescription() {
	return visitDescription;
}

/**
 * @param visitDescription the visit description to set
 */
public void setVisitDescription(String visitDescription) {
	this.visitDescription = visitDescription;
	this.setVisitAltered(true);
}

}