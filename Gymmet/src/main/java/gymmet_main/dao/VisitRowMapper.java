package gymmet_main.dao;

import gymmet_main.model.Visit;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class VisitRowMapper implements RowMapper<Visit> {
   public Visit mapRow(ResultSet rs, int rowNum) throws SQLException {
	  Visit visit = new Visit();
      visit.setID(rs.getInt("visitid"));
      visit.setVisitCustomerPnr(rs.getLong("customer"));
      visit.setVisitCardID(rs.getInt("cardid"));
      visit.setVisitType(rs.getString("type"));
      visit.setVisitDescription(rs.getString("description"));
      visit.setVisitDate(rs.getTimestamp("time"));
      visit.setVisitAltered(false);
      visit.addToList(visit);
      return visit;
   }
}