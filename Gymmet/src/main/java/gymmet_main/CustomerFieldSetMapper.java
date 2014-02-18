package gymmet_main;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

public class CustomerFieldSetMapper implements FieldSetMapper<Customer> {
    public Customer mapFieldSet(FieldSet fieldSet) {
        Customer cust = new Customer();
        // TODO Error checking
        // Set ID to array list size + 1 since CSV does not contain any ID
        cust.setID(Customer.getExtCustomers().size());
        cust.setCustName(fieldSet.readString(0));
        cust.setCustPnr(fieldSet.readLong(1)); 
        cust.setCustAddress(fieldSet.readString(2));
        cust.setCustPhone(fieldSet.readString(3));
        cust.setCustAltered(false);
        cust.addToExtList(cust.getCustID(), cust);
        return cust;
    }
}