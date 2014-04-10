package gymmet_main.dao;

import gymmet_main.model.Visit;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

public class VisitJDBCTemplate implements VisitDAO {
   @SuppressWarnings("unused")
   private DataSource dataSource;
   private JdbcTemplate jdbcTemplateObject;
   
   public void setDataSource(DataSource dataSource) {
      this.dataSource = dataSource;
      this.jdbcTemplateObject = new JdbcTemplate(dataSource);
   }

   public void create(Visit visit) {
      String SQL = "insert into visits (type, customer, cardid, description, time) values (?, ?, ?, ?, ?)";
      jdbcTemplateObject.update( SQL, visit.getVisitType(), visit.getVisitCustomerPnr(), visit.getVisitCardID(), visit.getVisitDescription(), visit.getVisitDate());
      System.out.println("Created Visit Record Type = " + visit.getVisitType() + " Customer = " + visit.getVisitCustomerPnr());
      return;
   }

   public Visit getVisit(Integer id) {
      String SQL = "select * from visits where visitid = ?";
      Visit Visit = jdbcTemplateObject.queryForObject(SQL, 
                        new Object[]{id}, new VisitRowMapper());
      return Visit;
   }

   public List<Visit> loadVisits() {
      String SQL = "select * from visits";
      List <Visit> Visits = jdbcTemplateObject.query(SQL, 
                                new VisitRowMapper());
      return Visits;
   }

   public void delete(Integer id){
      String SQL = "delete from visits where visitid = ?";
      jdbcTemplateObject.update(SQL, id);
      System.out.println("Deleted Visit Record with ID = " + id );
      return;
   }

   public void update(Visit visit){
      String SQL = "update visits set customer = ?, cardid = ?, type = ?, description = ?, time = ? where visitid = ?";
      jdbcTemplateObject.update(SQL, visit.getVisitCustomerPnr(), visit.getVisitCardID(), visit.getVisitType(), visit.getVisitID(), visit.getVisitDescription(), visit.getVisitDate());
      System.out.println("Updated visit Record with ID = " + visit.getVisitID() );
      return;
   }

}