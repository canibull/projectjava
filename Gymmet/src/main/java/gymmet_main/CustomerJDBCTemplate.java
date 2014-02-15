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

   public void create(String name, Long pnr, String address, String phone) {
      String SQL = "insert into customers (custname, custpnr, custaddress, custphone) values (?, ?, ?, ?)";
      
      jdbcTemplateObject.update( SQL, name, pnr, address, phone);
      System.out.println("Created Record Name = " + name + " Age = " + pnr + " Address = " + address + " Phone = " + phone);
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

   public void update(Integer id, Integer age){
      String SQL = "update customers set age = ? where id = ?";
      jdbcTemplateObject.update(SQL, age, id);
      System.out.println("Updated Record with ID = " + id );
      return;
   }

}