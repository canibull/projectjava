package gymmet_main;

import gymmet_main.dao.CustomerFieldSetMapper;
import gymmet_main.model.Customer;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.FileSystemResource;

public class ActionHandler {
    /**
     * A function to import customers from a flat file database
     */
    public static void readCustomersFromCSV() {
    	FlatFileItemReader<Customer> itemReader = new FlatFileItemReader<Customer>();
    	itemReader.setResource(new FileSystemResource("customers.txt"));
    	DefaultLineMapper<Customer> lineMapper = new DefaultLineMapper<Customer>();
    	DelimitedLineTokenizer semicolTokenizer = new DelimitedLineTokenizer();
    	semicolTokenizer.setDelimiter(";");
    	lineMapper.setLineTokenizer(semicolTokenizer);
    	lineMapper.setFieldSetMapper(new CustomerFieldSetMapper());
    	itemReader.setLineMapper(lineMapper);
    	itemReader.open(new ExecutionContext());
    	try {
			while (itemReader.read() != null);
		} catch (UnexpectedInputException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
