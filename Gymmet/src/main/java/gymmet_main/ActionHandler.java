package gymmet_main;

import java.util.LinkedHashMap;

import gymmet_main.dao.CardFieldSetMapper;
import gymmet_main.dao.CustomerFieldSetMapper;
import gymmet_main.dao.PeriodCardFieldSetMapper;
import gymmet_main.dao.VisitsFieldSetMapper;
import gymmet_main.model.Card;
import gymmet_main.model.Customer;
import gymmet_main.model.Visit;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.mapping.PatternMatchingCompositeLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.core.io.FileSystemResource;

public class ActionHandler {
	/**
	 * Load customers from database
	 */
	public static void loadCustomers() {
		Customer.getCustomers().clear();
		Customer.jdbc_DAO.loadCustomers();
	}
	/**
	 * Load cards from database
	 */
	public static void loadCards() {
		Card.getCards().clear();
		Card.jdbc_DAO.loadCards();
	}
	/**
	 * Load visits from database
	 */
	public static void loadVisits() {
		Visit.getVisits().clear();
		Visit.jdbc_DAO.loadVisits();
	}
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

    /**
     * A function to import cards and visits from a flat file database
     */
    public static void readCardsFromCSV() {
 
    	FlatFileItemReader<Object> itemReader = new FlatFileItemReader<Object>();
    	itemReader.setResource(new FileSystemResource("cards.txt"));

    	PatternMatchingCompositeLineMapper<Object> lineMapper = new PatternMatchingCompositeLineMapper<Object>();

    	DelimitedLineTokenizer semicolTokenizer = new DelimitedLineTokenizer();
    	semicolTokenizer.setDelimiter(";");

    	LinkedHashMap<String, LineTokenizer> tokenizers = new LinkedHashMap<String, LineTokenizer>();
    	tokenizers.put("Coupons*", semicolTokenizer);
    	tokenizers.put("Period*", semicolTokenizer);
    	tokenizers.put("V*", semicolTokenizer);
		lineMapper.setTokenizers(tokenizers);
 
    	LinkedHashMap<String, FieldSetMapper<Object>> fieldSetMappers = new LinkedHashMap<String, FieldSetMapper<Object>>();
    	fieldSetMappers.put("Coupons*", new CardFieldSetMapper());
    	fieldSetMappers.put("Period*", new PeriodCardFieldSetMapper());
    	fieldSetMappers.put("V*", new VisitsFieldSetMapper());
		lineMapper.setFieldSetMappers(fieldSetMappers);

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
