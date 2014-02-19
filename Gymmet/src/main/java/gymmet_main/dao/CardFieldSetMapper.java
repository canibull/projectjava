package gymmet_main.dao;

import gymmet_main.model.Card;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

public class CardFieldSetMapper implements FieldSetMapper<Card> {
    public Card mapFieldSet(FieldSet fieldSet) {
        Card card = new Card();
        // TODO Error checking
        // Set ID to array list size + 1 since CSV does not contain any ID
        card.setID(Card.getCards().size());
        card.setCardCustomer(fieldSet.readInt(0));
        card.setCardPnr(fieldSet.readInt(1)); 
        card.setCardAddress(fieldSet.readString(2));
        card.setCardPhone(fieldSet.readString(3));

        return card;
    }
}