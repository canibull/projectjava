package gymmet_main.dao;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;

import gymmet_main.model.Card;
import gymmet_main.model.Customer;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

public class CardFieldSetMapper implements FieldSetMapper<Card> {
    public Card mapFieldSet(FieldSet fieldSet) {
        Card card = new Card();

        card.setID(fieldSet.readInt(1));
        card.setCardCustomerID(Customer.getCustomer(fieldSet.readLong(2)).getCustID());
        card.setCardCreatedDate(new Timestamp(System.currentTimeMillis()));

        try {
			card.setCardExpiresDate((Timestamp) DateFormat.getInstance().parse(fieldSet.readString(4)));
		} catch (ParseException e) {
			// TODO More robust error check
			card.setCardCreatedDate(new Timestamp(System.currentTimeMillis()));
		}

        if (fieldSet.readString(0) == "Coupons") {
        	card.setCardType((char) 'c'); 
        	card.setCardCoupons(fieldSet.readInt(6));
        } else if (fieldSet.readString(0) == "Period") {
        	card.setCardType((char) 'p');
        	card.setCardCoupons(0);
        }

        return card;
    }
}