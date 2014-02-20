package gymmet_main.model;

import gymmet_main.dao.CardDAO;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
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
private int cardCustomer, cardCoupons;
private Character cardType;
private Date cardCreatedDate, cardExpiresDate, cardUpdatedDate;

// Static variable containing loaded Card instances indexed by customer database ID
private static LinkedHashMap<Integer, List<Card>> Cards = new LinkedHashMap<Integer, List<Card>>();

public Card() {

}

public Card(int cardID, int cardCustomerID, Character cardType) {
	this.cardID = cardID;
	this.setCardCustomerID(cardCustomerID);
	
	this.setCardAltered(false);
}

public String toString() {
    String output = "Card:ID=" + cardID + ", ID=" + cardCustomer;
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
public static LinkedHashMap<Integer, List<Card>> getCards() {
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
	for (List<Card> custcards: Cards.values()) {
		for (Card card: custcards) {
			if (card.isCardAltered()) {
				jdbc_DAO.update(card);
				output = true;
			}
		}
	}
	return output;
}

public int getCardCustomerID() {
	return cardCustomer;
}

public void setCardCustomerID(int customer) {
	this.cardCustomer = customer;
	this.setCardAltered(true);
}

public int getCardID() {
	return cardID;
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
	if (Cards.containsKey(card.getCardCustomerID())) {
		Cards.get(card.getCardCustomerID()).add(card);
	} else {
		List<Card> cardlist = new ArrayList<Card>();
		Cards.put(card.getCardCustomerID(), cardlist);
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

}