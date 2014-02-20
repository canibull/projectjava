package gymmet_main.dao;

import gymmet_main.model.Card;

import java.util.List;

import javax.sql.DataSource;

public interface CardDAO {
   /** 
    * This is the method to be used to initialize
    * database resources ie. connection.
    */
   public void setDataSource(DataSource ds);
   /** 
    * This is the method to be used to create
    * a record in the Card table.
    */
   public void create(Card card);
   /** 
    * This is the method to be used to list down
    * a record from the Card table corresponding
    * to a passed Card id.
    */
   public Card getCard(Integer id);
   /** 
    * This is the method to be used to list down
    * all the records from the Card table.
    */
   public List<Card> listCards();
   /** 
    * This is the method to be used to delete
    * a record from the Card table corresponding
    * to a passed Card id.
    */
   public void delete(Integer id);
   /** 
    * This is the method to be used to update
    * a record into the Card table.
    */
   public void update(Card card);
}