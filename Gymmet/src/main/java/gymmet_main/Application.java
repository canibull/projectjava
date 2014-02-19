package gymmet_main;

import gymmet_main.dao.CardJDBCTemplate;
import gymmet_main.dao.CustomerFieldSetMapper;
import gymmet_main.dao.CustomerJDBCTemplate;
import gymmet_main.model.Card;
import gymmet_main.model.Customer;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;

@ComponentScan
@EnableAutoConfiguration
public class Application {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Application.class);
        application.setShowBanner(false);
        application.setLogStartupInfo(false);
        application.run(args);
        try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml")) {

        // Initiate the JDBC template of container classes
        Customer.jdbc_DAO = (CustomerJDBCTemplate)context.getBean("customerJDBCTemplate");
        Card.jdbc_DAO = (CardJDBCTemplate)context.getBean("cardJDBCTemplate");

        InterfaceHandler.interactionLoop();

        context.close();
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
    	Customer.commitChanges();
    }

}