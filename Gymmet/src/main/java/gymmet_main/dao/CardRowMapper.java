package gymmet_main.dao;

import gymmet_main.model.Card;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CardRowMapper implements RowMapper<Card> {
   public Card mapRow(ResultSet rs, int rowNum) throws SQLException {
	  Card card = new Card();
      card.setID(rs.getInt("cardid"));
      card.setCardCustomer(rs.getInt("customer"));
//      card.setCardPnr(rs.getLong("cardpnr")); 
//      card.setCardAddress(rs.getString("cardAddress"));
//      card.setCardPhone(rs.getString("cardphone"));
      return card;
   }
}