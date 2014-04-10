package gymmet_main.dao;

import gymmet_main.model.Visit;

import java.util.List;

import javax.sql.DataSource;

public interface VisitDAO {
   /** 
    * This is the method to be used to initialize
    * database resources ie. connection.
    */
   public void setDataSource(DataSource ds);
   /** 
    * This is the method to be used to create
    * a record in the Visit table.
    */
   public void create(Visit visit);
   /** 
    * This is the method to be used to list down
    * a record from the Visit table corresponding
    * to a passed Visit id.
    */
   public Visit getVisit(Integer id);
   /** 
    * This is the method to be used to list down
    * all the records from the Visit table.
    */
   public List<Visit> loadVisits();
   /** 
    * This is the method to be used to delete
    * a record from the Visit table corresponding
    * to a passed Visit id.
    */
   public void delete(Integer id);
   /** 
    * This is the method to be used to update
    * a record into the Visit table.
    */
   public void update(Visit visit);
}