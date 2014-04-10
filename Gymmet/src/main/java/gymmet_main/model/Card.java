package gymmet_main.model;

import gymmet_main.dao.CardDAO;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@SuppressWarnings("serial")
public class Card implements Serializable {

public static CardDAO jdbc_DAO;	

private int cardID;

/**
 * cardAltered is used if any information has been set and need to be committed
 */
private boolean cardAltered = false;
private int cardCoupons;
private long cardCustomer;
private String cardLimit;
private Character cardType;
private Date cardCreatedDate, cardExpiresDate, cardUpdatedDate;
private static DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");

// Static variable containing loaded Card instances indexed by customer database ID
private static LinkedHashMap<Long, LinkedHashMap<Integer, Card>> Cards = new LinkedHashMap<Long, LinkedHashMap<Integer, Card>>();

public Card() {

}

public String toString() {
	Date currentDate = new Date(System.currentTimeMillis());
	String output = "";
	if (cardExpiresDate.before(currentDate)) output += "EXPIRED -- ";
	if (cardType == 'c') {
		if (cardCoupons == 0) output += "EMPTY --";
		output += "Card: "+cardID+" Type: Coupon for "+cardLimit+", Remaining coupons:"+cardCoupons+" Expires: "+df.format(cardExpiresDate);
	} else if (cardType == 'p') {
		output += "Card: "+cardID+" Type: Period, Period expires: "+df.format(cardExpiresDate);
	}
    return output;
}

/**
 * Set Card id, used by fieldmapper
 * @param cardID
 */
public void setID(int cardID) {
	this.cardID = cardID;
	this.setCardAltered(true);
}

/**
 * @return Get the ArrayList containing Cards
 */
public static LinkedHashMap<Long, LinkedHashMap<Integer, Card>> getCards() {
	return Cards;
}

/**
 * Static function to store all unsaved changes to customers and add any external customers
 * @return true if a change is made false if not
 */
public static boolean commitChanges() {
	boolean output = false;
//	if (!extCards.isEmpty()) {
//		for (List<Card> extcustcards: extCards.values()) {
//			for (Card extcard: extcustcards) {
//			if (extcard.isCardAltered()) {
//				jdbc_DAO.update(extcard);
//				output = true;
//			}
//		}
//		extCards.clear();
//	}
	for (LinkedHashMap<Integer, Card> custcards: Cards.values()) {
		for (Card card: custcards.values()) {
			if  (card.getCardUpdatedDate() == null) {
				jdbc_DAO.create(card);
				card.setCardAltered(false);
				output = true;
			} else if (card.isCardAltered()) {
				jdbc_DAO.update(card);
				card.setCardAltered(false);
				output = true;
			}
		}
	}
	return output;
}

public long getCardCustomerPnr() {
	return cardCustomer;
}

public void setCardCustomerPnr(long custPnr) {
	this.cardCustomer = custPnr;
	this.setCardAltered(true);
}

public int getCardID() {
	return cardID;
}

/**
 * A function to check if card is valid based on coupons and/or expiry date
 * @return true if valid
 */
public boolean isCardValid(String visitType) {
	Date currentDate = new Date(System.currentTimeMillis());
	if (cardType == 'p') {
		if (cardExpiresDate.after(currentDate)) return true;
	} else if (cardType == 'c') {
		if (this.getCardCoupons() > 0 && cardExpiresDate.after(currentDate) && getCardLimit().equals(visitType)) {
			return true;
		}
	}
	return false;
}

public boolean isCardAltered() {
	return cardAltered;
}

public void setCardAltered(boolean cardAltered) {
	this.cardAltered = cardAltered;
}

public Character getCardType() {
	return cardType;
}

public void setCardType(Character cardType) {
	this.cardType = cardType;
	this.setCardAltered(true);
}

/**
 * Add card to the static list based on customer database ID, if non existent create index
 * @param card Card object to add
 */
public void addToList(Card card) {
	Customer addCustomer = Customer.getCustomer(card.getCardCustomerPnr());
	if (addCustomer != null) {
		long custPnr = addCustomer.getCustPnr();
		if (Cards.containsKey(custPnr)) {
			Cards.get(custPnr).put(card.getCardID(), card);
		} else {
			LinkedHashMap<Integer, Card> cardlist = new LinkedHashMap<Integer, Card>();
			cardlist.put(card.getCardID(), card);
			Cards.put(custPnr, cardlist);
		}
	}
}

public Date getCardCreatedDate() {
	return cardCreatedDate;
}

public void setCardCreatedDate(Timestamp timestamp) {
	this.cardCreatedDate = new Date(timestamp.getTime());
	this.setCardAltered(true);
}

public Date getCardExpiresDate() {
	return cardExpiresDate;
}

public void setCardExpiresDate(Timestamp timestamp) {
	this.cardExpiresDate = new Date(timestamp.getTime());
	this.setCardAltered(true);
}

public Date getCardUpdatedDate() {
	return cardUpdatedDate;
}

public void setCardUpdatedDate(Timestamp timestamp) {
	this.cardUpdatedDate = new Date(timestamp.getTime());
	this.setCardAltered(true);
}

public int getCardCoupons() {
	return cardCoupons;
}

public void setCardCoupons(int cardCoupons) {
	this.cardCoupons = cardCoupons;
	this.setCardAltered(true);
}

/**
 * @return the cardLimit
 */
public String getCardLimit() {
	return cardLimit;
}

/**
 * @param cardLimit the cardLimit to set
 */
public void setCardLimit(String cardLimit) {
	this.cardLimit = cardLimit;
}

}