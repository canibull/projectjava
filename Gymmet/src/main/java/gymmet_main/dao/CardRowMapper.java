package gymmet_main.dao;

import gymmet_main.model.Card;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CardRowMapper implements RowMapper<Card> {
   public Card mapRow(ResultSet rs, int rowNum) throws SQLException {
	  Card card = new Card();
      card.setID(rs.getInt("cardid"));
      card.setCardCustomerPnr(rs.getLong("customer"));
      card.setCardType(rs.getString("cardtype").charAt(0));
      card.setCardLimit(rs.getString("limitation"));
      card.setCardCreatedDate(rs.getTimestamp("created"));
      card.setCardExpiresDate(rs.getTimestamp("expires"));
      card.setCardUpdatedDate(rs.getTimestamp("updated"));
      card.setCardCoupons(rs.getInt("coupons"));
      card.setCardAltered(false);
      card.addToList(card);
      return card;
   }
}