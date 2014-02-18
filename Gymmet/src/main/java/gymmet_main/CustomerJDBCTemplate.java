package gymmet_main;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class CustomerJDBCTemplate implements CustomerDAO {
   private DataSource dataSource;
   private JdbcTemplate jdbcTemplateObject;
   
   public void setDataSource(DataSource dataSource) {
      this.dataSource = dataSource;
      this.jdbcTemplateObject = new JdbcTemplate(dataSource);
   }

   public void create(Customer cust) {
	  String SQL = "INSERT INTO gymmet.customers (custpnr, custname, custaddress, custphone) VALUES (?, ?, ?, ?) ON DUPLICATE KEY UPDATE custpnr = VALUES(custpnr), custname = VALUES(custname), custaddress = VALUES(custaddress), custphone = VALUES(custphone)";
	  jdbcTemplateObject.update( SQL, cust.getCustPnr(), cust.getCustName(), cust.getCustAddress(), cust.getCustPhone());
      System.out.println("Created Record Name = " + cust.getCustName() + " Age = " + cust.getCustPnr() + " Address = " + cust.getCustAddress() + " Phone = " + cust.getCustPhone());
      return;
   }

   public Customer getCustomer(Integer id) {
      String SQL = "select * from customers where id = ?";
      Customer Customer = jdbcTemplateObject.queryForObject(SQL, 
                        new Object[]{id}, new CustomerRowMapper());
      return Customer;
   }

   public List<Customer> listCustomers() {
      String SQL = "select * from customers";
      List <Customer> Customers = jdbcTemplateObject.query(SQL, 
                                new CustomerRowMapper());
      return Customers;
   }

   public void delete(Integer id){
      String SQL = "delete from customers where id = ?";
      jdbcTemplateObject.update(SQL, id);
      System.out.println("Deleted Record with ID = " + id );
      return;
   }

   public void update(Customer cust){
      String SQL = "UPDATE customers SET custpnr = ?, custname = ?, custaddress = ? WHERE id = ?";
      jdbcTemplateObject.update(SQL, cust.getCustPnr(), cust.getCustName(), cust.getCustAddress(), cust.getCustID());
      System.out.println("Updated Record with ID = " + cust.getCustID() );
      return;
   }

}