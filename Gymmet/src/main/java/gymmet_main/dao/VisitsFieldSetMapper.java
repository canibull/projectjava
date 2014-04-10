package gymmet_main.dao;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import gymmet_main.model.Visit;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

public class VisitsFieldSetMapper implements FieldSetMapper<Object> {
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public Object mapFieldSet(FieldSet fieldSet) {
      Visit visit = new Visit();
      visit.setID(-1);
      visit.setVisitCardID(Visit.lastCardAddedID);
      visit.setVisitCustomerPnr(Visit.lastCardAddedPnr);
      visit.setVisitType(fieldSet.readString(2));
      visit.setVisitDescription(fieldSet.readString(3));

      try {
    	  	visit.setVisitDate(new Timestamp(dateFormat.parse(fieldSet.readString(1)).getTime()));
		} catch (ParseException e) {
			visit.setVisitDate(new Timestamp(System.currentTimeMillis()));
		}
      visit.setVisitAltered(true);
      visit.addToList(visit);

      return 1;
    }
}