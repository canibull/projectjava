package gymmet_main;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CustomerRowMapper implements RowMapper<Customer> {
   public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
	  Customer cust = new Customer();
      cust.setID(rs.getInt("custid"));
      cust.setCustName(rs.getString("custname"));
      cust.setCustPnr(rs.getLong("custpnr")); 
      cust.setCustAddress(rs.getString("custAddress"));
      cust.setCustPhone(rs.getString("custphone"));
      return cust;
   }
}