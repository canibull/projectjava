package gymmet_main;

import java.util.List;

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

        CustomerJDBCTemplate customerJDBCTemplate = (CustomerJDBCTemplate)context.getBean("customerJDBCTemplate");
        CardJDBCTemplate cardJDBCTemplate = (CardJDBCTemplate)context.getBean("cardJDBCTemplate");

        System.out.println("--- START ---\n");
        System.out.println(Customer.getCustomers().size());
        readCustomersFromCSV();
        System.out.println(Customer.getCustomers().size());
        //List<Customer> customers = customerJDBCTemplate.listCustomers();
        List<Customer> customers = customerJDBCTemplate.listCustomers();
        for (Customer record : customers) {
           System.out.println(record.toString());
        }
        System.out.println(Customer.getCustomers().size());

        for (Customer record : Customer.getCustomers()) {
            System.out.println(record.toString());
         }

        List<Card> cards = cardJDBCTemplate.listCards();
        for (Card record : cards) {
           System.out.println(record.toString());
        }


        System.out.println("\n--- STOP ---");

        context.close();
        }

    }

//    public List<Customer> readAllCustomers() {
//        String SQL = "SELECT * FROM gymmet.customers;";
//        List <Customer> customers = jdbcTemplateObject.query(SQL, new StudentMapper());
//        return customers;
//     }

    /**
     * Read all information about a specific customer
     * @param custID Primary key index of customer
     */
    public static void readCustomer(int custID) {
    	//SELECT * FROM gymmet.customers WHERE custid = 1;
    	
    }

    /**
     * A function to import customers from a flat file database
     */
    public static void readCustomersFromCSV() {
    	// TODO Match against existing social security numbers to avoid duplicates, add option to drop all tables, error checking
    	// TODO Alternatively handle duplicated in customer commit function
    	// TODO Bake into CustomerDAO
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
//			Customer player = itemReader.read();
    		//Customer cunt = new Customer();
			while (itemReader.read() != null);

//			System.out.println(player.toString());
//			Customer cunt = itemReader.read();
//			System.out.println(cunt.toString());
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