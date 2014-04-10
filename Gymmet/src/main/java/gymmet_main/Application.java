package gymmet_main;

import gymmet_main.dao.CustomerJDBCTemplate;
import gymmet_main.dao.CardJDBCTemplate;
import gymmet_main.dao.VisitJDBCTemplate;
import gymmet_main.model.Customer;
import gymmet_main.model.Card;
import gymmet_main.model.Visit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
        Visit.jdbc_DAO = (VisitJDBCTemplate)context.getBean("visitJDBCTemplate");

        // Start interaction loop for terminal interface
        InterfaceHandler.interactionLoop();

        context.close();

        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }

}