package gymmet_main.dao;

import gymmet_main.model.Card;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

public class CardJDBCTemplate implements CardDAO {
   @SuppressWarnings("unused")
   private DataSource dataSource;
   private JdbcTemplate jdbcTemplateObject;
   
   public void setDataSource(DataSource dataSource) {
      this.dataSource = dataSource;
      this.jdbcTemplateObject = new JdbcTemplate(dataSource);
   }

   public void create(Card card) {
      String SQL = "insert into cards (cardtype, customer) values (?, ?)";
      jdbcTemplateObject.update( SQL, card.getCardType(), card.getCardCustomerID());
      System.out.println("Created Card Record Type = " + card.getCardType() + " Customer = " + card.getCardCustomerID());
      return;
   }

   public Card getCard(Integer id) {
      String SQL = "select * from cards where cardid = ?";
      Card Card = jdbcTemplateObject.queryForObject(SQL, 
                        new Object[]{id}, new CardRowMapper());
      return Card;
   }

   public List<Card> listCards() {
      String SQL = "select * from cards";
      List <Card> Cards = jdbcTemplateObject.query(SQL, 
                                new CardRowMapper());
      return Cards;
   }

   public void delete(Integer id){
      String SQL = "delete from cards where cardid = ?";
      jdbcTemplateObject.update(SQL, id);
      System.out.println("Deleted Card Record with ID = " + id );
      return;
   }

   public void update(Card card){
	   // TODO Update coupons etc.
      String SQL = "update cards set customer = ?, cardtype = ?, expires = ?, where cardid = ?";
      jdbcTemplateObject.update(SQL, card.getCardCustomerID(), card.getCardType(), card.getCardExpiresDate(), card.getCardID());
      System.out.println("Updated Card Record with ID = " + card.getCardID() );
      return;
   }

}