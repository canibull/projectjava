package gymmet_main.dao;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import gymmet_main.model.Card;
import gymmet_main.model.Visit;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

public class PeriodCardFieldSetMapper implements FieldSetMapper<Object> {
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public Object mapFieldSet(FieldSet fieldSet) {
        Card card = new Card();

        card.setID(fieldSet.readInt(1));
        card.setCardCustomerPnr(fieldSet.readLong(2));
        card.setCardCreatedDate(new Timestamp(System.currentTimeMillis()));

        try {
			card.setCardExpiresDate(new Timestamp(dateFormat.parse(fieldSet.readString(4)).getTime()));
		} catch (ParseException e) {
			card.setCardExpiresDate(new Timestamp(System.currentTimeMillis()));
		}

    	card.setCardType((char) 'p');
    	card.setCardCoupons(0);
        Visit.lastCardAddedID = card.getCardID();
        Visit.lastCardAddedPnr = card.getCardCustomerPnr();

        card.addToList(card);

        return 1;
    }
}