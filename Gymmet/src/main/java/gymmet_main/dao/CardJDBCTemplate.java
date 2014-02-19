package gymmet_main.dao;

import gymmet_main.model.Card;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

public class CardJDBCTemplate implements CardDAO {
   private DataSource dataSource;
   private JdbcTemplate jdbcTemplateObject;
   
   public void setDataSource(DataSource dataSource) {
      this.dataSource = dataSource;
      this.jdbcTemplateObject = new JdbcTemplate(dataSource);
   }

   public void create(char type, Integer customer) {
      String SQL = "insert into cards (cardtype, customer) values (?, ?)";
      
      jdbcTemplateObject.update( SQL, type, customer);
      System.out.println("Created Card Record Type = " + type + " Customer = " + customer);
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

   public void update(Integer id, Integer customer){
	   // TODO Update coupons etc.
      String SQL = "update cards set customer = ? where cardid = ?";
      jdbcTemplateObject.update(SQL, customer, id);
      System.out.println("Updated Card Record with ID = " + id );
      return;
   }

}