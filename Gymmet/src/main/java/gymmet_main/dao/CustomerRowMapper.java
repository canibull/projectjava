package gymmet_main.dao;

import gymmet_main.model.Customer;

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
      cust.setCustAltered(false);
      cust.addToList(cust.getCustPnr(), cust);
      return cust;
   }
}