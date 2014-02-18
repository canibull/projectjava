package gymmet_main;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

@SuppressWarnings("serial")
public class Card implements Serializable {

public static CardDAO jdbc_DAO;	

private int cardID;
private long cardPnr;
/**
 * cardAltered is used if any information has been set and need to be committed
 */
private boolean cardAltered = false;
private int cardCustomer;
private String cardAddress, cardPhone;
// TODO l�s in Cards objekt till en arraylist d� informationen efterfr�gas fr�n databasen ist�llet
// Array containing indexes of cards linked to Card
private int[] cardCards;
// Static variable containing loaded Card instances
private static ArrayList<Card> Cards = new ArrayList<Card>();

public Card() {
	Cards.add(cardID, this);
}

public Card(int cardID, long cardPnr, int cardCustomer, String  cardAddress,  String cardPhone, int[] cardCards) {
	this.cardID = cardID;
	this.setCardPnr(cardPnr);
	this.setCardCustomer(cardCustomer);
	this.setCardAddress(cardAddress);
	this.setCardPhone(cardPhone);
	this.setCardCards(cardCards);
	this.setCardAltered(false);
	// Add this instance to the list of Cards with cardID as index
	Cards.add(cardID, this);
}

public String toString() {
    String output = "Card:ID=" + cardID + ", ID=" + cardCustomer;
    return output;
}


/**
 * Set Card id, used by fieldmapper
 * @param cardID
 */
protected void setID(int cardID) {
	this.cardID = cardID;
	this.setCardAltered(true);
}

/**
 * @return Get the ArrayList containing Cards
 */
public static ArrayList<Card> getCards() {
	return Cards;
}

/**
 * Commit changes to Card and update data-set
 * @return true if successful false if an error occurred
 */
public boolean commitChanges() {
	// TODO implement functionality
	if (this.isCardAltered()) {
		return true;
	}
	return false;
}

/**
 * Static function to store all unsaved changes to Cards
 * @return True if anything changed
 */
public static boolean commitAllChanges() {
	boolean output = false;
	Iterator<Card> iter = Cards.iterator();
	while (iter.hasNext()) {
		Card card = iter.next();
		if (card.isCardAltered()) {
			// TODO implement functionality
			output = true;
		}
	}
	return output;
}

public int getCardCustomer() {
	return cardCustomer;
}

public void setCardCustomer(int customer) {
	this.cardCustomer = customer;
	this.setCardAltered(true);
}

public String getCardAddress() {
	return cardAddress;
}

public void setCardAddress(String cardAddress) {
	this.cardAddress = cardAddress;
	this.setCardAltered(true);
}

public int[] getCardCards() {
	return cardCards;
}

public void setCardCards(int[] cardCards) {
	this.cardCards = cardCards;
	this.setCardAltered(true);
}

public String getCardPhone() {
	return cardPhone;
}

public void setCardPhone(String cardPhone) {
	this.cardPhone = cardPhone;
	this.setCardAltered(true);
}

public long getCardPnr() {
	return cardPnr;
}

public void setCardPnr(long cardPnr) {
	this.cardPnr = cardPnr;
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

}