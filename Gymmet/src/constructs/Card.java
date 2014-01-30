package constructs;
import java.util.ArrayList;

/**
 * Card class, stores and handles card information
 */

/**
 * Constructor for card class
 * @author Jonas Rosenqvist & Lennart Johansson
 *
 */
public class Card {
	private int cardID, custID;
	private long custPnr;
	private String cardName, cardAddress, cardPhone;
	private static ArrayList<Card> cards = new ArrayList<Card>();

	public Card(int cardID, int custID, String cardName, String  cardAddress,  String cardPhone) {
		this.cardID = cardID;
		this.cardID = custID;
		this.setCardName(cardName);
		this.setCardAddress(cardAddress);
		this.setCardPhone(cardPhone);
		// Add this instance to the list of cards with cardID as index
		cards.add(cardID, this);
	}

	/**
	 * Fetch a specific card object based on it's ID
	 * @param custID
	 * @return A customer object
	 */
	public static Card getCardomer(int custID) {
		return cards.get(custID);
	}

	/**
	 * @return Get the ArrayList containing cards for a customer
	 */
	public static ArrayList<Card> getCards(Customer reqCust) {
		// TODO hämta kort baserat på customerID från databasen
		ArrayList<Card> output = new ArrayList<Card>();
		for (int custCardID: reqCust.getCustCards()) {
			output.add(cards.get(custCardID));
		}
		return output;
	}

	/**
	 * Commit changes to customer and update data-set
	 * @return true if successful false if an error occurred
	 */
	public boolean commitChanges() {
		// TODO implement functionality
		return true;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String custName) {
		this.cardName = custName;
	}

	public String getCardAddress() {
		return cardAddress;
	}

	public void setCardAddress(String custAddress) {
		this.cardAddress = custAddress;
	}

	public String getCardPhone() {
		return cardPhone;
	}

	public void setCardPhone(String custPhone) {
		this.cardPhone = custPhone;
	}

	public long getCardPnr() {
		return custPnr;
	}

	public void setCustID(int custID) {
		this.cardID = custID;
	}

	public long getCustID() {
		return custID;
	}

}
