package no.westerdals.pgr301exam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

	private Logger logger = LoggerFactory.getLogger(DemoApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	CommandLineRunner initTestData(CustomerRepository repository) {
		return args -> {
			logger.info("Preloading " + repository.save(new Customer("Eddard", "Stark")));
			logger.info("Preloading " + repository.save(new Customer("Jon", "Arryn")));
			logger.info("Preloading " + repository.save(new Customer("Robert", "Baratheon")));
		};
	}
}
